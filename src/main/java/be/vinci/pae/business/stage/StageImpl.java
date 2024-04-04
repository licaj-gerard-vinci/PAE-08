package be.vinci.pae.business.stage;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.responsable.ResponsableDTO;
import be.vinci.pae.business.user.UserDTO;
import java.sql.Date;

/**
 * Represents the StageImpl class.
 */
public class StageImpl implements StageDTO {

  private int id;

  private ResponsableDTO responsable;

  private int idResponsable;

  private UserDTO etudiant;

  private int idEtudiant;

  private ContactDTO contact;

  private int idContact;

  private EntrepriseDTO entreprise;

  private int idEntreprise;

  private String sujet;

  private Date dateSignature;

  private int version;

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public ResponsableDTO getResponsable() {
    return responsable;
  }

  @Override
  public void setResponsable(ResponsableDTO responsable) {
    this.responsable = responsable;
  }

  @Override
  public int getIdResponsable() {
    return idResponsable;
  }

  @Override
  public void setIdResponsable(int idResponsable) {
    this.idResponsable = idResponsable;
  }

  @Override
  public UserDTO getEtudiant() {
    return etudiant;
  }

  @Override
  public void setEtudiant(UserDTO etudiant) {
    this.etudiant = etudiant;
  }

  @Override
  public int getIdEtudiant() {
    return idEtudiant;
  }

  @Override
  public void setIdEtudiant(int idEtudiant) {
    this.idEtudiant = idEtudiant;
  }

  @Override
  public ContactDTO getContact() {
    return contact;
  }

  @Override
  public void setContact(ContactDTO contact) {
    this.contact = contact;
  }

  @Override
  public int getIdContact() {
    return idContact;
  }

  @Override
  public void setIdContact(int idContact) {
    this.idContact = idContact;
  }

  @Override
  public EntrepriseDTO getEntreprise() {
    return entreprise;
  }

  @Override
  public void setEntreprise(EntrepriseDTO entreprise) {
    this.entreprise = entreprise;
  }

  @Override
  public int getIdEntreprise() {
    return idEntreprise;
  }

  @Override
  public void setIdEntreprise(int idEntreprise) {
    this.idEntreprise = idEntreprise;
  }

  @Override
  public String getSujet() {
    return sujet;
  }

  @Override
  public void setSujet(String sujet) {
    this.sujet = sujet;
  }

  /**
   * Gets the stage date.
   *
   * @return the stage date.
   */
  @Override
  public Date getdateSignature() {
    return dateSignature;
  }

  /**
   * Sets the stage date.
   *
   * @param dateSignature the new stage date.
   */
  @Override
  public void setdateSignature(Date dateSignature) {
    this.dateSignature = dateSignature;
  }

  /**
   * Gets the version.
   *
   * @return the version.
   */
  @Override
  public int getVersion() {
    return version;
  }

  /**
   * Sets the version.
   *
   * @param version the new version.
   */
  @Override
  public void setVersion(int version) {
    this.version = version;
  }

}
