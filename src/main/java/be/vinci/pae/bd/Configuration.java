package be.vinci.pae.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Configuration {

  private final static java.util.Scanner scanner = new java.util.Scanner(System.in);
  private Connection conn = null;
  private PreparedStatement preparedStatementConnexion;

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
      String url = "jdbc:postgresql://localhost:5432/postgres";
      conn = DriverManager.getConnection(url, "postgres",
          "");

      preparedStatementConnexion = conn.prepareStatement(
          "SELECT id_utilisateur, mot_de_passe FROM utilisateur WHERE email = ?");
    } catch (ClassNotFoundException e) {
      System.out.println("Driver PostgreSQL manquant !");
      System.exit(1);
    } catch (SQLException e) {
      System.out.println("Impossible de joindre le serveur !");
      e.printStackTrace();
      System.exit(1);
    }

  }

  public boolean connexion() {
    System.out.println("***************************************");
    System.out.println("* Connexion *");
    System.out.println("***************************************");
    System.out.print("email : ");
    String email = scanner.nextLine();
    System.out.print("Mot de passe : ");
    String mdp = scanner.nextLine();
    String stockerDansLaDB;
    try {
      preparedStatementConnexion.setString(1, email);
      ResultSet rs = preparedStatementConnexion.executeQuery();
      rs.next();
      stockerDansLaDB = rs.getString("mot_de_passe");
      if (BCrypt.checkpw(mdp, stockerDansLaDB)) {
        return true;
      } else {
        System.out.println("Mot de passe incorrect");
      }
      return false;

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}
