package be.vinci.pae.business.contact;

import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.year.YearDTO;
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
   * Gets the associated enterprise ID.
   *
   * @return the enterprise ID.
   */
  int getIdEntreprise();

  /**
   * Sets the associated enterprise ID.
   *
   * @param idEntreprise the new enterprise ID.
   */
  void setIdEntreprise(int idEntreprise);

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
   * Gets the associated user ID.
   *
   * @return the user ID.
   */
  int getIdUtilisateur();

  /**
   * Sets the associated user ID.
   *
   * @param idUtilisateur the new user ID.
   */
  void setIdUtilisateur(int idUtilisateur);

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
  YearDTO getAnnee();

  /**
   * Sets the meeting date.
   *
   * @param annee the new meeting date.
   */
  void setAnnee(YearDTO annee);

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
