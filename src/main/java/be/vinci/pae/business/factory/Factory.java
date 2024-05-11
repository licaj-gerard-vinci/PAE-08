package be.vinci.pae.business.factory;

import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.internship.InternshipDTO;
import be.vinci.pae.business.manager.ManagerDTO;
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
  InternshipDTO getInternshipDTO();


  /**
   * Create a new CompanyDTO.
   *
   * @return a new instance of CompanyDTO.
   */
  CompanyDTO getCompanyDTO();

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
   * Create a new managerDTO.
   *
   * @return a new instance of managerDTO.
   */
  ManagerDTO getManagerDTO();
}
