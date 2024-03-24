package be.vinci.pae.dal.stage;

import be.vinci.pae.business.stage.StageDTO;
import java.util.List;

/**
 * The Interface StageDAO.
 */
public interface StageDAO {


  /**
   * Gets all stages.
   *
   * @return all stages
   */

  List<StageDTO> getStages();

  /**
   * Gets the stage by id.
   *
   * @param id the id
   * @return the stage by id
   */
  StageDTO getStageById(int id);



}
