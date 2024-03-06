package be.vinci.pae.business;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
/**
 * Represents the StageDTO interface.
 */
@JsonDeserialize(as = StageImpl.class)
public interface StageDTO {

    /**
    * Gets the stage ID.
    *
    * @return the stage ID.
    */
    int getId();

    /**
    * Sets the stage ID.
    *
    * @param id the new stage ID.
    */
    void setId(int id);

    /**
    * Gets the stage Responsable.
    *
    * @return the stage Responsable.
    */
    ResponsableDTO getResponsable();

    /**
    * Sets the stage Responsable.
    *
    * @param responsable the new stage Responsable.
    */
    void setResponsable(ResponsableDTO responsable);

    /**
    * Gets the stage Etudiant.
    *
    * @return the stage Etudiant.
    */
    UserDTO getEtudiant();

    /**
    * Sets the stage Etudiant.
    *
    * @param etudiant the new stage Etudiant.
    */
    void setEtudiant(UserDTO etudiant);

    /**
    * Gets the stage Contact.
    *
    * @return the stage Contact.
    */
    ContactDTO getContact();

    /**
    * Sets the stage Contact.
    *
    * @param contact the new stage Contact.
    */
    void setContact(ContactDTO contact);

    /**
    * Gets the stage Entreprise.
    *
    * @return the stage Entreprise.
    */
    EntrepriseDTO getEntreprise();

   /**
   * Sets the stage Entreprise.
   *
   * @param entreprise the new stage Entreprise.
   */
    void setEntreprise(EntrepriseDTO entreprise);

  /**
  * Gets the stage Sujet.
  *
  * @return the stage Sujet.
  */
    String getSujet();

  /**
  * Sets the stage Sujet.
  *
  * @param sujet the new stage Sujet.
  */
    void setSujet(String sujet);

  /**
  * Gets the stage date.
  *
  * @return the stage date.
  */
    String getdateSignature();

  /**
  * Sets the stage date.
  *
  * @param dateSignature the new stage date.
  */
    void setdateSignature(String dateSignature);
    
    
}
