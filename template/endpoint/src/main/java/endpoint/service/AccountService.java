
package ${__package__}.endpoint.service;

import com.acmedcare.framework.exception.defined.BizServiceException;
import com.acmedcare.framework.exception.defined.RepositoryException;
import ${__package__}.bean.Account;
import ${__package__}.repository.AccountRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AccountService
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-03-06.
 */
@Service
public class AccountService extends ServiceImpl<AccountRepository, Account> {

  private static final Logger log = LoggerFactory.getLogger(AccountService.class);

  private final AccountRepository accountRepository;

  @Autowired
  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  /**
   * Query Account Service
   *
   * @param passport passport
   * @return a instance of {@link Account}
   */
  public Account queryAccount(String passport) throws BizServiceException {
    try {

      return this.accountRepository.queryAccount(passport);
    } catch (RepositoryException e) {
      log.error("账号业务数据库操作异常", e);
      // catch repository exception , wrapped with biz service exception
      throw new BizServiceException(e.getMessage());
    } catch (Exception e) {
      log.error("账号业务处理异常", e);
      throw new BizServiceException("账号业务处理异常:" + e.getMessage());
    }
  }
}
