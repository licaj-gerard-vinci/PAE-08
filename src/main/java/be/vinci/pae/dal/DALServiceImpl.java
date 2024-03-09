package be.vinci.pae.dal;

import be.vinci.pae.utils.Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Provides a data access layer service for creating prepared statements.
 */
public class DALServiceImpl implements DALService {

  private Connection conn;

  /**
   * Constructs a Configuration object and initializes a database connection. If connection cannot
   * be established, the application will terminate.
   */
  public DALServiceImpl() {
    try {
      // Connexion à la base de données
      String url = Config.getProperty("DatabaseFilePath");
      String user = Config.getProperty("User");
      String password = Config.getProperty("Password");

      conn = DriverManager.getConnection(url, user, password);

    } catch (SQLException e) {
      System.out.println("Impossible de joindre le serveur !");
      e.printStackTrace();
      System.exit(1);
    }

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
      return conn.prepareStatement(query);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }


}



