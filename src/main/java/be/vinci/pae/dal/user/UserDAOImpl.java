package be.vinci.pae.dal.user;

import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.dal.DALBackService;
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
  private DALBackService dalService;

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
    String query =
        "SELECT u.user_id, u.email,u.password,u.lastname,u.firstname,u.phone_number,"
            + "u.registration_date,"
            + "u.user_role,has_internship,"
            + " sy.year FROM pae.users u, pae.school_years sy WHERE user_id = ?";
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
   * Retrieves a single user by their email.
   *
   * @param email the user's email.
   * @return the user with the specified email or null if not found.
   */
  @Override
  public UserDTO getOneByEmail(String email) {
    String query = "SELECT u.user_id, u.email,u.password,u.lastname,u.firstname,u.phone_number,"
        + "u.registration_date,u.user_role,has_internship,sy.year "
        + "FROM pae.users u, pae.school_years sy WHERE email = ?";
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
   * Retrieves a list of all users with the role 'E'.
   *
   * @return a list of UserDTO objects representing all users with the role 'E'.
   */
  @Override
  public List<UserDTO> getAllUsers() {
    String query = "SELECT u.*, sy.year " +
        "FROM pae.users u " +
        "LEFT JOIN pae.school_years sy ON u.school_year_id = sy.school_year_id";
    List<UserDTO> users = new ArrayList<>();
    try (PreparedStatement statement = dalService.preparedStatement(query)) {
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          users.add(rsToUser(rs));
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return users;
  }

  /**
   * Registers a new user.
   *
   * @param user the user to register.
   * @return the registered user.
   */
  public UserDTO insertUser(UserDTO user) {
    String query = "INSERT INTO pae.users (email, password, lastname, firstname,"
        + " phone_number, user_role, registration_date ,has_internship) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?, FALSE) returning user_id";
    try (PreparedStatement statement = dalService.preparedStatement(query)) {
      statement.setString(1, user.getEmail());
      statement.setString(2, user.getPassword());
      statement.setString(3, user.getLastname());
      statement.setString(4, user.getFirstname());
      statement.setString(5, user.getPhone());
      statement.setString(6, user.getRole());
      statement.setDate(7, (java.sql.Date) user.getRegistrationDate());
      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          user.setId(rs.getInt("user_id"));
          return user;
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
   * @param rs the ResultSet containing user data.
   * @return a UserDTO object populated with user data from the ResultSet row.
   * @throws SQLException if an error occurs while accessing the ResultSet.
   */
  private UserDTO rsToUser(ResultSet rs) throws SQLException {
    UserDTO user = factory.getPublicUser();
    user.setId(rs.getInt("user_id"));
    user.setEmail(rs.getString("email"));
    user.setPassword(rs.getString("password"));
    user.setLastname(rs.getString("lastname"));
    user.setFirstname(rs.getString("firstname"));
    user.setPhone(rs.getString("phone_number"));
    user.setRegistrationDate(rs.getDate("registration_date"));
    user.setRole(rs.getString("user_role"));
    user.setYear(rs.getString("year"));
    user.setHasInternship(rs.getBoolean("has_internship"));
    return user;
  }
}