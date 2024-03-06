package be.vinci.pae.business;

import be.vinci.pae.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
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
  @JsonView(Views.Public.class)
  int getId();

  /**
   * Sets the ID of the year.
   *
   * @param id the new ID for the year.
   */
  @JsonView(Views.Public.class)
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
}
