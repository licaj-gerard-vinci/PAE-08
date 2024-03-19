package be.vinci.pae.business.user;

import be.vinci.pae.business.user.UserDTO;

/**
 * The {@code UserUCC} interface provides methods for managing user-related operations, such as
 * registering new users and retrieving user data.
 */
public interface UserUCC {

  /**
   * Login a new user.
   *
   * @param email    the user's email.
   * @param password the user's password.
   * @return the registered user.
   */
  UserDTO login(String email, String password);

  /**
   * Registers a new user.
   *
   * @param email    the user's email.
   * @param password the user's password.
   * @param name the user's name.
   * @param firstname the user's firstname.
   * @param phone the user's phone.
   * @param confirmPassword the user's confirmPassword.
   * @return the registered user.
   */
  UserDTO register(String email, String password, String name, String firstname, String phone, String confirmPassword, String role);

  /**
   * Retrieves a single user by their ID.
   *
   * @param id the user's ID.
   * @return the user with the specified ID or null if not found.
   */
  UserDTO getOne(int id);

}