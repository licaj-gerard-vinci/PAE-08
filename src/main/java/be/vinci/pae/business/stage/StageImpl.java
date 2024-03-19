package be.vinci.pae.business.stage;

/**
 * Represents the StageImpl class.
 */
public class StageImpl implements StageDetailedDTO {

  /**
   * Gets the stage ID.
   *
   * @return the stage ID.
   */
  private int id;

  /**
   * Gets the stage Responsable.
   *
   * @return the stage Responsable.
   */
  private int responsable;

  /**
   * Gets the stage Etudiant.
   *
   * @return the stage Etudiant.
   */
  private int etudiant;

  /**
   * Gets the stage Contact.
   *
   * @return the stage Contact.
   */
  private int contact;

  /**
   * Gets the stage Entreprise.
   *
   * @return the stage Entreprise.
   */
  private int entreprise;

  /**
   * Gets the stage Sujet.
   *
   * @return the stage Sujet.
   */
  private String sujet;

  /**
   * Gets the stage dateSignature.
   *
   * @return the stage dateSignature.
   */
  private String dateSignature;
  private String responsableNom;
  private String responsablePrenom;
  private String entrepriseNom;
  private String entrepriseAppellation;

  /**
   * Gets the stage ID.
   *
   * @return the stage ID.
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the stage ID.
   *
   * @param id the new stage ID.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the stage Responsable.
   *
   * @return the stage Responsable.
   */
  public int getResponsable() {
    return responsable;
  }

  /**
   * Sets the stage Responsable.
   *
   * @param responsable the new stage Responsable.
   */

  public void setResponsable(int responsable) {
    this.responsable = responsable;
  }

  /**
   * Gets the stage Etudiant.
   *
   * @return the stage Etudiant.
   */

  public int getEtudiant() {
    return etudiant;
  }

  /**
   * Sets the stage Etudiant.
   *
   * @param etudiant the new stage Etudiant.
   */

  public void setEtudiant(int etudiant) {
    this.etudiant = etudiant;
  }

  /**
   * Gets the stage Contact.
   *
   * @return the stage Contact.
   */

  public int getContact() {
    return contact;
  }

  /**
   * Sets the stage Contact.
   *
   * @param contact the new stage Contact.
   */

  public void setContact(int contact) {
    this.contact = contact;
  }


  /**
   * Gets the stage Entreprise.
   *
   * @return the stage Entreprise.
   */
  public int getEntreprise() {
    return entreprise;
  }

  /**
   * Sets the stage Entreprise.
   *
   * @param entreprise the new stage Entreprise.
   */

  public void setEntreprise(int entreprise) {
    this.entreprise = entreprise;
  }

  /**
   * Gets the stage Sujet.
   *
   * @return the stage Sujet.
   */

  public String getSujet() {
    return sujet;
  }

  /**
   * Sets the stage Sujet.
   *
   * @param sujet the new stage Sujet.
   */

  public void setSujet(String sujet) {
    this.sujet = sujet;
  }

  /**
   * Gets the stage dateSignature.
   *
   * @return the stage dateSignature.
   */

  public String getdateSignature() {
    return dateSignature;
  }

  /**
   * Sets the stage dateSignature.
   *
   * @param dateSignature the new stage dateSignature.
   */
  public void setdateSignature(String dateSignature) {
    this.dateSignature = dateSignature;
  }

  /**
   * Gets the responsable nom.
   *
   * @return the responsable nom
   */
  public String getResponsableNom() {
    return responsableNom;
  }

  /**
   * Sets the responsable nom.
   *
   * @param responsableNom the new responsable nom
   */
  public void setResponsableNom(String responsableNom) {
    this.responsableNom = responsableNom;
  }

  /**
   * Gets the responsable prenom.
   *
   * @return the responsable prenom
   */
  public String getResponsablePrenom() {
    return responsablePrenom;
  }

  /**
   * Sets the responsable prenom.
   *
   * @param responsablePrenom the new responsable prenom
   */

  public void setResponsablePrenom(String responsablePrenom) {
    this.responsablePrenom = responsablePrenom;
  }

  /**
   * Gets the entreprise nom.
   *
   * @return the entreprise nom
   */

  public String getEntrepriseNom() {
    return entrepriseNom;
  }

  /**
   * Sets the entreprise nom.
   *
   * @param entrepriseNom the new entreprise nom
   */

  public void setEntrepriseNom(String entrepriseNom) {
    this.entrepriseNom = entrepriseNom;
  }

  /**
   * Gets the entreprise appellation.
   *
   * @return the entreprise appellation
   */

  public String getEntrepriseAppellation() {
    return entrepriseAppellation;
  }

  /**
   * Sets the entreprise appellation.
   *
   * @param entrepriseAppellation the new entreprise appellation
   */

  public void setEntrepriseAppellation(String entrepriseAppellation) {
    this.entrepriseAppellation = entrepriseAppellation;
  }


}
