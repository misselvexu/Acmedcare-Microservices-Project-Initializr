
package ${__package__}.repository.mybatis.autoconfigure;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * {@link MybatisMapperAutoConfiguration}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-03-12.
 */
@Configuration
@MapperScan({"${__package__}.repository"})
public class MybatisMapperAutoConfiguration {}
