package be.vinci.pae.business;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents the ContactDTO interface.
 */
@JsonDeserialize(as = ContactImpl.class)
public interface ContactDTO {

/**
 * Gets the contact ID.
 */
  int getId();

  /**
   * Sets the contact ID.
   */
  void setId(int id);

  /**
   * Gets the associated enterprise ID.
   */
  EntrepriseDTO getEntreprise();

  /**
   * Sets the associated enterprise ID.
   */
  void setEntreprise(EntrepriseDTO entreprise);

  /**
   * Gets the associated user ID.
   */
  UserDTO getUtilisateur();

  /**
   * Sets the associated user ID.
   */
  void setUtilisateur(UserDTO utilisateur);

  /**
   * Gets the contact state.
   */
  String getEtatContact();

  /**
   * Sets the contact state.
   */
  void setEtatContact(String etatContact);

  /**
   * Gets the meeting place.
   */
  String getLieuxRencontre();

  /**
   * Sets the meeting place.
   */
  void setLieuxRencontre(String lieuxRencontre);

  /**
   * Gets the raison of refuse.
   */
  String getRaisonRefus();

  /**
   * Sets the raison of refuse.
   */
  void setRaisonRefus(String raisonRefus);

  /**
   * Gets the meeting date.
   */
  AnneeDTO getAnnee();

  /**
   * Sets the meeting date.
   */
  void setAnnee(AnneeDTO annee);


}
