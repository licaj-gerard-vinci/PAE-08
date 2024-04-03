package be.vinci.pae.business.internship;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.manager.ManagerDTO;
import be.vinci.pae.business.user.UserDTO;

/**
 * Represents the StageImpl class.
 */
public class InternshipImpl implements InternshipDTO {

  private int id;

  private ManagerDTO responsable;

  private int idResponsable;

  private UserDTO etudiant;

  private int idEtudiant;

  private ContactDTO contact;

  private int idContact;

  private EntrepriseDTO entreprise;

  private int idEntreprise;

  private String sujet;

  private String dateSignature;

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
  public ManagerDTO getResponsable() {
    return responsable;
  }

  @Override
  public void setResponsable(ManagerDTO responsable) {
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
  public String getdateSignature() {
    return dateSignature;
  }

  /**
   * Sets the stage date.
   *
   * @param dateSignature the new stage date.
   */
  @Override
  public void setdateSignature(String dateSignature) {
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
