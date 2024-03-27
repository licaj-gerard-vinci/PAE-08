package be.vinci.pae.dal.internship;

import be.vinci.pae.business.internship.InternshipDTO;
import java.util.List;

/**
 * The Interface InternshipDAO.
 */
public interface InternshipDAO {


  /**
   * Gets all internships.
   *
   * @return all internships
   */

  List<InternshipDTO> getInternships();

  /**
   * Gets the internship by id.
   *
   * @param id the id
   * @return the internship by id
   */
  InternshipDTO getInternshipId(int id);



}
