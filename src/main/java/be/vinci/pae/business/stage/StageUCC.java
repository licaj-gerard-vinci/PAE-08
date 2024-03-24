package be.vinci.pae.business.stage;

import java.util.List;

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
   * Gets all stages.
   *
   * @return all stages
   */
  List<StageDTO> getStages();
}
