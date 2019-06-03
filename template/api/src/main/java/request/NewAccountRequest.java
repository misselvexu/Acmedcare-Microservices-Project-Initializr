#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.request;

import com.acmedcare.framework.common.SerializableBean;
import com.acmedcare.framework.common.request.RequestValidator;
import com.acmedcare.framework.exception.defined.InvalidRequestParamException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * {@link NewAccountRequest}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-04-01.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@ApiModel(description = "创建通行证请求对象", value = "NewAccountRequest")
public class NewAccountRequest extends SerializableBean implements RequestValidator {

  private static final long serialVersionUID = -2696675967909648374L;

  /**
   * Account Name (passport)
   *
   * <p>
   */
  @NotBlank(message = "通行证账号不能为空")
  @ApiModelProperty(required = true, value = "通行证账号")
  private String accountName;

  /**
   * Account Passport (RSA)
   *
   * <p>
   */
  @NotBlank(message = "通行证密码不能为空")
  @ApiModelProperty(
      required = true,
      value = "通行证密码(RSA)",
      example =
          "Ze2gvMZ9ZUiNM1shO1aut02otXdMTK2MdwpNfAN+j1oG2rwXumgbRJCaO/+69BSCvVQIUkAcwWUpj/Ejk3XaWOPFZBxpXH3LuJrnIip0oTkqXxXf2iXwn5feIF2b/IlkHQ+lWLRH7O1WfWec4i2NyVy2xKgYthV0GkBm8LVtndo=")
  private String accountPassword;

  /**
   * Default Constructor of {@link NewAccountRequest}
   *
   * <p>
   */
  public NewAccountRequest() {}

  /**
   * Request Params Validate Action Method .
   *
   * @throws InvalidRequestParamException if parameter is invalid, thrown {@link
   *     InvalidRequestParamException}
   */
  @Override
  public void validate() throws InvalidRequestParamException {
    // TODO
  }
}
