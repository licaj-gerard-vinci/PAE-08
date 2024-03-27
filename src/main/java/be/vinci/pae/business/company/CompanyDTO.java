package be.vinci.pae.business.company;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents the EntrepriseDTO interface.
 */
@JsonDeserialize(as = CompanyImpl.class)
public interface CompanyDTO {

  /**
   * Gets the ID of the entreprise.
   *
   * @return the ID of the entreprise.
   */
  int getId();

  /**
   * Sets the ID of the entreprise.
   *
   * @param id the new ID for the entreprise.
   */
  void setId(int id);

  /**
   * Gets the name of the entreprise.
   *
   * @return the name of the entreprise.
   */
  String getName();

  /**
   * Sets the name of the entreprise.
   *
   * @param name the new name for the entreprise.
   */
  void setName(String name);

  /**
   * Gets the apellation of the entreprise.
   *
   * @return the apellation of the entreprise.
   */
  String getDesignation();

  /**
   * Sets the apellation of the company.
   *
   * @param designation the new designation for the company.
   */
  void setDesignation(String designation);

  /**
   * Gets the address of the company.
   *
   * @return the address of the company.
   */
  String getAddress();

  /**
   * Sets the address of the company.
   *
   * @param address the new address for the company.
   */
  void setAddress(String address);

  /**
   * Gets the city of the company.
   *
   * @return the city of the company.
   */
  String getCity();

  /**
   * Sets the city of the company.
   *
   * @param city the new city for the company.
   */
  void setCity(String city);


  /**
   * Gets the phone number of the company.
   *
   * @return the phone number of the company.
   */
  String getPhoneNumber();

  /**
   * Sets the phone number of the company.
   *
   * @param phoneNumber the new phone number for the company.
   */
  void setPhoneNumber(String phoneNumber);

  /**
   * Gets the email of the company.
   *
   * @return the email of the company.
   */
  String getEmail();

  /**
   * Sets the email of the company.
   *
   * @param email the new email for the company.
   */
  void setEmail(String email);

  /**
   * Gets the state of the company.
   *
   * @return the state of the company.
   */
  boolean isBlackListed();

  /**
   * Sets the state of the company.
   *
   * @param blackListed the new state for the company.
   */
  void setBlackListed(boolean blackListed);

  /**
   * Gets the bkacklistReason of the company.
   *
   * @return the blacklistReason of the company.
   */
  String getBlacklistReason();

  /**
   * Sets the blacklistReason of the company.
   *
   * @param blacklistReason the new blacklistReason for the company.
   */
  void setBlacklistReason(String blacklistReason);

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