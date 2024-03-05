package be.vinci.pae.business;

import be.vinci.pae.views.Views;
import com.fasterxml.jackson.annotation.JsonView;

public class ContactImpl implements ContactDTO {

  @JsonView(Views.Public.class)
  private int id;

  @JsonView(Views.Public.class)
  private EntrepriseDTO entreprise;
  @JsonView(Views.Public.class)
  private UserDTO utilisateur;

  @JsonView(Views.Public.class)
  private String etatContact;

  @JsonView(Views.Public.class)
  private String lieuxRencontre;

  @JsonView(Views.Public.class)
  private String raisonRefus;

  @JsonView(Views.Public.class)
  private AnneeDTO annee;



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
  public AnneeDTO getAnnee() {
    return annee;
  }

  /**
   * Sets the associated year.
   *
   * @param annee the new associated year.
   */
  @Override
  public void setAnnee(AnneeDTO annee) {
    this.annee = annee;
  }

  @Override
  public String toString() {
    return "ContactImpl [id=" + id + ", entreprise=" + entreprise + ", utilisateur=" + utilisateur
        + ", etatContact=" + etatContact + ", lieuxRencontre=" + lieuxRencontre + ", raisonRefus="
        + raisonRefus + ", annee=" + annee + "]";
  }
}
