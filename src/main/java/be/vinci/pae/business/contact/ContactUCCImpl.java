package be.vinci.pae.business.contact;

import be.vinci.pae.business.company.CompanyUCC;
import be.vinci.pae.business.user.UserUCC;
import be.vinci.pae.business.year.Year;
import be.vinci.pae.business.year.YearDTO;
import be.vinci.pae.business.year.YearUCC;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.contact.ContactDAO;
import be.vinci.pae.dal.user.UserDAO;
import be.vinci.pae.exceptions.BusinessException;
import be.vinci.pae.exceptions.ConflictException;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.exceptions.NotFoundException;
import jakarta.inject.Inject;
import java.util.List;

/**
 * The type Contact UCC.
 */
public class ContactUCCImpl implements ContactUCC {

  @Inject
  private ContactDAO contactDAO;
  @Inject
  private DALServices dalServices;
  @Inject
  private UserUCC myUserUCC;
  @Inject
  private UserDAO myDaoUser;
  @Inject
  private CompanyUCC myCompanyUCC;
  @Inject
  private YearUCC myYearUCC;
  @Inject
  private Year year;

  /**
   * Retrieves a list of contacts for a specific user.
   *
   * @return a list of contacts
   */
  @Override
  public List<ContactDTO> getContacts() {
    try {
      dalServices.openConnection();
      return contactDAO.getContacts();
    } finally {
      dalServices.close();
    }
  }

  /**
   * Retrieves all contact information for a specific user.
   *
   * @param idUser the ID of the user
   * @return a list of contacts with all information
   */
  @Override
  public List<ContactDTO> getContactsByUserId(int idUser) {
    myUserUCC.getOne(idUser);

    try {
      dalServices.openConnection();
      return contactDAO.getContactsAllInfo(idUser);
    } finally {
      dalServices.close();
    }
  }

  /**
   * Retrieves a contact by its ID.
   *
   * @param idContact the ID of the contact
   * @return the contact
   */
  @Override
  public ContactDTO getContactByContactId(int idContact) {
    try {
      dalServices.openConnection();
      ContactDTO contact = contactDAO.getContactById(idContact);
      if (contact == null) {
        throw new NotFoundException("Contact not found");
      }
      return contact;
    } finally {
      dalServices.close();
    }
  }

  /**
   * Inserts a new contact.
   *
   * @param contact the contact to insert
   */
  public void insertContact(ContactDTO contact) {
    if (contactDAO.getContactById(contact.getId()) != null) {
      throw new ConflictException("Contact already exists");
    }

    myUserUCC.getOne(contact.getStudent().getId());

    myCompanyUCC.getCompanyById(contact.getCompany().getId());

    String currentYear = year.renderCurrentYear();
    YearDTO yearDTO = myYearUCC.getYearByYear(currentYear);
    contact.setYear(yearDTO);
    contact.setIdYear(yearDTO.getId());
    try {
      dalServices.startTransaction();
      contactDAO.insertContact(contact);
      dalServices.commitTransaction();
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  /**
   * Updates a contact.
   *
   * @param contactToUpdate the contact to update
   */
  public void updateContact(ContactDTO contactToUpdate) {
    Contact contactToVerify = (Contact) contactDAO.getContactById(contactToUpdate.getId());

    if (contactToVerify == null) {
      throw new NotFoundException("Contact not found");
    }

    if (!contactToVerify.checkState(contactToVerify.getContactStatus(),
        contactToUpdate.getContactStatus())) {
      throw new BusinessException("Invalid state");
    }

    if (contactToVerify.getMeetingPlace() != null) {
      contactToUpdate.setMeetingPlace(contactToVerify.getMeetingPlace());
    }

    if (contactToVerify.getRefusalReason() != null) {
      contactToUpdate.setRefusalReason(contactToVerify.getRefusalReason());
    }

    contactToUpdate.setId(contactToVerify.getId());
    contactToUpdate.setYear(contactToVerify.getYear());

    try {
      dalServices.startTransaction();
      contactDAO.updateContact(contactToUpdate);
      dalServices.commitTransaction();
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  /**
   * Retrieves a contact by the company ID.
   *
   * @param idCompany the ID of the contact.
   * @return the contact.
   */
  public List<ContactDTO> getContactsByCompanyId(int idCompany) {
    myCompanyUCC.getCompanyById(idCompany);

    try {
      dalServices.openConnection();
      return contactDAO.getContactsByCompanyId(idCompany);
    } finally {
      dalServices.close();
    }
  }

  /**
   * Suspend all initiated and taken contacts.
   *
   * @param idUser the user getting all initiated and taken contacts updated to suspend
   * @param idContact the contact that want to be accepted
   */
  public void suspendContacts(int idUser, int idContact) {
    if (myDaoUser.getOneById(idUser) == null) {
      throw new NotFoundException("user not found");
    }
    try {
      dalServices.startTransaction();
      List<ContactDTO> userContacts = contactDAO.getContactsAllInfo(idUser);
      for (ContactDTO contact : userContacts) {
        if (contact.getContactStatus().equals("pris") && contact.getId() != idContact
                || contact.getContactStatus().equals("initié")) {
          contact.setContactStatus("suspendu");
          System.out.println("before updateContact call");
          updateContact(contact);
          System.out.println("after updateContact call");
        }
      }
      dalServices.commitTransaction();
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }
}
