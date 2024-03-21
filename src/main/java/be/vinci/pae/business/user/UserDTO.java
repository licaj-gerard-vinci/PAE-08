package be.vinci.pae.business.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;

/**
 * The {@code UserDTO} interface represents a Data Transfer Object (DTO) for a user within the
 * application. It defines methods to access and modify various attributes of a user, such as name,
 * phone number, registration dates, academic year, role, email, ID, and password.
 */
@JsonDeserialize(as = UserImpl.class)
public interface UserDTO {

  /**
   * Gets the last name of the user.
   *
   * @return the last name of the user.
   */
  String getLastname();

  /**
   * Sets the last name of the user.
   *
   * @param lastname the new last name for the user.
   */
  void setLastname(String lastname);

  /**
   * Gets the first name of the user.
   *
   * @return the first name of the user.
   */
  String getFirstname();

  /**
   * Sets the first name of the user.
   *
   * @param firstname the new first name for the user.
   */
  void setFirstname(String firstname);

  /**
   * Gets the phone number of the user.
   *
   * @return the phone number of the user.
   */
  String getPhone();

  /**
   * Sets the phone number of the user.
   *
   * @param phone the new phone number for the user.
   */
  void setPhone(String phone);

  /**
   * Gets the registration date of the user.
   *
   * @return the registration date of the user.
   */
  Date getRegistrationDate();

  /**
   * Sets the registration date of the user.
   *
   * @param registrationDate the new registration date for the user.
   */
  void setRegistrationDate(Date registrationDate);


  /**
   * Gets the role of the user.
   *
   * @return the role of the user.
   */
  String getRole();

  /**
   * Sets the role of the user.
   *
   * @param role the new role for the user.
   */
  void setRole(String role);

  /**
   * Gets the email of the user.
   *
   * @return the email of the user.
   */
  String getEmail();

  /**
   * Sets the email of the user.
   *
   * @param email the new email for the user.
   */
  void setEmail(String email);

  /**
   * Gets the ID of the user.
   *
   * @return the ID of the user.
   */
  int getId();

  /**
   * Sets the ID of the user.
   *
   * @param id the new ID for the user.
   */
  void setId(int id);

  /**
   * Gets the hashed password of the user.
   *
   * @return the hashed password of the user.
   */
  String getPassword();

  /**
   * Sets the hashed password of the user.
   *
   * @param password the new hashed password for the user.
   */
  void setPassword(String password);

  /**
   * Gets the academic year of the user.
   *
   * @return the academic year of the user.
   */
  String getYear();

  /**
   * Sets the academic year of the user.
   *
   * @param year the new academic year for the user.
   */
  void setYear(String year);

  /**
   * Gets the academic year of the user.
   *
   * @return the academic year of the user.
   */
  boolean getHasInternship();


  /**
   * Sets whether the user has an internship.
   *
   * @param hasInternship the internship status to set for the user.
   */
  void setHasInternship(boolean hasInternship);
}