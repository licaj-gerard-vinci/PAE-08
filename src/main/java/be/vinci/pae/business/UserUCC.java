package be.vinci.pae.business;

public interface UserUCC {

  /**
   * Registers a new user.
   *
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

}