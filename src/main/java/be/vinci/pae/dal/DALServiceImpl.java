package be.vinci.pae.dal;

import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.utils.Config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * The Class DALServiceImpl.
 */
public class DALServiceImpl implements DALBackService, DALServices {

  private ThreadLocal<Connection> connection = new ThreadLocal<>();
  private ThreadLocal<Integer> transactionCounter = new ThreadLocal<Integer>() {
    @Override
    protected Integer initialValue() {
      return 0;
    }
  };
  private BasicDataSource dataSource;

  /**
   * Instantiates a new DAL service impl.
   */
  public DALServiceImpl() {
    dataSource = new BasicDataSource();

    String url = Config.getProperty("DatabaseFilePath");
    String user = Config.getProperty("User");
    String password = Config.getProperty("Password");

    dataSource.setUrl(url);
    dataSource.setUsername(user);
    dataSource.setPassword(password);
    dataSource.setMaxTotal(5);
  }

  @Override
  public PreparedStatement preparedStatement(String query) {
    try {
      return getConnection().prepareStatement(query);
    } catch (SQLException e) {
      throw new FatalException(e);
    }
  }

  /**
   * Gets the connection.
   *
   * @return the connection
   */
  public Connection getConnection() {
    Connection conn = connection.get();
    if (conn == null) {
      try {
        conn = dataSource.getConnection();
        connection.set(conn);
      } catch (SQLException e) {
        throw new FatalException(e);
      }
    }
    return conn;
  }

  @Override
  public void startTransaction() {
    try {
      if (transactionCounter.get() == 0) {
        getConnection().setAutoCommit(false);
      }
      transactionCounter.set(transactionCounter.get() + 1);
    } catch (SQLException e) {
      throw new FatalException(e);
    }
  }

  @Override
  public void commitTransaction() {
    try {
      int counter = transactionCounter.get() - 1;
      transactionCounter.set(counter);
      if (counter == 0) {
        getConnection().commit();
        getConnection().setAutoCommit(true);
        close();
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
  }

  @Override
  public void rollbackTransaction() {
    try {
      int counter = transactionCounter.get() - 1;
      transactionCounter.set(counter);
      if (counter == 0) {
        getConnection().rollback();
        getConnection().setAutoCommit(true);
        close();
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
  }

  @Override
  public void close() {
    try {
      if (connection.get() != null) {
        System.out.println("avant le close " + dataSource.getNumActive());
        connection.get().close();
        connection.remove();
        transactionCounter.remove();
        System.out.println("apres le close " + dataSource.getNumActive());

      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
  }


}
