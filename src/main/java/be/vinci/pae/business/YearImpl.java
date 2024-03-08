package be.vinci.pae.business;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Represents the AnneeImpl class.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
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
