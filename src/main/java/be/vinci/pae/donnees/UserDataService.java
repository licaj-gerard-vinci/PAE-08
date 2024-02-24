package be.vinci.pae.donnees;

import be.vinci.pae.business.UserDTO;
import com.fasterxml.jackson.databind.node.ObjectNode;


/**
 * Interface for user data service operations, including retrieval, creation, and authentication of
 * users.
 */
public interface UserDataService {

  /**
   * Retrieves a list of all users.
   *
   * @return A list of {@link User} instances.
   */
  List<User> getAll();

  /**
   * Retrieves a single user by their unique identifier.
   *
   * @param id The unique identifier of the user.
   * @return The {@link User} instance, or {@code null} if not found.
   */
  UserDTO getOne(int id);

  /**
   * Retrieves a single user by their login.
   *
   * @param login The login of the user.
   * @return The {@link User} instance, or {@code null} if not found.
   */
  User getOne(String login);

  /**
   * Creates a new user in the system.
   *
   * @param user The {@link User} instance to be created.
   * @return The created {@link User} instance.
   */
  User createOne(User user);

  /**
   * Generates the next unique item ID for a user.
   *
   * @return The next unique ID as an integer.
   */
  int nextItemId();

  /**
   * Authenticates a user with the given email and password.
   *
   * @param email The email of the user.
   * @return A containing authentication details.
   */
  ObjectNode login(String email);


  /**
   * Generates an authentication token for a given user.
   *
   * @param user The {@link UserDTO} instance for which to generate a token.
   * @return An {@link ObjectNode} containing the generated token.
   */
  ObjectNode generateTokenForUser(UserDTO user);
}
