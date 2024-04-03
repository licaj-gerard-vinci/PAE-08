package be.vinci.pae.business.internship;

import java.util.List;

/**
 * The Interface StageUCC.
 */
public interface InternshipUCC {

  /**
   * Gets the stage user.
   *
   * @param idUser the id user
   * @return the stage user
   */

  InternshipDTO getStageUser(int idUser);

  /**
   * Gets all stages.
   *
   * @return all stages
   */
  List<InternshipDTO> getStages();

  /**
   * Inserts a new internship into the database.
   *
   * @param internship the contact to insert
   */
  void insertInternship(InternshipDTO internship);
}
