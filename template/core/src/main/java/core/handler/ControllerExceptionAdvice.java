#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.handler;

import com.acmedcare.framework.exception.defined.BizServiceException;
import com.acmedcare.framework.exception.entity.EntityBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * {@link ControllerExceptionAdvice}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-05-30.
 */
@ControllerAdvice
public class ControllerExceptionAdvice {

  /**
   * 缺少参数异常拦截
   *
   * @param e MissingServletRequestParameterException
   * @return 缺少返回返回值
   */
  @ExceptionHandler(value = MissingServletRequestParameterException.class)
  public ResponseEntity missingServletRequestParameterException(
      MissingServletRequestParameterException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            EntityBody.<Void, String>builder()
                .message("请求缺少参数[" + e.getParameterName() + "]")
                .build());
  }

  // === 定义自定义异常全局拦截机制

  /**
   * 全局业务Service处理异常
   *
   * @param e instance of {@link BizServiceException}
   * @return 返回值
   */
  @ExceptionHandler(value = BizServiceException.class)
  public ResponseEntity<?> bizServiceException(BizServiceException e) {
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(EntityBody.<Void, String>builder().message(e.getMessage()).build());
  }

  // === end

  /**
   * 最底层异常拦截
   *
   * @param e exception
   * @return 通用返回值
   */
  @ExceptionHandler(value = Exception.class)
  public ResponseEntity exception(Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(EntityBody.exception(e));
  }
}
