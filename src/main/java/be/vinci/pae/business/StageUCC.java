package be.vinci.pae.business;

/**
 * The Interface StageUCC.
 */
public interface StageUCC {

  /**
   * Gets the stage user.
   *
   * @param idUser the id user
   * @return the stage user
   */

  StageDTO getStageUser(int idUser);

  /**
   * Gets the detailed stage for user.
   *
   * @param idUser the id user
   * @return the detailed stage for user
   */

  StageDetailedDTO getDetailedStageForUser(int idUser);
}
