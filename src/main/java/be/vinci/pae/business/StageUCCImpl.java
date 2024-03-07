package be.vinci.pae.business;

import be.vinci.pae.dal.StageDAO;
import jakarta.inject.Inject;

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

  public StageDTO GetStageUser(int idUser) {
    return stageDAO.getStageOfUser(idUser);
  }


  /**
   * Gets the detailed stage for user.
   *
   * @param idUser the id user
   * @return the detailed stage for user
   */
  @Override
  public StageDetailedDTO getDetailedStageForUser(int idUser) {
    return (StageDetailedDTO) stageDAO.getDetailOfStage(idUser);
  }

}
