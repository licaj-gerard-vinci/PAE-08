package be.vinci.pae.dal;

import be.vinci.pae.business.UserDTO;

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

}
