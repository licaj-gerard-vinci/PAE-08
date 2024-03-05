package be.vinci.pae.business;

import be.vinci.pae.views.Views;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EntrepriseImpl implements EntrepriseDTO {

  @JsonView(Views.Public.class)
  private int id;

  @JsonView(Views.Public.class)
  private String nom;

  @JsonView(Views.Public.class)
  private String apellation;

  @JsonView(Views.Public.class)
  private String adresse;

  @JsonView(Views.Public.class)
  private String numTel;

  @JsonView(Views.Public.class)
  private String email;

  @JsonView(Views.Public.class)
  private boolean blackListed;

  @JsonView(Views.Public.class)
  private String motivation_blacklist;


  /**
   * Gets the ID of the entreprise.
   *
   * @return the ID of the entreprise.
   */

  public int getId() {
    return id;
  }

  /**
   * Sets the ID of the entreprise.
   *
   * @param id the new ID for the entreprise.
   */

  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the name of the entreprise.
   *
   * @return the name of the entreprise.
   */

  public String getNom() {
    return nom;
  }

  /**
   * Sets the name of the entreprise.
   *
   * @param nom the new name for the entreprise.
   */

  public void setNom(String nom) {
    this.nom = nom;
  }

  /**
   * Gets the apellation of the entreprise.
   *
   * @return the apellation of the entreprise.
   */

  public String getApellation() {
    return apellation;
  }

  /**
   * Sets the apellation of the entreprise.
   *
   * @param apellation the new apellation for the entreprise.
   */

  public void setApellation(String apellation) {
    this.apellation = apellation;
  }

  /**
   * Gets the adresse of the entreprise.
   *
   * @return the adresse of the entreprise.
   */

  public String getAdresse() {
    return adresse;
  }

  /**
   * Sets the adresse of the entreprise.
   *
   * @param adresse the new adresse for the entreprise.
   */

  public void setAdresse(String adresse) {
    this.adresse = adresse;
  }

  /**
   * Gets the numTel of the entreprise.
   *
   * @return the numTel of the entreprise.
   */

  public String getNumTel() {
    return numTel;
  }

  /**
   * Sets the numTel of the entreprise.
   *
   * @param numTel the new numTel for the entreprise.
   */

  public void setNumTel(String numTel) {
    this.numTel = numTel;
  }

  /**
   * Gets the email of the entreprise.
   *
   * @return the email of the entreprise.
   */

  public String getEmail() {
    return email;
  }

  /**
   * Sets the email of the entreprise.
   *
   * @param email the new email for the entreprise.
   */

  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets the blackListed of the entreprise.
   *
   * @return the blackListed of the entreprise.
   */

  public boolean isBlackListed() {
    return blackListed;
  }

  /**
   * Sets the blackListed of the entreprise.
   *
   * @param blackListed the new blackListed for the entreprise.
   */

  public void setBlackListed(boolean blackListed) {
    this.blackListed = blackListed;
  }

  /**
   * Gets the motivation_blacklist of the entreprise.
   *
   * @return the motivation_blacklist of the entreprise.
   */
  public String getMotivation_blacklist() {
    return motivation_blacklist;
  }


  /**
   * Sets the motivation_blacklist of the entreprise.
   *
   * @param motivation_blacklist the new motivation_blacklist for the entreprise.
   */

  public void setMotivation_blacklist(String motivation) {
    this.motivation_blacklist = motivation;
  }

  /**
   * toString method
   *
   * @return String
   */
  @Override
  public String toString() {
    return "EntrepriseImpl [id=" + id + ", nom=" + nom + ", apellation=" + apellation + ", adresse="
        + adresse
        + ", numTel=" + numTel + ", email=" + email + ", blackListed=" + blackListed
        + ", motivation_blacklist="
        + motivation_blacklist + "]";
  }


}
