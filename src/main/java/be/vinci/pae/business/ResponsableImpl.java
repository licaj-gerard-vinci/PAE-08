package be.vinci.pae.business;

import be.vinci.pae.views.Views;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * Represents the ResponsableImpl class.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponsableImpl implements ResponsableDTO {

  @JsonView(Views.Public.class)
  private int id;
  @JsonView(Views.Public.class)
  private String nom;
  @JsonView(Views.Public.class)
  private String prenom;
  @JsonView(Views.Public.class)
  private String numTel;
  @JsonView(Views.Public.class)
  private String email;
  @JsonView(Views.Public.class)
  private EntrepriseDTO entreprise;

  /**
   * Gets the responsable ID.
   *
   * @return the responsable ID.
   */
  @Override
  public int getId() {
    return id;
  }

  /**
   * Sets the responsable ID.
   *
   * @param id the new responsable ID.
   */
  @Override
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the responsable name.
   *
   * @return the responsable name.
   */
  @Override
  public String getNom() {
    return nom;
  }

  /**
   * Sets the responsable name.
   *
   * @param nom the new responsable name.
   */
  @Override
  public void setNom(String nom) {
    this.nom = nom;
  }

  /**
   * Gets the responsable first name.
   *
   * @return the responsable first name.
   */
  @Override
  public String getPrenom() {
    return prenom;
  }

  /**
   * Sets the responsable first name.
   *
   * @param prenom the new responsable first name.
   */
  @Override
  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  /**
   * Gets the responsable email.
   *
   * @return the responsable email.
   */
  @Override
  public String getNumTel() {
    return numTel;
  }

  /**
   * Sets the responsable email.
   *
   * @param numTel the new responsable email.
   */
  @Override
  public void setNumTel(String numTel) {
    this.numTel = numTel;
  }

  /**
   * Gets the responsable email.
   *
   * @return the responsable email.
   */
  @Override
  public String getEmail() {
    return email;
  }

  /**
   * Sets the responsable email.
   *
   * @param email the new responsable email.
   */
  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets the entreprise of the responsable.
   *
   * @return the entreprise of the responsable.
   */
  @Override
  public EntrepriseDTO getEntreprise() {
    return entreprise;
  }

  /**
   * Sets the entreprise of the responsable.
   *
   * @param entreprise the new entreprise of the responsable.
   */
  @Override
  public void setEntreprise(EntrepriseDTO entreprise) {
    this.entreprise = entreprise;
  }

}