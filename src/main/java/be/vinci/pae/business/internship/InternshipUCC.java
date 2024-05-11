package be.vinci.pae.business.internship;

import java.util.List;

/**
 * The Interface internshipUCC.
 */
public interface InternshipUCC {

  /**
   * Gets the internship user.
   *
   * @param idUser the id user
   * @return the internship user
   */

  InternshipDTO getInternshipByUserId(int idUser);

  /**
   * Gets all internships.
   *
   * @return all internships
   */
  List<InternshipDTO> getInternship();

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
   * @param id the id of the internship to update
   */
  void updateInternshipTopic(InternshipDTO internship, int id);
}
