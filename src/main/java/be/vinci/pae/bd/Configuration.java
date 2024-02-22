package be.vinci.pae.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;


/**
 * Configuration class for establishing connection to the PostgreSQL database and managing user
 * authentication.
 */
public class Configuration {

  private Connection conn = null;
  private PreparedStatement preparedStatementConnexion;

  /**
   * Constructs a Configuration object and initializes a database connection. If connection cannot
   * be established, the application will terminate.
   */
  public Configuration() {
    /*  UTILISER CETTE CONFIGURATION SEULEMENT SI PC ECOLE
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println("Driver PostgreSQL manquant !");
      System.exit(1);
    }
    String url = "jdbc:postgresql://172.24.2.6:5432/dbresulramadani";

    try {
      conn = DriverManager.getConnection(url, "resulramadani", "PN6HIFCUQ");
    } catch (SQLException e) {
      System.out.println("Impossible de joindre le server !");
      System.exit(1);
    }
    try {
      preparedStatementConnexion = conn.prepareStatement(
          "SELECT id_utilisateur FROM bdpae.utilisateur WHERE email=?");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

     */

    try {

      Class.forName("org.postgresql.Driver");

      // Connexion à la base de données
      String url = "jdbc:postgresql://coursinfo.vinci.be:5432/dbnadir_ahdid?user=nadir_ahdid";
      conn = DriverManager.getConnection(url, "nadir_ahdid",
          "nadir123");

      //on recupere le mots de passe en plus pour pouvoir le comparer avec le mots de passe
      preparedStatementConnexion = conn.prepareStatement(
          "SELECT * FROM utilisateur WHERE email = ?");
    } catch (ClassNotFoundException e) {
      System.out.println("Driver PostgreSQL manquant !");
      System.exit(1);
    } catch (SQLException e) {
      System.out.println("Impossible de joindre le serveur !");
      e.printStackTrace();
      System.exit(1);
    }

  }

  /**
   * Authenticates a user based on email and password.
   *
   * @param email User's email.
   * @param mdp   User's password.
   * @return true if authenticated, false otherwise.
   */

  public boolean connexion(String email, String mdp) {

    try {
      preparedStatementConnexion.setString(1, email);
      ResultSet rs = preparedStatementConnexion.executeQuery();

      // Check if the ResultSet has a row (i.e., user found)
      if (rs.next()) {
        //
        String stockerDansLaDB = rs.getString("mot_de_passe");
        if (BCrypt.checkpw(mdp, stockerDansLaDB)) {
          System.out.println("Connexion réussie !");
          return true;
        } else {
          System.out.println("Mot de passe incorrect");
        }
      } else {
        System.out.println("Aucun utilisateur trouvé avec cet email");
      }
      return false;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Returns the current database connection.
   *
   * @return The active database connection.
   */
  public Connection getConnection() {
    return conn;
  }


}
