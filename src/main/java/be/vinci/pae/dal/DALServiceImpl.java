package be.vinci.pae.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DALServiceImpl implements DALService {

  private Connection conn;

  /**
   * Constructs a Configuration object and initializes a database connection. If connection cannot
   * be established, the application will terminate.
   */
  public DALServiceImpl() {
    try {

      Class.forName("org.postgresql.Driver");

      // Connexion à la base de données
      String url = "jdbc:postgresql://localhost:5432/postgres";
      conn = DriverManager.getConnection(url, "postgres",
          "Brilantculi188");
    } catch (ClassNotFoundException e) {
      System.out.println("Driver PostgreSQL manquant !");
      System.exit(1);
    } catch (SQLException e) {
      System.out.println("Impossible de joindre le serveur !");
      e.printStackTrace();
      System.exit(1);
    }

  }

  @Override
  public Connection getConnection() {
    return conn;
  }


}



