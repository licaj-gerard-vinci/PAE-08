package be.vinci.pae.dal.stage;

import be.vinci.pae.business.stage.StageDTO;
import be.vinci.pae.business.stage.StageDetailedDTO;

/**
 * The Interface StageDAO.
 */
public interface StageDAO {


  /**
   * Gets the stage of user.
   *
   * @param id the id
   * @return the stage of user
   */

  StageDTO getStageOfUser(int id);

  /**
   * Gets the detail of stage.
   *
   * @param id the id
   * @return the detail of stage
   */

  StageDetailedDTO getDetailOfStage(int id);

}
