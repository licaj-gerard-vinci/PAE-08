package be.vinci.pae.business;

import be.vinci.pae.donnees.UserDataService;
import jakarta.inject.Inject;

public class UserUCCImpl implements UserUCC {

  @Inject
  private UserDataService userDataService;

  @Override
  public UserDTO login(String email, String password) throws IllegalArgumentException {
    User user = (User) userDataService.getOne(email);
    if (user.checkPassword(password)) {
      return user;
    }
    return null;
  }

}