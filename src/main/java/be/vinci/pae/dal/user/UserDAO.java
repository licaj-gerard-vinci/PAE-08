package be.vinci.pae.dal.user;

import be.vinci.pae.business.user.UserDTO;
import java.util.List;

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
   * Retrieves a list of all users.
   *
   * @return a list of all UserDTO objects.
   */
  List<UserDTO> getAllUsers();

  /**
   * Retrieves a single user by their login.
   *
   * @param email The login of the user.
   * @return The {@link UserDTO} instance, or {@code null} if not found.
   */
  UserDTO getOneByEmail(String email);

  /**
   * Registers a new user.
   *
   * @param email The email of the user.
   * @param password The password of the user.
   * @param name The name of the user.
   * @param firstname The firstname of the user.
   * @param phone The phone of the user.
   * @return The {@link UserDTO} instance, or {@code null} if not found.
   */
  UserDTO insertUser(String email, String password, String name, String firstname, String phone, String role);


}
