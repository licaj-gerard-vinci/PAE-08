package be.vinci.pae.domain;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Creating User object.
 */
public class User {

  private int id;

  private String login;

  private String password;

  // Return username as string.
  public String getLogin() {
    return login;
  }

  // Set username as string.
  public void setLogin(String login) {
    this.login = login;
  }

  // Return ID.
  public int getId() {
    return id;
  }

  // Set ID.
  public void setId(int id) {
    this.id = id;
  }

  // Return password as string.
  public String getPassword() {
    return password;
  }

  // Set password as string.
  public void setPassword(String password) {
    this.password = password;
  }

  // Return True if password is correct.
  public boolean checkPassword(String password) {
    return BCrypt.checkpw(password, this.password);
  }

  // Hash a password.
  public String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  // toString.
  @Override
  public String toString() {
    return "{id:" + id + ", login:" + login + ", password:" + password + "}";
  }

}