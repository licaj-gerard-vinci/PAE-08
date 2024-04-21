package be.vinci.pae.business.stage;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.responsable.ResponsableDTO;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.year.YearDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.sql.Date;

/**
 * Represents the StageDTO interface.
 */
@JsonDeserialize(as = StageImpl.class)
public interface StageDTO {

  /**
   * Gets the stage ID.
   *
   * @return the stage ID.
   */
  int getId();

  /**
   * Sets the stage ID.
   *
   * @param id the new stage ID.
   */
  void setId(int id);

  /**
   * Gets the stage Responsable.
   *
   * @return the stage Responsable.
   */
  ResponsableDTO getResponsable();

  /**
   * Sets the stage Responsable.
   *
   * @param responsable the new stage Responsable.
   */
  void setResponsable(ResponsableDTO responsable);

  /**
   * Gets the stage Responsable ID.
   *
   * @return the stage Responsable ID.
   */
  int getIdResponsable();

  /**
   * Sets the stage Responsable ID.
   *
   * @param idResponsable the new stage Responsable ID.
   */
  void setIdResponsable(int idResponsable);



  /**
   * Gets the stage Etudiant.
   *
   * @return the stage Etudiant.
   */
  UserDTO getEtudiant();

  /**
   * Sets the stage Etudiant.
   *
   * @param etudiant the new stage Etudiant.
   */
  void setEtudiant(UserDTO etudiant);

  /**
   * Gets the stage Etudiant ID.
   *
   * @return the stage Etudiant ID.
   */
  int getIdEtudiant();

  /**
   * Sets the stage Etudiant ID.
   *
   * @param idEtudiant the new stage Etudiant ID.
   */
  void setIdEtudiant(int idEtudiant);

  /**
   * Gets the stage Contact.
   *
   * @return the stage Contact.
   */
  ContactDTO getContact();

  /**
   * Sets the stage Contact.
   *
   * @param contact the new stage Contact.
   */
  void setContact(ContactDTO contact);

  /**
   * Gets the stage Contact ID.
   *
   * @return the stage Contact ID.
   */
  int getIdContact();

  /**
   * Sets the stage Contact ID.
   *
   * @param idContact the new stage Contact ID.
   */
  void setIdContact(int idContact);

  /**
   * Gets the stage Entreprise.
   *
   * @return the stage Entreprise.
   */
  EntrepriseDTO getEntreprise();

  /**
   * Sets the stage Entreprise.
   *
   * @param entreprise the new stage Entreprise.
   */
  void setEntreprise(EntrepriseDTO entreprise);

  /**
   * Gets the stage Entreprise ID.
   *
   * @return the stage Entreprise ID.
   */
  int getIdEntreprise();

  /**
   * Sets the stage Entreprise ID.
   *
   * @param idEntreprise the new stage Entreprise ID.
   */
  void setIdEntreprise(int idEntreprise);

  /**
   * Gets the stage Sujet.
   *
   * @return the stage Sujet.
   */
  String getSujet();

  /**
   * Sets the stage Sujet.
   *
   * @param sujet the new stage Sujet.
   */
  void setSujet(String sujet);

  /**
   * Gets the stage date.
   *
   * @return the stage date.
   */
  Date getdateSignature();

  /**
   * Sets the stage date.
   *
   * @param dateSignature the new stage date.
   */
  void setdateSignature(Date dateSignature);

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
   * Gets the associated year ID.
   *
   * @return the year ID.
   */
  int getIdAnnee();

  /**
   * Sets the associated year ID.
   *
   * @param idAnnee the new year ID.
   */
  void setIdAnnee(int idAnnee);


}
