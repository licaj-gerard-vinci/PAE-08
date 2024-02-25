package be.vinci.pae.dal;

import be.vinci.pae.business.FactoryImpl;
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
  private FactoryImpl factory;

  /**
   * Retrieves a single user by their ID.
   *
   * @param id the user's ID.
   * @return the user with the specified ID or null if not found.
   */
  @Override
  public UserDTO getOneById(int id) {
    UserDTO userDTO = factory.getPublicUser();
    String query = "SELECT id_utilisateur, mot_de_passe, nom, prenom, numero_tel, date_inscription, anne_academique, role_utilisateur FROM pae.utilisateur WHERE id_utilisateur = ?";
    try (PreparedStatement statement = ps.preparedStatement(query)) {
      statement.setInt(1, id);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          UserDTO user = factory.getPublicUser();

          // Remplir l'objet user avec toutes les données nécessaires de la base de données.
          user.setId(rs.getInt("id_utilisateur"));
          user.setEmail(rs.getString("email")); // Supposant que login est l'email.
          user.setNom(rs.getString("nom"));
          user.setPrenom(rs.getString("prenom"));
          user.setNumTel(rs.getString("numero_tel"));
          user.setDateInscription(rs.getDate("date_inscription"));
          user.setAnneeAcademique(rs.getString("anne_academique"));
          user.setRole(rs.getString("role_utilisateur").charAt(0));
        }
        statement.close();
        return userDTO;
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
    return null;
  }

}