package be.vinci.pae.business.stage;

/**
 * The Interface StageDetailedDTO.
 */
public interface StageDetailedDTO extends StageDTO {


  /**
   * Gets the responsable nom.
   *
   * @return the responsable nom
   */
  String getResponsableNom();
  
  /**
   * Sets the responsable nom.
   *
   * @param responsableNom the new responsable nom
   */

  void setResponsableNom(String responsableNom);

  /**
   * Gets the responsable prenom.
   *
   * @return the responsable prenom
   */

  String getResponsablePrenom();

  /**
   * Sets the responsable prenom.
   *
   * @param responsablePrenom the new responsable prenom
   */

  void setResponsablePrenom(String responsablePrenom);

  /**
   * Gets the entreprise nom.
   *
   * @return the entreprise nom
   */

  String getEntrepriseNom();

  /**
   * Sets the entreprise nom.
   *
   * @param entrepriseNom the new entreprise nom
   */

  void setEntrepriseNom(String entrepriseNom);

  /**
   * Gets the entreprise appellation.
   *
   * @return the entreprise appellation
   */

  String getEntrepriseAppellation();

  /**
   * Sets the entreprise appellation.
   *
   * @param entrepriseAppellation the new entreprise appellation
   */

  void setEntrepriseAppellation(String entrepriseAppellation);


}
