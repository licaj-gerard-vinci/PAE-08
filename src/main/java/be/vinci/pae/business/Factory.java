package be.vinci.pae.business;

public interface Factory {

  /**
   * Create a new user.
   *
   * @return the user.
   */
  UserDTO getPublicUser();
}
