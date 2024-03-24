package be.vinci.pae.business.stage;

import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.stage.StageDAO;
import jakarta.inject.Inject;
import java.util.List;

/**
 * The Class StageUCCImpl.
 */
public class StageUCCImpl implements StageUCC {

  @Inject
  private StageDAO stageDAO;

  @Inject
  private DALServices dalServices;

  /**
   * Gets the stage user.
   *
   * @param idUser the id user
   * @return the stage user
   */

  public StageDTO getStageUser(int idUser) {
    try {
      dalServices.startTransaction();
      StageDTO stage = stageDAO.getStageById(idUser);
      dalServices.commitTransaction();
      return stage;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.close();
    }
  }


  /**
   * Gets all stages.
   *
   * @return all stages
   */
  @Override
  public List<StageDTO> getStages() {
    try {
      dalServices.startTransaction();
      List<StageDTO> stages = stageDAO.getStages();
      dalServices.commitTransaction();
      return stages;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.close();
    }
  }
}
