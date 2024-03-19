package be.vinci.pae.business.user;

import java.util.List;

/**
 * The {@code UserUCC} interface provides methods for managing user-related operations, such as
 * registering new users and retrieving user data.
 */
public interface UserUCC {

  /**
   * Registers a new user.
   *
   * @param email    the user's email.
   * @param password the user's password.
   * @return the registered user.
   */
  UserDTO login(String email, String password);

  /**
   * Retrieves a single user by their ID.
   *
   * @param id the user's ID.
   * @return the user with the specified ID or null if not found.
   */
  UserDTO getOne(int id);

  /**
   * Retrieves all users.
   *
   * @return a list of all users.
   */
  List<UserDTO> getAll();
}