package be.vinci.pae.business.responsable;

import be.vinci.pae.business.entreprise.EntrepriseDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents the ResponsableDTO interface.
 */
@JsonDeserialize(as = ResponsableImpl.class)
public interface ResponsableDTO {

  /**
   * Gets the responsable ID.
   *
   * @return the responsable ID.
   */
  int getId();

  /**
   * Sets the responsable ID.
   *
   * @param id the new responsable ID.
   */
  void setId(int id);

  /**
   * Gets the responsable name.
   *
   * @return the responsable name.
   */
  String getNom();

  /**
   * Sets the responsable name.
   *
   * @param nom the new responsable name.
   */

  void setNom(String nom);

  /**
   * Gets the responsable first name.
   *
   * @return the responsable first name.
   */
  String getPrenom();

  /**
   * Sets the responsable first name.
   *
   * @param prenom the new responsable first name.
   */
  void setPrenom(String prenom);

  /**
   * Gets the responsable email.
   *
   * @return the responsable email.
   */
  String getNumTel();

  /**
   * Sets the responsable email.
   *
   * @param numTel the new responsable email.
   */
  void setNumTel(String numTel);

  /**
   * Gets the responsable email.
   *
   * @return the responsable email.
   */
  String getEmail();

  /**
   * Sets the responsable email.
   *
   * @param email the new responsable email.
   */
  void setEmail(String email);


  /**
   * Gets the entreprise.
   *
   * @return the entreprise.
   */
  EntrepriseDTO getEntreprise();

  /**
   * Sets the entreprise.
   *
   * @param entreprise the new entreprise.
   */
  void setEntreprise(EntrepriseDTO entreprise);

  /**
   * Gets the entreprise ID.
   *
   * @return the entreprise ID.
   */
  int getIdEntreprise();

  /**
   * Sets the entreprise ID.
   *
   * @param idEntreprise the new entreprise ID.
   */
  void setIdEntreprise(int idEntreprise);


}
