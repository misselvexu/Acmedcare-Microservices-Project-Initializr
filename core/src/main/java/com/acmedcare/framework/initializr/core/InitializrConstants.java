package com.acmedcare.framework.initializr.core;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * {@link InitializrConstants}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-06-03.
 */
public class InitializrConstants {

  private static final String HOME_ENV_PROPERTIES = "acmedcare.initializr.home";

  public static final Integer ONCE = 1;

  /**
   * Home Properties Defined
   *
   * <p>
   */
  public static String home = home();

  public static final String CREATED_DIR = "/created";

  public static final String PACKAGE_SUFFIX = ".zip";

  public static final String[] SOURCE_DIRS_KEY = {"src/main/java", "src/test/java"};

  private static String home() {
    return InitializrConstants.home = System.getProperty(HOME_ENV_PROPERTIES);
  }

  public static boolean isSourceFile(String filePath) {
    if (StringUtils.isNoneBlank(filePath)) {
      for (String sourceDir : SOURCE_DIRS_KEY) {
        if (filePath.contains(sourceDir)) {
          return true;
        }
      }
    }
    return false;
  }

  public static String buildRightSourceFilePath(String originFilePath, String basePackage) {

    String rightFilePath = originFilePath;

    String packageDir = basePackage.replace(".", File.separator);

    for (String sourceDir : SOURCE_DIRS_KEY) {
      rightFilePath =
          rightFilePath.replace(sourceDir, sourceDir.concat(File.separator).concat(packageDir));
    }

    return rightFilePath;
  }

  // === other system properties

  /**
   * {@link Constants}
   *
   * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
   * @version ${project.version} - 2019-06-03.
   */
  public static class Constants {

    /**
     * Group Id Defined
     *
     * <p>
     */
    public static final String GROUP_ID = "${__groupId__}";

    /**
     * Artifact Id Defined
     *
     * <p>
     */
    public static final String ARTIFACT_ID = "${__artifactId__}";

    /**
     * Package Name Defined
     *
     * <p>
     */
    public static final String PACKAGE_NAME = "${__package__}";

    /**
     * Version Defined
     *
     * <p>
     */
    public static final String VERSION = "${__version__}";

    /**
     * Project Name Defined
     *
     * <p>
     */
    public static final String PROJECT_NAME = "${__name__}";

    /**
     * Server Port
     *
     * <p>
     */
    public static final String SERVER_PORT = "${__server.port__}";

    /**
     * Database Host
     *
     * <p>
     */
    public static final String DATABASE_HOST = "${__db.host__}";

    /**
     * Database Name
     *
     * <p>
     */
    public static final String DATABASE_NAME = "${__db.name__}";

    /**
     * Database Username
     *
     * <p>
     */
    public static final String DATABASE_USERNAME = "${__db.username__}";

    /**
     * Database Password
     *
     * <p>
     */
    public static final String DATABASE_PASSWORD = "${__db.password__}";
  }
}
