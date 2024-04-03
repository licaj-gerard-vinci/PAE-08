package be.vinci.pae.business.factory;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.manager.ManagerDTO;
import be.vinci.pae.business.internship.InternshipDTO;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.year.YearDTO;


/**
 * A factory for creating DTO objects.
 */
public interface Factory {

  /**
   * Create a new UserDTO.
   *
   * @return a new instance of UserDTO.
   */
  UserDTO getPublicUser();

  /**
   * Create a new ContactDTO.
   *
   * @return a new instance of ContactDTO.
   */
  InternshipDTO getStageDTO();


  /**
   * Create a new EntrepriseDTO.
   *
   * @return a new instance of EntrepriseDTO.
   */
  EntrepriseDTO getEntrepriseDTO();

  /**
   * Create a new ContactDTO.
   *
   * @return a new instance of ContactDTO.
   */
  ContactDTO getContactDTO();

  /**
   * year DTO.
   *
   * @return year DTO
   */
  YearDTO getYearDTO();

  /**
   * Create a new ResponsableDTO.
   *
   * @return a new instance of ResponsableDTO.
   */
  ManagerDTO getManagerDTO();
}
