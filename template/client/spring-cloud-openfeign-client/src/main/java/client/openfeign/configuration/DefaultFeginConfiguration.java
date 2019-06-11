package ${__package__}.client.openfeign.configuration;

import com.acmedcare.framework.exception.entity.EntityBody;
import com.acmedcare.framework.kits.jackson.JacksonKit;
import ${__package__}.client.openfeign.exception.ApiClientException;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.*;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import feign.hystrix.HystrixFeign;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;

/**
 * {@link DefaultFeginConfiguration}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-03-06.
 */
public class DefaultFeginConfiguration {

  @Bean
  Request.Options feignOptions() {
    return new Request.Options(5000, 5000);
  }

  @Bean
  public Decoder feignDecoder() {
    return new ResponseEntityDecoder(new SpringDecoder(feignHttpMessageConverter()));
  }

  @Bean
  public feign.Logger.Level level() {
    return feign.Logger.Level.FULL;
  }

  private ObjectFactory<HttpMessageConverters> feignHttpMessageConverter() {
    final HttpMessageConverters httpMessageConverters =
        new HttpMessageConverters(new MappingJackson2HttpMessageConverter());
    return () -> httpMessageConverters;
  }

  /**
   * Feign Retryer Config
   *
   * @return a {@link Retryer} instance
   */
  @Bean
  public Retryer feignRetryer() {
    return Retryer.NEVER_RETRY;
  }

  @Bean
  public ErrorDecoder errorNormalDecoder() {
    return new FeignErrorDecoder();
  }

  @ConditionalOnProperty(name = "feign.hystrix.enabled", matchIfMissing = true)
  @Bean
  @Primary
  public ErrorDecoder errorDecoder() {
    return new FeignHystrixErrorDecoder();
  }

  @Configuration
  @ConditionalOnClass({HystrixCommand.class, HystrixFeign.class})
  protected static class HystrixFeignConfiguration {
    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "feign.hystrix.enabled", matchIfMissing = true)
    public Feign.Builder feignHystrixBuilder() {
      return HystrixFeign.builder();
    }
  }

  public static class FeignErrorDecoder implements ErrorDecoder {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Exception decode(String methodKey, Response response) {
      Exception exception = new ApiClientException("[OPEN-FEIGN-CLIENT] normal invoke exception ");
      try {
        String json = Util.toString(response.body().asReader());
        if (!StringUtils.isEmpty(json)) {
          EntityBody entityBody = JacksonKit.jsonToObject(json, EntityBody.class);
          if (entityBody != null) {
            exception = new ApiClientException(String.valueOf(entityBody.getMessage()));
          }
        }
      } catch (IOException ex) {
        logger.error(ex.getMessage(), ex);
      }
      return exception;
    }
  }

  public static class FeignHystrixErrorDecoder implements ErrorDecoder {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Exception decode(String methodKey, Response response) {
      Exception exception = new ApiClientException("[OPEN-FEIGN-CLIENT] hystrix invoke exception");
      try {
        String json = Util.toString(response.body().asReader());
        if (!StringUtils.isEmpty(json)) {
          EntityBody entityBody = JacksonKit.jsonToObject(json, EntityBody.class);
          if (entityBody != null) {
            if (400 <= response.status() && response.status() < 500) {
              exception = new HystrixBadRequestException(String.valueOf(entityBody.getMessage()));
            } else {
              logger.error(exception.getMessage(), exception);
            }
          }
        }
      } catch (IOException ex) {
        logger.error(ex.getMessage(), ex);
      }
      return exception;
    }
  }
}
