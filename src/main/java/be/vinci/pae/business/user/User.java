package be.vinci.pae.business.user;

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
   * @param password the password to hash.
   * @return the hashed password.
   */
  String hashPassword(String password);

  /**
   * Get the current academic year.
   *
   * @return academicYear format of the current year
   */
  String getCurrentYear();

  /**
   * Render the user's role.
   *
   * @param user the user you want to get its role
   * @return String char user role
   */
  String renderRole(UserDTO user);


}


