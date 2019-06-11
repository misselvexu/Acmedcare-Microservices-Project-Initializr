package ${__package__}.client.openfeign;

import ${__package__}.client.openfeign.configuration.DefaultFeginConfiguration;
import ${__package__}.response.AccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * TemplateApiClient
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-03-06.
 */
@FeignClient(
    name = ClientProperties.PROJECT_SERVICE_NAMESPACE,
    configuration = DefaultFeginConfiguration.class)
public interface TemplateApiClient {

  /**
   * Query account detail by passport
   *
   * @param passport passport
   * @return {@link ${__package__}.bean.Account} instance json
   *     response
   */
  @GetMapping(value = "/account/{passport}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<AccountResponse> queryAccount(@PathVariable("passport") String passport);
}
