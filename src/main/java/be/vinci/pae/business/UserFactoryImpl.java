package be.vinci.pae.business;

public class UserFactoryImpl implements UserFactory {

  /**
   * @return
   */
  @Override
  public UserDTO getPublicUser() {
    return new UserImpl();
  }
}
