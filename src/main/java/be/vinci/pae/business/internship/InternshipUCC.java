package be.vinci.pae.business.internship;

import java.util.List;

/**
 * The Interface InternshipUCC.
 */
public interface InternshipUCC {

  /**
   * Gets the internship user.
   *
   * @param userId the id user
   * @return the internship user
   */

  InternshipDTO getInternshipUser(int userId);

  /**
   * Gets all internships.
   *
   * @return all internships
   */
  List<InternshipDTO> getInternships();
}
