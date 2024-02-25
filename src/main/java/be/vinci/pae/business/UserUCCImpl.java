package be.vinci.pae.business;

import be.vinci.pae.dal.UserDAO;
import jakarta.inject.Inject;

public class UserUCCImpl implements UserUCC {

  @Inject
  private UserDAO userDAO;

  /**
   * Registers a new user.
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

}