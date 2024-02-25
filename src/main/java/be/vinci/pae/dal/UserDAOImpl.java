package be.vinci.pae.dal;

import be.vinci.pae.business.Factory;
import be.vinci.pae.business.UserDTO;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Provides services related to user data management, including retrieval, creation, login, and
 * registration of users. Utilizes a JSON-based storage mechanism and JWT for authentication
 * tokens.
 */
public class UserDAOImpl implements UserDAO {

  @Inject
  private DALService ps;

  @Inject
  private Factory factory;

  /**
   * Retrieves a single user by their ID.
   *
   * @param id the user's ID.
   * @return the user with the specified ID or null if not found.
   */
  @Override
  public UserDTO getOneById(int id) {
    UserDTO user = factory.getPublicUser();
    String query = "SELECT id_utilisateur, mot_de_passe, nom, prenom, numero_tel, date_inscription, role_utilisateur FROM utilisateur WHERE id_utilisateur = ?";
    try (PreparedStatement statement = ps.preparedStatement(query)) {
      statement.setInt(1, id);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {

          // Remplir l'objet user avec toutes les données nécessaires de la base de données.
          user.setId(rs.getInt("id_utilisateur"));
          user.setEmail(rs.getString("email")); // Supposant que login est l'email.
          user.setPassword(rs.getString("mot_de_passe"));
          user.setNom(rs.getString("nom"));
          user.setPrenom(rs.getString("prenom"));
          user.setNumTel(rs.getString("numero_tel"));
          user.setDateInscription(rs.getDate("date_inscription"));
          user.setRole(rs.getString("role_utilisateur"));
        }
        statement.close();
        return user;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Retrieves a single user by their login.
   *
   * @param email the user's login.
   * @return the user with the specified login or null if not found.
   */
  @Override
  public UserDTO getOneByEmail(String email) {
    UserDTO user = factory.getPublicUser();
    String query = "SELECT id_utilisateur, mot_de_passe, nom, prenom, numero_tel, date_inscription, role_utilisateur FROM utilisateur WHERE email = ?";
    try (PreparedStatement statement = ps.preparedStatement(query)) {
      statement.setString(1, email);
      try (ResultSet rs = statement.executeQuery()) {
        System.out.println("test1");
        while (rs.next()) {
          System.out.println("test2");
          // Remplir l'objet user avec toutes les données nécessaires de la base de données.
          user.setId(rs.getInt("id_utilisateur"));
          user.setEmail(email);
          user.setPassword(rs.getString("mot_de_passe"));
          user.setNom(rs.getString("nom"));
          user.setPrenom(rs.getString("prenom"));
          user.setNumTel(rs.getString("numero_tel"));
          user.setDateInscription(rs.getDate("date_inscription"));
          user.setRole(rs.getString("role_utilisateur"));
        }
        statement.close();
        System.out.println(user);
        return user;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}