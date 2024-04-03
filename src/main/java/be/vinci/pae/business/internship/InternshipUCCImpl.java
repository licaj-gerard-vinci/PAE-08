package be.vinci.pae.business.internship;

import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.business.entreprise.EntrepriseUCC;
import be.vinci.pae.business.user.UserUCC;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.stage.StageDAO;
import be.vinci.pae.exceptions.ConflictException;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.exceptions.NotFoundException;
import jakarta.inject.Inject;
import java.util.List;

/**
 * The Class StageUCCImpl.
 */
public class InternshipUCCImpl implements InternshipUCC {

  @Inject
  private StageDAO internshipDAO;

  @Inject
  private DALServices dalServices;

  @Inject
  private UserUCC myUser;

  @Inject
  private ContactUCC myContact;

  @Inject
  private EntrepriseUCC myCompany;

  /**
   * Gets the stage user.
   *
   * @param idUser the id user
   * @return the stage user
   */

  public InternshipDTO getStageUser(int idUser) {
    try {
      dalServices.startTransaction();
      InternshipDTO stage = internshipDAO.getStageById(idUser);
      if (stage == null) {
        throw new NotFoundException("No internship found for this user");
      }
      dalServices.commitTransaction();
      return stage;
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }


  /**
   * Gets all stages.
   *
   * @return all stages
   */
  @Override
  public List<InternshipDTO> getStages() {
    try {
      dalServices.startTransaction();
      List<InternshipDTO> stages = internshipDAO.getStages();
      if (stages == null) {
        throw new NotFoundException("No stages found");
      }
      dalServices.commitTransaction();
      return stages;
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  /**
   * Inserts a new internship into the database.
   *
   * @param internship the contact to insert
   */
  @Override
  public void insertInternship(InternshipDTO internship) {
    if (myContact.getContactById(internship.getContact().getId()) == null) {
      return;
    }

    if (myUser.getOne(internship.getEtudiant().getId()) == null) {
      return;
    }

    if (myCompany.getEntreprise(internship.getEntreprise().getId()) == null) {
      return;
    }

    try {
      dalServices.startTransaction();
      if(getStageUser(internship.getEtudiant().getId()) != null) {
        throw new ConflictException("internship for the student already exists");
      }

      internshipDAO.insertInternship(internship);
      dalServices.commitTransaction();
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }
}
