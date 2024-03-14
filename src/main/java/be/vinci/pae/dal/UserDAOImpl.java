package be.vinci.pae.dal;

import be.vinci.pae.business.Factory;
import be.vinci.pae.business.UserDTO;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    String query = "SELECT user_id, email, password, last_name, first_name, phone_number, "
        + "registration_date, user_role FROM pae.users WHERE user_id = ?";
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

    String query = "SELECT user_id, email, password, last_name, first_name, phone_number, registration_date, user_role FROM pae.users WHERE email = ?";

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
   * List of all users.
   */
  public List<UserDTO> getAllUsers() {
    String query = "SELECT user_id, last_name, first_name, user_role FROM pae.users WHERE user_role = 'E'";
    List<UserDTO> users = new ArrayList<>();
    try (PreparedStatement statement = dalService.preparedStatement(query)) {
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          UserDTO user = factory.getPublicUser();
          user.setId(rs.getInt("user_id"));
          user.setNom(rs.getString("last_name"));
          user.setPrenom(rs.getString("first_name"));
          user.setRole(rs.getString("user_role"));
          users.add(user);
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return users;
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
    user.setId(rs.getInt("user_id"));
    user.setEmail(rs.getString("email"));
    user.setPassword(rs.getString("password"));
    user.setNom(rs.getString("last_name"));
    user.setPrenom(rs.getString("first_name"));
    user.setNumTel(rs.getString("phone_number"));
    user.setDateInscription(rs.getDate("registration_date"));
    user.setRole(rs.getString("user_role"));
    return user;
  }


}