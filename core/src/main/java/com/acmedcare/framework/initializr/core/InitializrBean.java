package com.acmedcare.framework.initializr.core;

import com.acmedcare.framework.initializr.exception.InitializrException;
import lombok.*;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

import static com.acmedcare.framework.initializr.core.InitializrConstants.Constants.*;

/**
 * {@link InitializrBean}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-06-04.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InitializrBean implements Serializable {

  // == properties

  private String groupId;
  private String artifactId;
  private String version;
  private String name;
  private String packageName;
  private String description;
  private String packaging;
  private String javaVersion;

  private transient CountDownLatch waitLatch;

  // === methods

  public String renderPath(String baseDir, boolean containPackage) throws InitializrException {

    if (!StringUtils.endsWithIgnoreCase(baseDir, File.separator)) {
      baseDir = baseDir.concat(File.separator);
    }

    String createdPath =
        groupId
            .replaceAll("\\.", File.separator)
            .concat(File.separator)
            .concat(artifactId)
            .concat(File.separator)
            .concat(version)
            .concat(File.separator)
            .concat(name);

    if (containPackage) {
      createdPath = createdPath.concat(File.separator).concat(packageName);
    }

    String result = baseDir.concat(createdPath);

    Path path = Paths.get(result);

    if (!Files.exists(path)) {
      try {
        Files.createDirectories(path);
      } catch (IOException e) {
        e.printStackTrace();
        throw new InitializrException("[INITIALIZR] create target dir failed .", e);
      }
    }

    return result;
  }

  public String renderPath(String baseDir) throws InitializrException {
    return renderPath(baseDir, true);
  }

  public String sha1() throws InitializrException {
    try {
      String content =
          this.groupId
              .concat("#")
              .concat(this.artifactId)
              .concat("#")
              .concat(this.version)
              .concat("#")
              .concat(this.name)
              .concat("#")
              .concat(this.packageName);

      return new String(
          MessageDigest.getInstance("SHA").digest(content.getBytes(Charset.defaultCharset())),
          Charset.defaultCharset());
    } catch (NoSuchAlgorithmException e) {
      throw new InitializrException(e);
    }
  }

  // === equals & hashcode

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InitializrBean that = (InitializrBean) o;
    return Objects.equals(groupId, that.groupId)
        && Objects.equals(artifactId, that.artifactId)
        && Objects.equals(version, that.version)
        && Objects.equals(name, that.name)
        && Objects.equals(packageName, that.packageName)
        && Objects.equals(description, that.description)
        && Objects.equals(packaging, that.packaging)
        && Objects.equals(javaVersion, that.javaVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        groupId, artifactId, version, name, packageName, description, packaging, javaVersion);
  }

  String rebuild(String fileContent) {

    if (org.apache.commons.lang3.StringUtils.isNoneBlank(fileContent)) {
      fileContent =
          fileContent
              .replace(GROUP_ID, this.groupId)
              .replace(ARTIFACT_ID, this.artifactId)
              .replace(VERSION, this.version)
              .replace(PACKAGE_NAME, this.packageName)
              .replace(PROJECT_NAME, this.name);
    }

    return fileContent;
  }
}
