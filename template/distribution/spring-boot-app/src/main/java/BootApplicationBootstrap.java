package ${package};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xyz.vopen.tiffany.swagger.EnableSwagger2;

/**
 * {@link BootApplicationBootstrap}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-03-12.
 */
@EnableSwagger2
@SpringBootApplication
public class BootApplicationBootstrap {

  public static void main(String[] args) {
    SpringApplication.run(BootApplicationBootstrap.class, args);
  }
}
