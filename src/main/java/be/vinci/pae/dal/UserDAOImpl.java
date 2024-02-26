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
  private DALService dalService;

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
    String query = "SELECT id_utilisateur, email, mot_de_passe, nom, prenom, numero_tel, "
        + "date_inscription, role_utilisateur FROM pae.utilisateur WHERE id_utilisateur = ?";
    try (PreparedStatement statement = dalService.preparedStatement(query)) {
      statement.setInt(1, id);
      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          return rsToUser(rs);
        }

      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  /**
   * Retrieves a single user by their login.
   *
   * @param email the user's login.
   * @return the user with the specified login or null if not found.
   */
  @Override
  public UserDTO getOneByEmail(String email) {
    String query = "SELECT id_utilisateur, email, mot_de_passe, nom, prenom, numero_tel, "
        + "date_inscription, role_utilisateur FROM pae.utilisateur WHERE email = ?";
    try (PreparedStatement statement = dalService.preparedStatement(query)) {
      statement.setString(1, email);
      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          return rsToUser(rs);
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }


  /**
   * Retrieves a single user by their login and password.
   *
   * @param rs the user's query.
   * @return the user with the specified login and password or null if not found.
   * @throws SQLException if an error occurs while processing the query.
   */
  public UserDTO rsToUser(ResultSet rs) throws SQLException {
    UserDTO user = factory.getPublicUser();
    user.setId(rs.getInt("id_utilisateur"));
    user.setEmail(rs.getString("email"));
    user.setPassword(rs.getString("mot_de_passe"));
    System.out.println(rs.getString("nom"));
    user.setNom(rs.getString("nom"));
    user.setPrenom(rs.getString("prenom"));
    user.setNumTel(rs.getString("numero_tel"));
    user.setDateInscription(rs.getDate("date_inscription"));
    user.setRole(rs.getString("role_utilisateur"));
    return user;
  }

}