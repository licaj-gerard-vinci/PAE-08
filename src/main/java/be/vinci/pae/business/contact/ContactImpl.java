package be.vinci.pae.business.contact;

import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.year.YearDTO;


/**
 * Represents the ContactImpl class.
 */
public class ContactImpl implements Contact {

  private int id;

  private EntrepriseDTO entreprise;

  private int idEntreprise;

  private UserDTO utilisateur;

  private int idUtilisateur;

  private String etatContact;

  private String lieuxRencontre;

  private String raisonRefus;

  private YearDTO annee;

  private int idAnnee;

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
   * Gets the associated year ID.
   *
   * @return the associated year ID.
   */
  @Override
  public int getIdAnnee() {
    return idAnnee;
  }

  /**
   * Sets the associated year ID.
   *
   * @param idAnnee the new associated year ID.
   */
  @Override
  public void setIdAnnee(int idAnnee) {
    this.idAnnee = idAnnee;
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

  /**
   * Check if a contact can be updated to the 'taken' state.
   *
   * @param actualState The actuel.
   * @param expectedState The expected state of the contact.
   * @return true if the contact can be updated, false otherwise.
   */
  @Override
  public boolean checkState(String actualState, String expectedState) {
    if (actualState.equals("pris") && !expectedState.equals("accepté")
        && !expectedState.equals("refusé") && !expectedState.equals("non suivi")
        && !expectedState.equals("blacklisté") && !expectedState.equals("suspendu")) {
      return false;
    }

    if (actualState.equals("initié") && !expectedState.equals("pris")
        && !expectedState.equals("non suivi") && !expectedState.equals("blacklisté")
        && !expectedState.equals("suspendu")) {
      return false;
    }

    if (actualState.equals("non suivi") || actualState.equals("refusé")
        || actualState.equals("accepté") || actualState.equals("suspendu")) {
      return false;
    }

    return !expectedState.equals("initié");
  }
}