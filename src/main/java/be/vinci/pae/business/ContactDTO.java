package be.vinci.pae.business;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents the ContactDTO interface.
 */
@JsonDeserialize(as = ContactImpl.class)
public interface ContactDTO {

  /**
  * Gets the contact ID.
  *
  * @return the contact ID.
  */
  int getId();

  /**
   * Sets the contact ID.
   * 
   * @param id the new contact ID.
   */
  void setId(int id);

  /**
   * Gets the associated enterprise ID.
   * 
   * @return the enterprise ID.
   */
  EntrepriseDTO getEntreprise();

  /**
   * Sets the associated enterprise ID.
   * 
   * @param entreprise the new enterprise ID.
   */
  void setEntreprise(EntrepriseDTO entreprise);

  /**
   * Gets the associated user ID.
   * 
   * @return the user ID.
   */
  UserDTO getUtilisateur();

  /**
   * Sets the associated user ID.
   * 
   * @param utilisateur the new user ID.
   */
  void setUtilisateur(UserDTO utilisateur);

  /**
   * Gets the contact state.
   * 
   * @return the contact state.
   */
  String getEtatContact();

  /**
   * Sets the contact state.
   * 
   * @param etatContact the new contact state.
   */
  void setEtatContact(String etatContact);

  /**
   * Gets the meeting place.
   * 
   * @return the meeting place.
   */
  String getLieuxRencontre();

  /**
   * Sets the meeting place.
   * 
   * @param lieuxRencontre the new meeting place.
   */
  void setLieuxRencontre(String lieuxRencontre);

  /**
   * Gets the raison of refuse.
   * 
   * @return the raison of refuse.
   */
  String getRaisonRefus();

  /**
   * Sets the raison of refuse.
   * 
   * @param raisonRefus the new raison of refuse.
   */
  void setRaisonRefus(String raisonRefus);

  /**
   * Gets the meeting date.
   * 
   * @return the meeting date.
   */
  AnneeDTO getAnnee();

  /**
   * Sets the meeting date.
   * 
   * @param annee the new meeting date.
   */
  void setAnnee(AnneeDTO annee);


}
