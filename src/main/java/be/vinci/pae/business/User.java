package be.vinci.pae.business;

/**
 * The {@code User} interface represents a user within the application. It extends the
 * {@code UserDTO} interface, providing additional methods related to user functionality.
 */
public interface User extends UserDTO {

  /**
   * Checks if the provided password matches the user's hashed password.
   *
   * @param password the password to check.
   * @return {@code true} if the password matches, {@code false} otherwise.
   */
  boolean checkPassword(String password);

  /**
   * Hashes the provided password using a secure hashing algorithm.
   *
   * @return the hashed password.
   */
  String hashPassword(String password);

  /**
   * Creates a string representation of the user, typically including essential details like user
   * ID, login, and password (hashed).
   *
   * @return the string representation of the user.
   */
  @Override
  String toString();
}

