package be.vinci.pae.business.stage;

import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.stage.StageDAO;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.exceptions.NotFoundException;
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
      if (stage == null) {
        throw new NotFoundException("No stage found for this user");
      }
      dalServices.commitTransaction();
      return stage;
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
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
      if (stages == null) {
        throw new NotFoundException("No stages found");
      }
      dalServices.commitTransaction();
      return stages;
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }
}
