package be.vinci.pae.business.stage;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserUCC;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.stage.StageDAO;
import be.vinci.pae.exceptions.ConflictException;
import be.vinci.pae.exceptions.FatalException;
import jakarta.inject.Inject;
import java.util.List;

/**
 * The Class StageUCCImpl.
 */
public class StageUCCImpl implements StageUCC {

  @Inject
  private StageDAO internshipDAO;

  @Inject
  private DALServices dalServices;

  @Inject
  private ContactUCC myContact;

  @Inject
  private UserUCC myUser;

  /**
   * Gets the stage user.
   *
   * @param idUser the id user
   * @return the stage user
   */

  public StageDTO getInternshipByUserId(int idUser) {
    try {
      dalServices.openConnection();
      return internshipDAO.getStageById(idUser);
    } finally {
      dalServices.close();
    }
  }


  /**
   * Gets all stages.
   *
   * @return all stages
   */
  @Override
  public List<StageDTO> getStages() {
    try {
      dalServices.openConnection();
      return internshipDAO.getStages();
    } finally {
      dalServices.close();
    }
  }

  /**
   * Inserts a new internship into the database.
   *
   * @param internship the contact to insert
   */
  @Override
  public void insertInternship(StageDTO internship) {
    ContactDTO myContactDTO = internship.getContact();
    if (myContact.getContactByContactId(myContactDTO.getId()) == null
        || myContactDTO.getUtilisateur().getId() != internship.getEtudiant().getId()
        || myContactDTO.getEntreprise().getId() != internship.getEntreprise().getId()) {
      return;
    } // verify either if contact  exists and if the userId and companyId.
    // are the same for the contact and internship, if one of them are different, "return;".
    if (internshipDAO.getStageById(internship.getEtudiant().getId()) != null) {
      throw new ConflictException("internship for the student already exists");
    }
    myContactDTO.setEtatContact("accepté");
    // since it comes from "insertInternship", the state I want wasn't updated previously.
    UserDTO myUserDTO = myUser.getOne(internship.getEtudiant().getId());
    myUserDTO.setHasInternship(true);
    myUserDTO.setPassword(""); // to prevent from changing it afterwards in user update.
    try {
      dalServices.startTransaction();
      internshipDAO.insertInternship(internship);
      // verification for (if company/user exists) were already done here, in updateContact.
      myContact.updateContact(myContactDTO); // contact accepted
      myContact.suspendContacts(myUserDTO.getId(), myContactDTO.getId());
      myUser.update(myUserDTO.getId(), myUserDTO);
      dalServices.commitTransaction();
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }
}
