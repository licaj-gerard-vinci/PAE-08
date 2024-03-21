package be.vinci.pae.business.factory;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.contact.ContactDetailledDTO;
import be.vinci.pae.business.contact.ContactImpl;
import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.entreprise.EntrepriseImpl;
import be.vinci.pae.business.stage.StageDTO;
import be.vinci.pae.business.stage.StageDetailedDTO;
import be.vinci.pae.business.stage.StageImpl;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserImpl;

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
   * Creates and returns a new instance of {@code StageDTO}.
   *
   * @return a new {@code StageDTO} instance.
   */
  @Override
  public StageDTO getStageDTO() {
    return new StageImpl();
  }

  /**
   * Creates and returns a new instance of {@code StageDetailedDTO}.
   *
   * @return a new {@code StageDetailedDTO} instance.
   */
  @Override
  public StageDetailedDTO getDetailedStageDTO() {
    return new StageImpl();
  }

  /**
   * Creates and returns a new instance of {@code ContactDTO}.
   *
   * @return a new {@code ContactDTO} instance.
   */
  @Override
  public ContactDetailledDTO getDetailledContactDTO() {
    return new ContactImpl();
  }

  /**
   * Create a new EntrepriseDTO.
   *
   * @return a new instance of EntrepriseDTO.
   */
  @Override
  public EntrepriseDTO getEntrepriseDTO() {
    return new EntrepriseImpl();
  }

  /**
   * Create a new ContactDTO.
   *
   * @return a new instance of ContactDTO.
   */
  public ContactDTO getContactDTO() {
    return new ContactImpl();
  }
}

