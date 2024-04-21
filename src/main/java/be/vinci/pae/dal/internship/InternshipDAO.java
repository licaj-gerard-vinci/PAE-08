package be.vinci.pae.dal.internship;

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

  List<InternshipDTO> getInternship();

  /**
   * Gets the stage by id.
   *
   * @param id the id
   * @return the stage by id
   */
  InternshipDTO getInternshipById(int id);

  /**
   * Inserts a new internship into the database.
   *
   * @param internship the contact to insert
   */
  void insertInternship(InternshipDTO internship);

  /**
   * Updates an internship in the database.
   *
   * @param internship the internship to update
   */
  void updateInternshipTopic(InternshipDTO internship);

}
