package be.vinci.pae.business;
/**
 * The Interface DetailedStageDTO.
 */
public class DetailedStageDTO extends StageImpl {

  /**
   * Instantiates a new detailed stage DTO.
   */
  private String responsableNom;
  private String responsablePrenom;
  private String entrepriseNom;
  private String entrepriseAppellation;

 
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
