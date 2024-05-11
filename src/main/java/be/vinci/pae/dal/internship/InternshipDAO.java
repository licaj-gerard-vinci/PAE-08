package be.vinci.pae.dal.internship;

import be.vinci.pae.business.internship.InternshipDTO;
import java.util.List;

/**
 * The Interface InternshipDAO.
 */
public interface InternshipDAO {


  /**
   * Gets all Internships.
   *
   * @return all Internships
   */

  List<InternshipDTO> getInternship();

  /**
   * Gets the internship by id.
   *
   * @param id the id
   * @return the internship by id
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
