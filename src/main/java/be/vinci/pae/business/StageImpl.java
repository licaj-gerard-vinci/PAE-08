package be.vinci.pae.business;

import be.vinci.pae.views.Views;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * Represents the StageImpl class.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StageImpl implements StageDTO {

  /**
   * Gets the stage ID.
   *
   * @return the stage ID.
   */
  @JsonView(Views.Public.class)
  private int id;

  /**
   * Gets the stage Responsable.
   *
   * @return the stage Responsable.
   */
  @JsonView(Views.Public.class)
  private int responsable;

  /**
   * Gets the stage Etudiant.
   *
   * @return the stage Etudiant.
   */
  @JsonView(Views.Public.class)
  private int etudiant;

  /**
   * Gets the stage Contact.
   *
   * @return the stage Contact.
   */
  @JsonView(Views.Public.class)
  private int contact;

  /**
   * Gets the stage Entreprise.
   *
   * @return the stage Entreprise.
   */
  @JsonView(Views.Public.class)
  private int entreprise;

  /**
   * Gets the stage Sujet.
   *
   * @return the stage Sujet.
   */
  @JsonView(Views.Public.class)
  private String sujet;

  /**
   * Gets the stage dateSignature.
   *
   * @return the stage dateSignature.
   */
  @JsonView(Views.Public.class)
  private String dateSignature;

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


}
