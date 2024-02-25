package be.vinci.pae.business;

/**
 * A factory for creating User objects.
 */
public interface Factory {

  /**
   * Create a new user.
   *
   * @return the user.
   */
  UserDTO getPublicUser();
}
