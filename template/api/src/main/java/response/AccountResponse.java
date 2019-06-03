
package ${__package__}.response;

import com.acmedcare.framework.common.SerializableBean;
import com.acmedcare.framework.kits.BeanUtils;
import com.acmedcare.framework.kits.jackson.JacksonDateFormat;
import ${__package__}.Constants;
import ${__package__}.bean.Account;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * AccountResponse
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-03-06.
 */
@Getter
@Setter
@Builder
@ApiModel(description = "用户查询返回值")
@AllArgsConstructor
public class AccountResponse extends SerializableBean {

  private static final long serialVersionUID = 7966948111148821592L;

  /** 代理主键 */
  @ApiModelProperty(value = "通行证编号")
  private long pkid;

  /** 通行证账号 */
  @ApiModelProperty(value = "通行证账号")
  private String passport;

  /** 用户名称 */
  @ApiModelProperty(value = "姓名")
  private String username;

  /**
   * 账户状态
   *
   * <p>默认: 有效 {@link Constants.AccountStatus#ENABLED}
   *
   * @see Constants.AccountStatus
   */
  @ApiModelProperty(value = "账户状态")
  private Constants.AccountStatus status = Constants.AccountStatus.ENABLED;

  /** 创建时间 */
  @ApiModelProperty(value = "创建时间")
  @JacksonDateFormat
  private Date createTime;

  /** 更新时间 */
  @ApiModelProperty(hidden = true)
  private Date upgradeTime;

  /**
   * Default Constructor
   *
   * <p>
   */
  public AccountResponse() {}

  /**
   * Copy Properties From {@link Account} instance
   *
   * @param account source instance of {@link Account}
   */
  public AccountResponse(Account account) {
    BeanUtils.copyProperties(account, this);
  }
}
