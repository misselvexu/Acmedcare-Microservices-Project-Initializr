package ${__package__};

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import xyz.vopen.tiffany.swagger.EnableSwagger2;

/**
 * {@link CloudApplicationBootstrap}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-03-12.
 */
@EnableSwagger2
@SpringBootApplication
@EnableDiscoveryClient
public class CloudApplicationBootstrap {

  public static void main(String[] args) {
    // new application
    new SpringApplicationBuilder()
        .sources(CloudApplicationBootstrap.class)

        // default properties
        .properties("spring.profiles.active=nacos")
        .web(WebApplicationType.SERVLET)
        .run(args);
  }
}
