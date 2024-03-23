package be.vinci.pae.business.stage;

import be.vinci.pae.dal.stage.StageDAO;
import jakarta.inject.Inject;
import java.util.List;

/**
 * The Class StageUCCImpl.
 */
public class StageUCCImpl implements StageUCC {

  @Inject
  private StageDAO stageDAO;

  /**
   * Gets the stage user.
   *
   * @param idUser the id user
   * @return the stage user
   */

  public StageDTO getStageUser(int idUser) {
    return stageDAO.getStageById(idUser);
  }

  /**
   * Gets all stages.
   *
   * @return all stages
   */
  @Override
  public List<StageDTO> getStages() {
    return stageDAO.getStages();
  }

}
