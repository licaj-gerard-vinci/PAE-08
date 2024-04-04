package be.vinci.pae.dal.stage;

import be.vinci.pae.business.internship.InternshipDTO;
import java.util.List;

/**
 * The Interface StageDAO.
 */
public interface InternshipDAO {


  /**
   * Gets all stages.
   *
   * @return all stages
   */

  List<InternshipDTO> getStages();

  /**
   * Gets the stage by id.
   *
   * @param id the id
   * @return the stage by id
   */
  InternshipDTO getStageById(int id);

  /**
   * Inserts a new internship into the database.
   *
   * @param internship the contact to insert
   */
  void insertInternship(InternshipDTO internship);

}
