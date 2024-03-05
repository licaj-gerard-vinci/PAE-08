package be.vinci.pae.business;

import be.vinci.pae.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents the AnneeDTO interface.
 */

@JsonDeserialize(as = AnneeImpl.class)
public interface AnneeDTO {

  @JsonView(Views.Public.class)
  int getId();

  @JsonView(Views.Public.class)
  void setId(int id);

  String getAnnee();

  void setAnnee(String annee);

}
