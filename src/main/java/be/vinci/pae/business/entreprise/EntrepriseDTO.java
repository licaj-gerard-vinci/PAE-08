package be.vinci.pae.business.entreprise;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents the EntrepriseDTO interface.
 */
@JsonDeserialize(as = EntrepriseImpl.class)
public interface EntrepriseDTO {

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
  String getNom();

  /**
   * Sets the name of the entreprise.
   *
   * @param nom the new name for the entreprise.
   */
  void setNom(String nom);

  /**
   * Gets the apellation of the entreprise.
   *
   * @return the apellation of the entreprise.
   */
  String getAppellation();

  /**
   * Sets the apellation of the entreprise.
   *
   * @param appellation the new apellation for the entreprise.
   */
  void setAppellation(String appellation);

  /**
   * Gets the address of the entreprise.
   *
   * @return the address of the entreprise.
   */
  String getAdresse();

  /**
   * Sets the address of the entreprise.
   *
   * @param adresse the new address for the entreprise.
   */
  void setAdresse(String adresse);

  /**
   * Gets the city of the entreprise.
   *
   * @return the city of the entreprise.
   */
  String getCity();

  /**
   * Sets the city of the entreprise.
   *
   * @param city the new city for the entreprise.
   */
  void setCity(String city);


  /**
   * Gets the postal code of the entreprise.
   *
   * @return the postal code of the entreprise.
   */
  String getNumTel();

  /**
   * Sets the postal code of the entreprise.
   *
   * @param numTel the new postal code for the entreprise.
   */
  void setNumTel(String numTel);

  /**
   * Gets the email of the entreprise.
   *
   * @return the email of the entreprise.
   */
  String getEmail();

  /**
   * Sets the email of the entreprise.
   *
   * @param email the new email for the entreprise.
   */
  void setEmail(String email);

  /**
   * Gets the state of the entreprise.
   *
   * @return the state of the entreprise.
   */
  boolean isBlackListed();

  /**
   * Sets the state of the entreprise.
   *
   * @param blackListed the new state for the entreprise.
   */
  void setBlackListed(boolean blackListed);

  /**
   * Gets the motivation_blacklist of the entreprise.
   *
   * @return the motivation_blacklist of the entreprise.
   */
  String getMotivation_blacklist();


  /**
   * Sets the motivation_blacklist of the entreprise.
   *
   * @param motivationBlacklist the new motivation_blacklist for the entreprise.
   */
  void setMotivation_blacklist(String motivationBlacklist);

  /**
   * Get the motivation.
   *
   * @return motivation
   */
  String getMotivation();

  /**
   * Sets the motivation.
   *
   * @param motivation the new motivation for the entreprise.
   */
  void setMotivation(String motivation);

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
