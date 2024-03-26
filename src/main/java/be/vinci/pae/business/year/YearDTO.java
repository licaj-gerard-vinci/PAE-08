package be.vinci.pae.business.year;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents the AnneeDTO interface.
 */
@JsonDeserialize(as = YearImpl.class)
public interface YearDTO {

  /**
   * Gets the ID of the year.
   *
   * @return the ID of the year.
   */
  int getId();

  /**
   * Sets the ID of the year.
   *
   * @param id the new ID for the year.
   */
  void setId(int id);

  /**
   * Gets the year.
   *
   * @return the year.
   */
  String getAnnee();

  /**
   * Sets the year.
   *
   * @param annee the new year.
   */
  void setAnnee(String annee);

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
