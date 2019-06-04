package com.acmedcare.framework.initializr.core;

/**
 * {@link InitializrConstants}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-06-03.
 */
public class InitializrConstants {

  private static final String HOME_ENV_PROPERTIES = "acmedcare.initializr.home";

  /**
   * Home Properties Defined
   *
   * <p>
   */
  public static String home = home();

  public static final String CREATED_DIR = "/created";

  public static final String PACKAGE_SUFFIX = ".zip";

  private static String home() {
    return InitializrConstants.home = System.getProperty(HOME_ENV_PROPERTIES);
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
     * Project Name Defined
     *
     * <p>
     */
    public static final String PROJECT_NAME = "${__name__}";
  }
}
