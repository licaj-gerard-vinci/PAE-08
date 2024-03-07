package be.vinci.pae.business;

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
  

  @Override
  public StageDTO getStageDTO() {
    return new StageImpl();
  }

  @Override
  public StageDetailedDTO getDetailedStageDTO() {
    return new StageImpl();
  }
}

