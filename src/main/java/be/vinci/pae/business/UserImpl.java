package be.vinci.pae.business;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Creating User object.
 */
public class UserImpl implements User {

  private int id;

  private String login;

  private String password;

  /**
   * Gets the user's login.
   *
   * @return the user's login.
   */
  @Override
  public String getLogin() {
    return login;
  }

  /**
   * Sets the user's login.
   *
   * @param login the new login for the user.
   */
  @Override
  public void setLogin(String login) {
    this.login = login;
  }

  /**
   * Gets the user's ID.
   *
   * @return the user's ID.
   */
  @Override
  public int getId() {
    return id;
  }

  /**
   * Sets the user's ID.
   *
   * @param id the new ID for the user.
   */
  @Override
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the user's hashed password.
   *
   * @return the hashed password.
   */
  @Override
  public String getPassword() {
    return password;
  }

  /**
   * Sets the user's password after hashing it.
   *
   * @param password the new password to hash and set.
   */
  @Override
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Checks if the provided password matches the user's hashed password.
   *
   * @param password the password to check.
   * @return true if the password matches, false otherwise.
   */
  @Override
  public boolean checkPassword(String password) {
    return BCrypt.checkpw(password, this.password);
  }

  /**
   * Hash a password.
   *
   * @param password
   * @return
   */

  @Override
  public String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  /**
   * Creates a string representation of the user with ID, login, and password.
   *
   * @return the string representation of the user.
   */
  @Override
  public String toString() {
    return "{id:" + id + ", login:" + login + ", password:" + password + "}";
  }

}