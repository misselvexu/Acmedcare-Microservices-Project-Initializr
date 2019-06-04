package com.acmedcare.framework.initializr.web;

import com.acmedcare.framework.exception.defined.InvalidRequestParamException;
import com.acmedcare.framework.exception.entity.EntityBody;
import com.acmedcare.framework.initializr.web.exception.InitializrException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * {@link ExceptionsHandler}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-06-04.
 */
@ControllerAdvice
public class ExceptionsHandler {

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity missParamException(MissingServletRequestParameterException e) {
    return ResponseEntity.badRequest().body(EntityBody.exception(e));
  }

  /**
   * Invalid Request Param Exception Handler
   *
   * @param e exception
   * @return default response
   */
  @ExceptionHandler(InvalidRequestParamException.class)
  public ResponseEntity invalidRequestParamException(InvalidRequestParamException e) {
    return ResponseEntity.badRequest().body(EntityBody.exception(e));
  }

  @ExceptionHandler(InitializrException.class)
  public ResponseEntity initializrException(InitializrException e) {
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(EntityBody.exception(e));
  }

  /**
   * Global Exception Handler
   *
   * @param exception e
   * @return default response
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity exception(Exception exception) {
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(EntityBody.exception(exception));
  }
}
