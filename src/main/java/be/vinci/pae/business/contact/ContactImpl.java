package be.vinci.pae.business.contact;

/**
 * Represents the ContactImpl class.
 */

public class ContactImpl implements ContactDetailledDTO {

  private int id;

  private int entreprise;
  private int utilisateur;

  private String etatContact;

  private String lieuxRencontre;

  private String raisonRefus;

  private int annee;

  private String nomEntreprise;

  private String appellation;


  /**
   * Gets the contact ID.
   *
   * @return the contact ID.
   */
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
   * Gets the associated enterprise ID.
   *
   * @return the enterprise ID.
   */
  @Override
  public int getEntreprise() {
    return entreprise;
  }

  /**
   * Sets the associated enterprise ID.
   *
   * @param entreprise the new enterprise ID.
   */
  @Override
  public void setEntreprise(int entreprise) {
    this.entreprise = entreprise;
  }

  /**
   * Gets the associated user ID.
   *
   * @return the user ID.
   */
  @Override
  public int getUtilisateur() {
    return utilisateur;
  }

  /**
   * Sets the associated user ID.
   *
   * @param utilisateur the new user ID.
   */
  @Override
  public void setUtilisateur(int utilisateur) {
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
  public int getAnnee() {
    return annee;
  }

  /**
   * Sets the associated year.
   *
   * @param annee the new associated year.
   */
  @Override
  public void setAnnee(int annee) {
    this.annee = annee;
  }

  /**
   * Gets the name of the associated enterprise.
   *
   * @return the name of the associated enterprise.
   */
  public String getNomEntreprise() {
    return nomEntreprise;
  }

  /**
   * Sets the name of the associated enterprise.
   *
   * @param nomEntreprise the new name of the associated enterprise.
   */
  public void setNomEntreprise(String nomEntreprise) {
    this.nomEntreprise = nomEntreprise;
  }

  /**
   * Gets the appellation of the associated enterprise.
   *
   * @return the appellation of the associated enterprise.
   */

  public String getAppellation() {
    return appellation;

  }

  /**
   * Sets the appellation of the associated enterprise.
   *
   * @param appellation the new appellation of the associated enterprise.
   */
  public void setAppellation(String appellation) {
    this.appellation = appellation;
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
