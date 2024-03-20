package be.vinci.pae.business.user;

import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.user.UserDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;
import java.util.Date;
import java.util.List;

/**
 * The {@code UserUCCImpl} class provides methods for managing user-related operations, such as
 * registering new users and retrieving user data. It makes use of dependency injection to obtain a
 * reference to the {@code UserDAO} for accessing user-related data.
 */
public class UserUCCImpl implements UserUCC {

  @Inject
  private UserDAO userDAO;

  @Inject
  private DALServices dalServices;



  /**
   * Login a new user.
   *
   * @return the registered user.
   */
  @Override
  public UserDTO login(String email, String password) throws IllegalArgumentException {
    User user = (User) userDAO.getOneByEmail(email);
    if (user != null && user.checkPassword(password)) {
      return user;
    }
    return null;
  }

  /**
   * Retrieves a single user by their ID.
   *
   * @param id the user's ID.
   * @return the user with the specified ID or null if not found.
   */
  @Override
  public UserDTO getOne(int id) {
    User user = (User) userDAO.getOneById(id);
    if (user == null) {
      return null;
    }
    return user;
  }

  /**
   * Retrives all users.
   */
  @Override
  public List<UserDTO> getAll() {
    List<UserDTO> users = userDAO.getAllUsers();
    return users;
  }

  /**
   * Registers a new user.
   *
   * @return the registered user.
   */
  @Override
  public UserDTO register(String email, String password, String name, String firstname,
      String phone, String confirmPassword, String role) {

    dalServices.startTransaction();

    User user = (User) userDAO.getOneByEmail(email);
    if (user != null || !password.equals(confirmPassword)) {
      dalServices.rollbackTransaction();
      throw new WebApplicationException("email incorrect", Status.CONFLICT);
    }
    if(!role.equals("E") && !role.equals("A") && !role.equals("P")){
      dalServices.rollbackTransaction();
      throw new WebApplicationException("email incorrect", Status.CONFLICT);
    }
    Date dateInscription = new java.sql.Date(System.currentTimeMillis());
    user = (User) userDAO.insertUser(email, password, name, firstname,
        phone, role, dateInscription);
    user.setDateInscription(dateInscription);

    dalServices.commitTransaction();
    return user;
  }

}