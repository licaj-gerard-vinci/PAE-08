package be.vinci.pae.dal.user;

import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.dal.DALService;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
    String query = "SELECT u.user_id, u.email, u.password, u.lastname, u.firstname, u.phone_number,"
        + "u.registration_date, u.user_role, sc.year, u.has_internship "
        + "FROM pae.users u, pae.school_years sc "
        + "WHERE sc.school_year_id = u.school_year_id AND u.user_role = 'E'";
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
   * @param email    the user's email.
   * @param password the user's password.
   * @param name the user's name.
   * @param firstname the user's firstname.
   * @param phone the user's phone.
   * @return the registered user.
   */
  public UserDTO insertUser(String email, String password, String name, String firstname, String phone, String role, Date dateInscription) {
    String query = "INSERT INTO pae.users (email, password, lastname, firstname, phone_number, user_role, registration_date ,has_internship) VALUES (?, ?, ?, ?, ?, ?, ?, FALSE) returning user_id";
    try (PreparedStatement statement = dalService.preparedStatement(query)) {
      statement.setString(1, email);
      statement.setString(2, password);
      statement.setString(3, name);
      statement.setString(4, firstname);
      statement.setString(5, phone);
      statement.setString(6, role);
      statement.setDate(7, (java.sql.Date) dateInscription);
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
    user.setId(rs.getInt("user_id"));
    user.setEmail(rs.getString("email"));
    user.setPassword(rs.getString("password"));
    user.setNom(rs.getString("lastname"));
    user.setPrenom(rs.getString("firstname"));
    user.setNumTel(rs.getString("phone_number"));
    user.setDateInscription(rs.getDate("registration_date"));
    user.setRole(rs.getString("user_role"));
    user.setYear(rs.getString("year"));
    user.setHasInternship(rs.getBoolean("has_internship"));
    return user;
  }


}