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

  StageDTO getInternshipByUserId(int idUser);

  /**
   * Gets all stages.
   *
   * @return all stages
   */
  List<StageDTO> getStages();

  /**
   * Inserts a new internship into the database.
   *
   * @param internship the contact to insert
   */
  void insertInternship(StageDTO internship);

  /**
   * Updates an internship in the database.
   *
   * @param internship the internship to update
   * @param id the id of the internship to update
   */
  void updateInternshipTopic(StageDTO internship, int id);
}
