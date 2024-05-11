package be.vinci.pae.business.manager;

import be.vinci.pae.business.company.CompanyDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents the managerDTO interface.
 */
@JsonDeserialize(as = ManagerImpl.class)
public interface ManagerDTO {

  /**
   * Gets the manager ID.
   *
   * @return the manager ID.
   */
  int getId();

  /**
   * Sets the manager ID.
   *
   * @param id the new manager ID.
   */
  void setId(int id);

  /**
   * Gets the manager name.
   *
   * @return the manager name.
   */
  String getName();

  /**
   * Sets the manager name.
   *
   * @param name the new manager name.
   */

  void setName(String name);

  /**
   * Gets the manager first name.
   *
   * @return the manager first name.
   */
  String getFirstName();

  /**
   * Sets the manager first name.
   *
   * @param firstName the new manager first name.
   */
  void setFirstName(String firstName);

  /**
   * Gets the manager phone.
   *
   * @return the manager phone.
   */
  String getPhone();

  /**
   * Sets the manager phone.
   *
   * @param phone the new manager phone.
   */
  void setPhone(String phone);

  /**
   * Gets the manager email.
   *
   * @return the manager email.
   */
  String getEmail();

  /**
   * Sets the manager email.
   *
   * @param email the new manager email.
   */
  void setEmail(String email);


  /**
   * Gets the company.
   *
   * @return the company.
   */
  CompanyDTO getCompany();

  /**
   * Sets the company.
   *
   * @param company the new company.
   */
  void setCompany(CompanyDTO company);

  /**
   * Gets the company ID.
   *
   * @return the company ID.
   */
  int getIdCompany();

  /**
   * Sets the company ID.
   *
   * @param idCompany the new company ID.
   */
  void setIdCompany(int idCompany);

  /**
   * Gets the version.
   *
   * @return the version.
   */
  int getVersion();

  /**
   * Sets the version.
   *
   * @param version the new version.
   */
  void setVersion(int version);


}