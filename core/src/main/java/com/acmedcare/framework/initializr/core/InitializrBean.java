package com.acmedcare.framework.initializr.core;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

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
  private String name;
  private String packageName;
  private String description;
  private String packaging;
  private String javaVersion;

  // === methods

  public String renderPath(String baseDir) {
    // TODO
    return null;
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
        && Objects.equals(name, that.name)
        && Objects.equals(packageName, that.packageName)
        && Objects.equals(description, that.description)
        && Objects.equals(packaging, that.packaging)
        && Objects.equals(javaVersion, that.javaVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        groupId, artifactId, name, packageName, description, packaging, javaVersion);
  }
}
