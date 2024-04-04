package be.vinci.pae.business.internship;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.stage.InternshipDAO;
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
  private InternshipDAO internshipDAO;

  @Inject
  private DALServices dalServices;

  @Inject
  private ContactUCC myContact;

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
    ContactDTO myContactDTO = internship.getContact();
    if (myContact.getContactById(myContactDTO.getId()) == null
            || myContactDTO.getUtilisateur().getId() != internship.getEtudiant().getId()
            || myContactDTO.getEntreprise().getId() != internship.getEntreprise().getId()) {
      return;
    } // verify either if contact exists and if the userId and companyId.
    // are the same for the contact and internship, if one of them are different, "return;".
    myContactDTO.setEtatContact("accepté");
    // since it comes from "insertInternship", the state I want wasn't updated previously.
    try {
      dalServices.startTransaction();
      if(internshipDAO.getStageById(internship.getEtudiant().getId()) != null) {
        throw new ConflictException("internship for the student already exists");
      }
      internshipDAO.insertInternship(internship);
      // verification for (if company/user exists) were already done here, in updateContact.
      myContact.updateContact(myContactDTO);
      dalServices.commitTransaction();
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }
}
