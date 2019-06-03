
package ${__package__}.repository;

import com.acmedcare.framework.exception.defined.RepositoryException;
import ${__package__}.bean.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * {@link AccountRepository} Methods Defined
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-03-06.
 */
@Repository
public interface AccountRepository extends BaseMapper<Account> {

  /**
   * Query {@link Account} by passport
   *
   * @param passport passport
   * @return a instance of {@link Account}
   * @throws RepositoryException repository exception
   */
  Account queryAccount(String passport) throws RepositoryException;
}
