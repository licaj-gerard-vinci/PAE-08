package be.vinci.pae.business.company;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents the CompanyDTO interface.
 */
@JsonDeserialize(as = CompanyImpl.class)
public interface CompanyDTO {

  /**
   * Gets the ID of the Company.
   *
   * @return the ID of the Company.
   */
  int getId();

  /**
   * Sets the ID of the Company.
   *
   * @param id the new ID for the Company.
   */
  void setId(int id);

  /**
   * Gets the name of the Company.
   *
   * @return the name of the Company.
   */
  String getName();

  /**
   * Sets the name of the Company.
   *
   * @param name the new name for the Company.
   */
  void setName(String name);

  /**
   * Gets the designation of the Company.
   *
   * @return the designation of the Company.
   */
  String getDesignation();

  /**
   * Sets the designation of the Company.
   *
   * @param designation the new designation for the Company.
   */
  void setDesignation(String designation);

  /**
   * Gets the address of the Company.
   *
   * @return the address of the Company.
   */
  String getAddress();

  /**
   * Sets the address of the Company.
   *
   * @param address the new address for the Company.
   */
  void setAddress(String address);

  /**
   * Gets the city of the Company.
   *
   * @return the city of the Company.
   */
  String getCity();

  /**
   * Sets the city of the Company.
   *
   * @param city the new city for the Company.
   */
  void setCity(String city);


  /**
   * Gets the postal code of the Company.
   *
   * @return the postal code of the Company.
   */
  String getPhone();

  /**
   * Sets the Phone of the Company.
   *
   * @param phone the new Phone for the Company.
   */
  void setPhone(String phone);

  /**
   * Gets the email of the Company.
   *
   * @return the email of the Company.
   */
  String getEmail();

  /**
   * Sets the email of the Company.
   *
   * @param email the new email for the Company.
   */
  void setEmail(String email);

  /**
   * Gets the state of the Company.
   *
   * @return the state of the Company.
   */
  boolean isBlackListed();

  /**
   * Sets the state of the Company.
   *
   * @param blackListed the new state for the Company.
   */
  void setBlackListed(boolean blackListed);

  /**
   * Gets the motivation_blacklist of the Company.
   *
   * @return the motivation_blacklist of the Company.
   */
  String getMotivation();

  /**
   * Sets the motivation_blacklist of the Company.
   *
   * @param motivationBlacklist the new motivation_blacklist for the Company.
   */
  void setMotivation(String motivationBlacklist);

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