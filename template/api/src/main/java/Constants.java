#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

/**
 * {@link Constants} Defined
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-03-06.
 */
@SuppressWarnings("unused")
public final class Constants {

  /**
   * {@link AccountStatus} defined
   *
   * @since ${project.version}
   */
  public enum AccountStatus {

    /** 有效账户状态 */
    ENABLED,

    /** 账户锁定状态 */
    LOCKED,

    /** 无效账户(注销状态) */
    DISABLED,
  }
}
