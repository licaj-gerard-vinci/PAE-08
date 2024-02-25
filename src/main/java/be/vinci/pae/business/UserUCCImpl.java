package be.vinci.pae.business;

import be.vinci.pae.dal.UserDAO;
import jakarta.inject.Inject;

public class UserUCCImpl implements UserUCC {

  @Inject
  private UserDAO userDAO;

  @Override
  public UserDTO login(String email, String password) throws IllegalArgumentException {
    User user = (User) userDAO.getOneByEmail(email);
    if (user.checkPassword(password)) {
      return user;
    }
    return null;
  }

}