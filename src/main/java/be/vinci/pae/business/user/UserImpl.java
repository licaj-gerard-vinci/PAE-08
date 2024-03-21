package be.vinci.pae.business.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import org.mindrot.jbcrypt.BCrypt;


/**
 * Creating User object.
 */
public class UserImpl implements User {


  private int id;

  private String email;

  private String lastname;

  private String firstname;

  private String phone;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yy hh:mm:ss")

  private Date registrationDate;

  private String role;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  private String schoolyear;

  private boolean hasInternship;

  /**
   * Gets the user's login.
   *
   * @return the user's login.
   */
  @Override
  public String getEmail() {
    return email;
  }

  /**
   * Sets the user's login.
   *
   * @param email the new login for the user.
   */
  @Override
  public void setEmail(String email) {
    this.email = email;
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
   * Gets the user's last name.
   *
   * @return the user's last name.
   */
  @Override
  public String getLastname() {
    return lastname;
  }

  /**
   * Sets the user's last name.
   *
   * @param lastname the new last name for the user.
   */
  @Override
  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  /**
   * Gets the user's first name.
   *
   * @return the user's first name.
   */
  @Override
  public String getFirstname() {
    return firstname;
  }

  /**
   * Sets the user's first name.
   *
   * @param firstname the new first name for the user.
   */
  @Override
  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  /**
   * Gets the user's phone number.
   *
   * @return the user's phone number.
   */
  @Override
  public String getPhone() {
    return phone;
  }

  /**
   * Sets the user's phone number.
   *
   * @param phone the new phone number for the user.
   */
  @Override
  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * Gets the user's registration date.
   *
   * @return the user's registration date.
   */
  @Override
  public Date getRegistration_date() {
    return registrationDate;
  }

  /**
   * Sets the user's registration date.
   *
   * @param registrationDate the new registration date for the user.
   */
  @Override
  public void setRegistration_date(Date registrationDate) {
    this.registrationDate = registrationDate;
  }

  /**
   * Gets the user's role.
   *
   * @return the user's role.
   */
  @Override
  public String getRole() {
    return role;
  }

  /**
   * Sets the user's role.
   *
   * @param role the new role for the user.
   */
  @Override
  public void setRole(String role) {
    this.role = role;
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
   * Hashes a password.
   *
   * @param password the password to hash.
   * @return the hashed password.
   */

  public String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  /**
   * Gets the user's school year.
   *
   * @return the user's school year.
   */
  public String getYear() {
    return schoolyear;
  }

  /**
   * Sets the user's school year.
   *
   * @param schoolyear the new school year for the user.
   */
  public void setYear(String schoolyear) {
    this.schoolyear = schoolyear;
  }

  /**
   * Gets the user's internship status.
   *
   * @return the user's internship status.
   */
  public boolean getHasInternship() {
    return hasInternship;
  }

  /**
   * Sets the user's internship status.
   *
   * @param hasInternship the internship status to set for the user.
   */
  public void setHasInternship(boolean hasInternship) {
    this.hasInternship = hasInternship;
  }

  /**
   * return string of attributs.
   */
  @Override
  public String toString() {
    return "id=" + id + ", email='" + email + '\'' + ", nom='" + lastname + '\'' + ", prenom='"
        + firstname + '\'' + ", numTel='" + phone + '\'' + ", dateInscription=" + registrationDate
        + ", role='" + role + '\'' + ", password='" + password + '\'' + '}';
  }
}