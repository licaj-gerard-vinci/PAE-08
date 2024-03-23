package be.vinci.pae.dal;

import be.vinci.pae.utils.Config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Provides a data access layer service for creating prepared statements.
 */
public class DALServiceImpl implements DALBackService, DALServices {

  private ThreadLocal<Connection> connection;

  private BasicDataSource dataSource;

  /**
   * Constructs a Configuration object and initializes a database connection. If connection cannot
   * be established, the application will terminate.
   */
  public DALServiceImpl() {
    connection = new ThreadLocal<>();
    dataSource = new BasicDataSource();

    String url = Config.getProperty("DatabaseFilePath");
    String user = Config.getProperty("User");
    String password = Config.getProperty("Password");

    dataSource.setUrl(url);
    dataSource.setUsername(user);
    dataSource.setPassword(password);

  }

  /**
   * Create a PS.
   *
   * @param query the query to be executed.
   * @return the prepared statement.
   */
  @Override
  public PreparedStatement preparedStatement(String query) {
    try {
      return getConnection().prepareStatement(query);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Retrieves a connection to the database.
   *
   * @return the connection.
   */
  public Connection getConnection() {
    if (connection.get() == null) {
      try {
        connection.set(dataSource.getConnection());
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
    return connection.get();
  }

  /**
   * Starts a transaction.
   */
  @Override
  public void startTransaction() {
    try {
      getConnection().setAutoCommit(false);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Commits a transaction.
   */
  @Override
  public void commitTransaction() {
    try {
      getConnection().commit();
      getConnection().setAutoCommit(true);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Rolls back a transaction.
   */
  @Override
  public void rollbackTransaction() {
    try {
      getConnection().rollback();
      getConnection().setAutoCommit(true);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Closes the connection.
   */
  @Override
  public void close() {
    try {
      if (connection.get() != null) {
        connection.get().close();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }


}



