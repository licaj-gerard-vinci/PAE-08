package be.vinci.pae.dal;

import be.vinci.pae.business.StageDTO;
import be.vinci.pae.business.StageDetailedDTO;

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
