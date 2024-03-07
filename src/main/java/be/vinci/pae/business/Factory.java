package be.vinci.pae.business;

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
  StageDTO getStageDTO();

  /**
   * Create a new DetailedStageDTO.
   *
   * @return a new instance of DetailedStageDTO.
   */

  StageDetailedDTO getDetailedStageDTO();
}
