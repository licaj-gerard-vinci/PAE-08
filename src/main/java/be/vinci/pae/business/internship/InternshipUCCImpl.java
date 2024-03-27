package be.vinci.pae.business.internship;

import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.internship.InternshipDAO;
import be.vinci.pae.presentation.exceptions.FatalException;
import be.vinci.pae.presentation.exceptions.NotFoundException;
import jakarta.inject.Inject;
import java.util.List;

/**
 * The Class StageUCCImpl.
 */
public class InternshipUCCImpl implements InternshipUCC {

  @Inject
  private InternshipDAO internshipDAO;

  @Inject
  private DALServices dalServices;

  /**
   * Gets the internship user.
   *
   * @param userId the id user
   * @return the internship user
   */

  public InternshipDTO getInternshipUser(int userId) {
    try {
      dalServices.startTransaction();
      InternshipDTO internship = internshipDAO.getInternshipId(userId);
      if (internship == null) {
        throw new NotFoundException("No internship found for this user");
      }
      dalServices.commitTransaction();
      return internship;
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.close();
    }
  }


  /**
   * Gets all internships.
   *
   * @return all internships
   */
  @Override
  public List<InternshipDTO> getInternships() {
    try {
      dalServices.startTransaction();
      List<InternshipDTO> internships = internshipDAO.getInternships();
      if (internships == null) {
        throw new NotFoundException("No internships found");
      }
      dalServices.commitTransaction();
      return internships;
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.close();
    }
  }
}
