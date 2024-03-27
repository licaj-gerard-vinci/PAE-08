package be.vinci.pae.business.factory;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.contact.ContactImpl;
import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.company.CompanyImpl;
import be.vinci.pae.business.manager.ManagerDTO;
import be.vinci.pae.business.manager.ManagerImpl;
import be.vinci.pae.business.internship.InternshipDTO;
import be.vinci.pae.business.internship.InternshipImpl;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserImpl;
import be.vinci.pae.business.year.YearDTO;
import be.vinci.pae.business.year.YearImpl;

/**
 * Implementation of the {@code UserFactory} interface, providing methods to create instances of
 * {@code UserDTO}.
 */
public class FactoryImpl implements Factory {

  /**
   * Creates and returns a new instance of {@code UserDTO} representing a public user. This method
   * is intended for creating user instances with limited information, suitable for public-facing
   * contexts where sensitive details are not exposed.
   *
   * @return a new {@code UserDTO} instance representing a public user.
   */
  @Override
  public UserDTO getPublicUser() {
    return new UserImpl();
  }


  /**
   * Creates and returns a new instance of {@code InternshipDTO}.
   *
   * @return a new {@code InternshipDTO} instance.
   */
  @Override
  public InternshipDTO getInternshipDTO() {
    return new InternshipImpl();
  }


  /**
   * Create a new CompanyDTO.
   *
   * @return a new instance of CompanyDTO.
   */
  @Override
  public CompanyDTO getCompanyDTO() {
    return new CompanyImpl();
  }

  /**
   * Create a new ContactDTO.
   *
   * @return a new instance of ContactDTO.
   */
  @Override
  public ContactDTO getContactDTO() {
    return new ContactImpl();
  }

  /**
   * year DTO.
   *
   * @return year DTO
   */
  @Override
  public YearDTO getYearDTO() {
    return new YearImpl();
  }

  /**
   * Create a new ManagerDTO
   *
   * @return a new instance of managerDTO.
   */
  @Override
  public ManagerDTO getManagerDTO() {
    return new ManagerImpl();
  }
}

