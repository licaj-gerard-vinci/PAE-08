package be.vinci.pae.dal;

import be.vinci.pae.business.UserDTO;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Interface for user data service operations, including retrieval, creation, and authentication of
 * users.
 */
public interface UserDAO {

  /**
   * Retrieves a single user by their unique identifier.
   *
   * @param id The unique identifier of the user.
   * @return The {@link UserDTO} instance, or {@code null} if not found.
   */
  UserDTO getOneById(int id);

  /**
   * Retrieves a single user by their login.
   *
   * @param email The login of the user.
   * @return The {@link UserDTO} instance, or {@code null} if not found.
   */
  UserDTO getOneByEmail(String email);


  /**
   * Authenticates a user with the given email and password.
   *
   * @param email    The email of the user.
   * @param password The password of the user.
   * @return An {@link ObjectNode} containing authentication details.
   */
  UserDTO login(String email, String password);

}
