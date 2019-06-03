
package ${__package__}.repository.handler;

import ${__package__}.Constants;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link AccountStatusTypeHandler} 自定义类型处理器
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-03-12.
 */
public class AccountStatusTypeHandler extends BaseTypeHandler<Constants.AccountStatus> {

  private static final Logger log = LoggerFactory.getLogger(AccountStatusTypeHandler.class);

  @Override
  public void setNonNullParameter(
      PreparedStatement ps, int i, Constants.AccountStatus parameter, JdbcType jdbcType)
      throws SQLException {
    log.debug("set account' status with value : {}", parameter);
    ps.setString(i, parameter.name());
  }

  @Override
  public Constants.AccountStatus getNullableResult(ResultSet rs, String columnName)
      throws SQLException {
    log.debug("get account' status with value : {}", rs.getString(columnName));
    return Constants.AccountStatus.valueOf(rs.getString(columnName));
  }

  @Override
  public Constants.AccountStatus getNullableResult(ResultSet rs, int columnIndex)
      throws SQLException {
    log.debug("get account' status with value : {}", rs.getString(columnIndex));
    return Constants.AccountStatus.valueOf(rs.getString(columnIndex));
  }

  @Override
  public Constants.AccountStatus getNullableResult(CallableStatement cs, int columnIndex)
      throws SQLException {
    log.debug("get account' status with value : {}", cs.getNString(columnIndex));
    return Constants.AccountStatus.valueOf(cs.getNString(columnIndex));
  }
}
