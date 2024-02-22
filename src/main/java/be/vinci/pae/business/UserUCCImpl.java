package be.vinci.pae.business;

import be.vinci.pae.donnees.UserDataServiceImpl;
import jakarta.inject.Inject;

public class UserUCCImpl implements UserUCC {

  @Inject
  private UserDataServiceImpl userDataService;

  @Override
  public UserDTO login(String email, String password) throws IllegalArgumentException {
    if (!email.endsWith("@student.vinci.be") || !email.endsWith("@vinci.be")) {
      return null;
    }
    User user = userDataService.getOne(email);
    if (user.checkPassword(password)) {
      return user;
    }
    return null;
  }

}