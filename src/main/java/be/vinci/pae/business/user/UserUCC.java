package be.vinci.pae.business.user;

import java.util.List;

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

  /**
   * Registers a new user.
   *
   * @param userDTO the user to register.
   * @return the registered user.
   */
  UserDTO register(UserDTO userDTO);


  /**
   * Updates a user.
   *
   * @param userDTO the user to update.
   * @return the updated user.
   */
  boolean update(int id, UserDTO userDTO);

  /**
   * Verifies a user.
   *
   * @param id the user's ID.
   * @param password the user's password.
   * @return the verified user.
   */
  boolean checkPassword(int id, String password);

}