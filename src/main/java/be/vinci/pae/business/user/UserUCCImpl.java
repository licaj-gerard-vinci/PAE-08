package be.vinci.pae.business.user;

import be.vinci.pae.dal.user.UserDAO;
import jakarta.inject.Inject;

/**
 * The {@code UserUCCImpl} class provides methods for managing user-related operations, such as
 * registering new users and retrieving user data. It makes use of dependency injection to obtain a
 * reference to the {@code UserDAO} for accessing user-related data.
 */
public class UserUCCImpl implements UserUCC {

  @Inject
  private UserDAO userDAO;


  /**
   * Login a new user.
   *
   * @return the registered user.
   */
  @Override
  public UserDTO login(String email, String password) throws IllegalArgumentException {
    User user = (User) userDAO.getOneByEmail(email);
    if (user != null) {
      return user;
    }
    return null;
  }

  /**
   * Registers a new user.
   *
   * @return the registered user.
   */
  @Override
  public UserDTO register(String email, String password, String name, String firstname, String phone, String confirmPassword, String role) {
    User user = (User) userDAO.getOneByEmail(email);
    if(!role.equals("E") && !role.equals("A") && !role.equals("P")){
      return null;
    }
    if (user != null || !password.equals(confirmPassword)) {
      return null;
    }
    user = (User) userDAO.register(email, password, name, firstname, phone, role);

    user.setDateInscription(new java.sql.Date(System.currentTimeMillis()));

    return user;
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



}