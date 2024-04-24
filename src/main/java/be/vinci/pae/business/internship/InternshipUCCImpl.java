package be.vinci.pae.business.internship;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserUCC;
import be.vinci.pae.business.year.Year;
import be.vinci.pae.business.year.YearUCC;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.internship.InternshipDAO;
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
  private ContactUCC myContactUCC;
  @Inject
  private UserUCC myUserUCC;
  @Inject
  private YearUCC myYearUCC;
  @Inject
  private Factory factory;

  /**
   * Gets the stage user.
   *
   * @param idUser the id user
   * @return the stage user
   */

  public InternshipDTO getInternshipByUserId(int idUser) {
    try {
      dalServices.openConnection();
      return internshipDAO.getInternshipById(idUser);
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
  public List<InternshipDTO> getInternship() {
    try {
      dalServices.openConnection();
      return internshipDAO.getInternship();
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
  public void insertInternship(InternshipDTO internship) {
    ContactDTO myContactDTO = myContactUCC.getContactByContactId(internship.getContact().getId());
    if (myContactDTO.getStudent().getId() != internship.getStudent().getId()
        || myContactDTO.getCompany().getId() != internship.getCompany().getId()) {
      throw new NotFoundException("contact doesn't exist or doesn't match with the internship");
    } // verify either if contact  exists and if the userId and companyId.
    // are the same for the contact and internship, if one of them are different, "return;".
    if (internshipDAO.getInternshipById(internship.getStudent().getId()) != null) {
      throw new ConflictException("internship for the student already exists");
    }
    myContactDTO.setContactStatus("accepté");
    // since it comes from "insertInternship", the state I want wasn't updated previously.
    UserDTO myUserDTO = myUserUCC.getOne(internship.getStudent().getId());
    myUserDTO.setHasInternship(true);
    myUserDTO.setPassword(""); // to prevent from changing in user update.
    Year year = (Year) factory.getYearDTO();
    internship.setYear(myYearUCC.getYearByYear(year.renderCurrentYear()));
    try {
      dalServices.startTransaction();
      internshipDAO.insertInternship(internship);
      // verification for (if company/user exists) were already done here, in updateContact.
      myContactUCC.updateContact(myContactDTO); // contact accepted
      myContactUCC.suspendContacts(myUserDTO.getId(), myContactDTO.getId());
      myUserUCC.update(myUserDTO.getId(), myUserDTO);
      dalServices.commitTransaction();
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  /**
   * Updates the topic of an internship.
   *
   * @param internship the InternshipDTO object representing the internship to be updated
   * @param id         the id of the internship to update
   */

  public void updateInternshipTopic(InternshipDTO internship, int id) {
    internship.setId(id);
    try {
      dalServices.startTransaction();
      internshipDAO.updateInternshipTopic(internship);
      dalServices.commitTransaction();
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }


}
