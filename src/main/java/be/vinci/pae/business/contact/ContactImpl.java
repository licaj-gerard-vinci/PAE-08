package be.vinci.pae.business.contact;

import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.year.YearDTO;


/**
 * Represents the ContactImpl class.
 */
public class ContactImpl implements ContactDTO {

  private int id;

  private EntrepriseDTO entreprise;

  private int idEntreprise;

  private UserDTO utilisateur;
  private int idUtilisateur;
  private String etatContact;

  private String lieuxRencontre;

  private String raisonRefus;

  private YearDTO annee;

  private int version;



  @Override
  public int getIdEntreprise() {
    return idEntreprise;
  }

  @Override
  public void setIdEntreprise(int idEntreprise) {
    this.idEntreprise = idEntreprise;
  }

  @Override
  public int getIdUtilisateur() {
    return idUtilisateur;
  }

  @Override
  public void setIdUtilisateur(int idUtilisateur) {
    this.idUtilisateur = idUtilisateur;
  }

  @Override
  public int getId() {
    return id;
  }

  /**
   * Sets the contact ID.
   *
   * @param id the new contact ID.
   */
  @Override
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the associated enterprise.
   *
   * @return the enterprise.
   */
  @Override
  public EntrepriseDTO getEntreprise() {
    return entreprise;
  }

  /**
   * Sets the associated enterprise ID.
   *
   * @param entreprise the new enterprise ID.
   */
  @Override
  public void setEntreprise(EntrepriseDTO entreprise) {
    this.entreprise = entreprise;
  }

  /**
   * Gets the associated user ID.
   *
   * @return the user ID.
   */
  @Override
  public UserDTO getUtilisateur() {
    return utilisateur;
  }

  /**
   * Sets the associated user ID.
   *
   * @param utilisateur the new user ID.
   */
  @Override
  public void setUtilisateur(UserDTO utilisateur) {
    this.utilisateur = utilisateur;
  }

  /**
   * Gets the state of the contact.
   *
   * @return the contact state.
   */
  @Override
  public String getEtatContact() {
    return etatContact;
  }

  /**
   * Sets the state of the contact.
   *
   * @param etatContact the new contact state.
   */
  @Override
  public void setEtatContact(String etatContact) {
    this.etatContact = etatContact;
  }

  /**
   * Gets the meeting location.
   *
   * @return the meeting location.
   */
  @Override
  public String getLieuxRencontre() {
    return lieuxRencontre;
  }

  /**
   * Sets the meeting location.
   *
   * @param lieuxRencontre the new meeting location.
   */
  @Override
  public void setLieuxRencontre(String lieuxRencontre) {
    this.lieuxRencontre = lieuxRencontre;
  }

  /**
   * Gets the reason for refusal.
   *
   * @return the reason for refusal.
   */
  @Override
  public String getRaisonRefus() {
    return raisonRefus;
  }

  /**
   * Sets the reason for refusal.
   *
   * @param raisonRefus the new reason for refusal.
   */
  @Override
  public void setRaisonRefus(String raisonRefus) {
    this.raisonRefus = raisonRefus;
  }


  /**
   * Gets the associated year.
   *
   * @return the associated year.
   */
  @Override
  public YearDTO getAnnee() {
    return annee;
  }

  /**
   * Sets the associated year.
   *
   * @param annee the new associated year.
   */
  @Override
  public void setAnnee(YearDTO annee) {
    this.annee = annee;
  }

  /**
   * Gets the version.
   *
   * @return the version.
   */
  public int getVersion() {
    return version;
  }
  /**
   * Sets the version.
   *
   * @param version the new version.
   */
  public void setVersion(int version) {
    this.version = version;
  }

  /**
   * Returns a string representation of the object.
   *
   * @return a string representation of the object.
   */
  @Override
  public String toString() {
    return "ContactImpl [id=" + id + ", entreprise=" + entreprise + ", utilisateur=" + utilisateur
        + ", etatContact=" + etatContact + ", lieuxRencontre=" + lieuxRencontre + ", raisonRefus="
        + raisonRefus + ", annee=" + annee + "]";
  }
}
