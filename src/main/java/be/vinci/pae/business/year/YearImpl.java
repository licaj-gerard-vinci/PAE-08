package be.vinci.pae.business.year;

import be.vinci.pae.business.year.YearDTO;

/**
 * Represents the AnneeImpl class.
 */
public class YearImpl implements YearDTO {

  private int id;
  private String annee;

  /**
   * Gets the ID of the year.
   *
   * @return the ID of the year.
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the ID of the year.
   *
   * @param id the new ID for the year.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the year.
   *
   * @return the year.
   */
  public String getAnnee() {
    return annee;
  }

  /**
   * Sets the year.
   *
   * @param annee the new year.
   */
  public void setAnnee(String annee) {
    this.annee = annee;
  }

}
