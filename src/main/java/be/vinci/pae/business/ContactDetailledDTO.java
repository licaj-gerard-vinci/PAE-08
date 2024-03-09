package be.vinci.pae.business;

/**
 * Represents the ContactDetailledDTO interface.
 */

public interface ContactDetailledDTO extends ContactDTO {

  /**
   * Gets the associated enterprise ID.
   *
   * @return the associated enterprise ID.
   */
  String getNomEntreprise();

  /**
   * Sets the associated enterprise ID.
   *
   * @param nomEntreprise the new associated enterprise ID.
   */

  void setNomEntreprise(String nomEntreprise);

  /**
   * Gets the associated enterprise appellation.
   *
   * @return the associated enterprise appellation.
   */
  String getAppellation();

  /**
   * Sets the associated enterprise appellation.
   *
   * @param appellation the new associated enterprise appellation.
   */
  void setAppellation(String appellation);

}
