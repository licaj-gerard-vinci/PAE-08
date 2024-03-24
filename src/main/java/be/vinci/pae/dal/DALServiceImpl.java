package be.vinci.pae.dal;

import be.vinci.pae.utils.Config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

public class DALServiceImpl implements DALBackService, DALServices {

  private ThreadLocal<Connection> connection = new ThreadLocal<>();
  private ThreadLocal<Integer> transactionCounter = new ThreadLocal<Integer>() {
    @Override
    protected Integer initialValue() {
      return 0; // Initialiser le compteur de transaction à 0 pour chaque thread
    }
  };
  private BasicDataSource dataSource;

  public DALServiceImpl() {
    dataSource = new BasicDataSource();

    String url = Config.getProperty("DatabaseFilePath");
    String user = Config.getProperty("User");
    String password = Config.getProperty("Password");

    dataSource.setUrl(url);
    dataSource.setUsername(user);
    dataSource.setPassword(password);
  }

  @Override
  public PreparedStatement preparedStatement(String query) {
    try {
      return getConnection().prepareStatement(query);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Connection getConnection() {
    Connection conn = connection.get();
    if (conn == null) {
      try {
        System.out.println("avant la conexion "+ dataSource.getNumActive());
        conn = dataSource.getConnection();
      System.out.println("apres la conexion "+ dataSource.getNumActive());
        connection.set(conn);
      } catch (SQLException e) {
        throw new RuntimeException(e);
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
      throw new RuntimeException(e);
    }
  }

  @Override
  public void commitTransaction() {
    try {
      int counter = transactionCounter.get() - 1;
      transactionCounter.set(counter);
      if (counter == 0) { // Commit seulement si c'est la transaction de niveau le plus externe
        getConnection().commit();
        getConnection().setAutoCommit(true);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void rollbackTransaction() {
    try {
      int counter = transactionCounter.get() - 1;
      transactionCounter.set(counter);
      if (counter == 0) { // Rollback seulement si c'est la transaction de niveau le plus externe
        getConnection().rollback();
        getConnection().setAutoCommit(true);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void close() {
    try {
      if (connection.get() != null) {
        System.out.println("avant le close " + dataSource.getNumActive());
        connection.get().close();
        connection.remove(); // Assurez-vous de nettoyer la référence de la connexion
        transactionCounter.remove(); // Nettoyer le compteur de transaction pour le thread actuel
        System.out.println("apres le close " + dataSource.getNumActive());

      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }


}
