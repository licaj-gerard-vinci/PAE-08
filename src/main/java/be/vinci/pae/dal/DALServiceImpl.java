package be.vinci.pae.dal;

import be.vinci.pae.utils.Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
      conn = DriverManager.getConnection(url, "nadir_ahdid",
          "nadir123");
    } catch (SQLException e) {
      System.out.println("Impossible de joindre le serveur !");
      e.printStackTrace();
      System.exit(1);
    }

  }

  /*
   * Create a PS
   * paramters : String Query
   * */
  @Override
  public PreparedStatement preparedStatement(String query) {
    try {
      return conn.prepareStatement(query);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }


}



