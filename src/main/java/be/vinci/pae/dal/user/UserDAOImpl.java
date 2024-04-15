package be.vinci.pae.dal.user;

import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.year.YearDTO;
import be.vinci.pae.dal.DALBackService;
import be.vinci.pae.dal.utils.DALBackServiceUtils;
import be.vinci.pae.exceptions.FatalException;
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
  private DALBackServiceUtils dalBackServiceUtils;

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
        "SELECT u.*,sy.*"
            + " FROM pae.users u, pae.school_years sy WHERE user_id = ?";
    try (PreparedStatement statement = dalService.preparedStatement(query)) {
      statement.setInt(1, id);
      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          return rsToUser(rs, "get");
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
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
    String query = "SELECT u.* ,sy.* "
        + "FROM pae.users u, pae.school_years sy WHERE user_email = ?";
    try (PreparedStatement statement = dalService.preparedStatement(query)) {
      statement.setString(1, email);
      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          return rsToUser(rs, "get");
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
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
    String query = "SELECT u.*, sy.* FROM pae.users u "
        + "LEFT JOIN pae.school_years sy ON u.user_school_year_id = sy.school_year_id";
    List<UserDTO> users = new ArrayList<>();
    try (PreparedStatement statement = dalService.preparedStatement(query)) {
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          users.add(rsToUser(rs, "get"));
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
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
    String query =
        "INSERT INTO pae.users (user_email, user_password, user_lastname, user_firstname, "
            + "user_school_year_id, user_phone_number, user_role, user_registration_date, "
            + "user_has_internship, user_version) "
            + "VALUES (?, ?, ?, ?, 1, ?, ?, ?, FALSE, 1) RETURNING user_id";
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
          user.setVersion(1);
          return user;
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
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
  private UserDTO rsToUser(ResultSet rs, String method) throws SQLException {
    YearDTO year = factory.getYearDTO();
    year.setId(rs.getInt("school_year_id"));
    year.setAnnee(rs.getString("year"));
    year.setVersion(rs.getInt("school_year_version"));
    UserDTO user = dalBackServiceUtils.fillUserDTO(rs, method);
    user.setYear(year);
    return user;
  }


  /**
   * Update info of a user.
   *
   * @param user the user to update.
   * @return true if the user was updated successfully, false otherwise.
   */
  public boolean updateUser(UserDTO user) {
    // We create a user, so we can get the version of the user before the update
    // and will just add the information we want to change in it
    UserDTO userBeforeUpdate = getOneById(user.getId());
    if (userBeforeUpdate == null) {
      return false;
    }
    boolean hasPassword = false;
    if (user.getEmail() != null && !user.getEmail().isEmpty()) {
      userBeforeUpdate.setEmail(user.getEmail());
    }
    if (user.getLastname() != null && !user.getLastname().isEmpty()) {
      userBeforeUpdate.setLastname(user.getLastname());
    }
    if (user.getFirstname() != null && !user.getFirstname().isEmpty()) {
      userBeforeUpdate.setFirstname(user.getFirstname());
    }
    if (user.getPhone() != null && !user.getPhone().isEmpty()) {
      userBeforeUpdate.setPhone(user.getPhone());
    }

    if (user.getPassword() != null && !user.getPassword().isEmpty()) {
      userBeforeUpdate.setPassword(user.getPassword());
    }

    if (user.getHasInternship()) {
      userBeforeUpdate.setHasInternship(user.getHasInternship());
    }

    String query = "UPDATE pae.users SET "
        + "user_email = ?, user_lastname = ?, user_firstname = ?, "
        + "user_phone_number = ?, user_version = user_version + 1, "
        + "user_has_internship = ?";

    if (user.getPassword() != null && !user.getPassword().isEmpty()) {
      hasPassword = true;
      query += ", user_password = ?";
    }

    query += " WHERE user_id = ? AND user_version = ?";

    try (PreparedStatement statement = dalService.preparedStatement(query)) {
      int index = 1;
      statement.setString(index++, userBeforeUpdate.getEmail());
      statement.setString(index++, userBeforeUpdate.getLastname());
      statement.setString(index++, userBeforeUpdate.getFirstname());
      statement.setString(index++, userBeforeUpdate.getPhone());
      statement.setBoolean(index++, userBeforeUpdate.getHasInternship());

      if (hasPassword) {
        statement.setString(index++, userBeforeUpdate.getPassword());
      }

      statement.setInt(index++, userBeforeUpdate.getId()); // user_id
      statement.setInt(index, userBeforeUpdate.getVersion()); // current user_version

      int rowsUpdated = statement.executeUpdate();
      return rowsUpdated > 0;
    } catch (SQLException e) {
      System.err.println("SQL error on update: " + e.getMessage());
      throw new RuntimeException("Failed to update user", e);
    }
  }



}