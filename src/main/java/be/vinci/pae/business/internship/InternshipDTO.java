package be.vinci.pae.business.internship;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.manager.ManagerDTO;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.year.YearDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.sql.Date;

/**
 * Represents the StageDTO interface.
 */
@JsonDeserialize(as = InternshipImpl.class)
public interface InternshipDTO {

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
  ManagerDTO getManager();

  /**
   * Sets the stage Responsable.
   *
   * @param manager the new stage Responsable.
   */
  void setManager(ManagerDTO manager);

  /**
   * Gets the stage Responsable ID.
   *
   * @return the stage Responsable ID.
   */
  int getIdManager();

  /**
   * Sets the stage Responsable ID.
   *
   * @param idManager the new stage Responsable ID.
   */
  void setIdManager(int idManager);



  /**
   * Gets the stage Etudiant.
   *
   * @return the stage Etudiant.
   */
  UserDTO getStudent();

  /**
   * Sets the stage Etudiant.
   *
   * @param student the new stage Etudiant.
   */
  void setStudent(UserDTO student);

  /**
   * Gets the stage Etudiant ID.
   *
   * @return the stage Etudiant ID.
   */
  int getIdStudent();

  /**
   * Sets the stage Etudiant ID.
   *
   * @param idStudent the new stage Etudiant ID.
   */
  void setIdStudent(int idStudent);

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
  CompanyDTO getCompany();

  /**
   * Sets the stage Entreprise.
   *
   * @param company the new stage Entreprise.
   */
  void setCompany(CompanyDTO company);

  /**
   * Gets the stage Entreprise ID.
   *
   * @return the stage Entreprise ID.
   */
  int getIdCompany();

  /**
   * Sets the stage Entreprise ID.
   *
   * @param idCompany the new stage Entreprise ID.
   */
  void setIdCompany(int idCompany);

  /**
   * Gets the stage Sujet.
   *
   * @return the stage Sujet.
   */
  String getTopics();

  /**
   * Sets the stage Sujet.
   *
   * @param topics the new stage Sujet.
   */
  void setTopics(String topics);

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
