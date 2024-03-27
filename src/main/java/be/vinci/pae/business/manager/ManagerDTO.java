package be.vinci.pae.business.manager;

import be.vinci.pae.business.company.CompanyDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents the ResponsableDTO interface.
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
   * Gets the manager lastname.
   *
   * @return the manager lastname.
   */
  String getLastname();

  /**
   * Sets the manager lastname.
   *
   * @param lastname the new manager lastname.
   */

  void setLastname(String lastname);

  /**
   * Gets the manager first name.
   *
   * @return the manager first name.
   */
  String getFirstname();

  /**
   * Sets the manager first name.
   *
   * @param firstname the new manager first name.
   */
  void setFirstname(String firstname);

  /**
   * Gets the manager phone number.
   *
   * @return the manager phone number.
   */
  String getPhoneNumber();

  /**
   * Sets the manager phone number.
   *
   * @param phoneNumber the new manager phone number.
   */
  void setPhoneNumber(String phoneNumber);

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
  int getCompanyId();

  /**
   * Sets the company ID.
   *
   * @param companyId the new company ID.
   */
  void setCompanyId(int companyId);

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