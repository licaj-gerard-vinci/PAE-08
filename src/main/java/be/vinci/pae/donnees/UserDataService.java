package be.vinci.pae.donnees;

import be.vinci.pae.business.User;
import be.vinci.pae.business.UserDTO;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;

/**
 * Interface for user data service operations, including retrieval, creation, and authentication of
 * users.
 */
public interface UserDataService {



  /**
   * Authenticates a user with the given email and password.
   *
   * @param email    The email of the user.
   * @param password The password of the user.
   * @return An {@link ObjectNode} containing authentication details.
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
