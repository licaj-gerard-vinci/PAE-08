package be.vinci.pae.business.contact;

import be.vinci.pae.business.company.CompanyUCC;
import be.vinci.pae.business.user.UserUCC;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.contact.ContactDAO;
import be.vinci.pae.presentation.exceptions.BusinessException;
import be.vinci.pae.presentation.exceptions.FatalException;
import be.vinci.pae.presentation.exceptions.NotFoundException;
import jakarta.inject.Inject;
import java.util.List;

/**
 * The type Contact UCC.
 */
public class ContactUCCImpl implements ContactUCC {

  @Inject
  ContactDAO contactDAO;

  @Inject
  private DALServices dalServices;

  @Inject
  private UserUCC myUser;

  @Inject
  private CompanyUCC myCompany;

  /**
   * Retrieves a list of contacts for a specific user.
   *
   * @return a list of contacts
   */
  @Override
  public List<ContactDTO> getContacts() {
    try {
      dalServices.startTransaction();
      List<ContactDTO> contacts = contactDAO.getContacts();
      dalServices.commitTransaction();
      return contacts;
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.close();
    }
  }

  /**
   * Retrieves all contact information for a specific user.
   *
   * @param userId the ID of the user
   * @return a list of contacts with all information
   */
  @Override
  public List<ContactDTO> getContactsAllInfo(int userId) {
    if (myUser.getOne(userId) == null) {
      throw new NotFoundException("User not found");
    }
    try {
      dalServices.startTransaction();
      List<ContactDTO> contacts = contactDAO.getContactsAllInfo(userId);
      dalServices.commitTransaction();
      return contacts;
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.close();
    }
  }

  /**
   * Retrieves a contact by its ID.
   *
   * @param contactId the ID of the contact
   * @return the contact
   */
  @Override
  public ContactDTO getContactById(int contactId) {
    try {
      dalServices.startTransaction();
      ContactDTO contact = contactDAO.getContactById(contactId);
      if (contact == null) {
        throw new NotFoundException("Contact not found");
      }
      dalServices.commitTransaction();
      return contact;
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
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
    if (myUser.getOne(contact.getUser().getId()) == null) {
      return;
    }

    if (myCompany.getCompany(contact.getCompany().getId()) == null) {
      return;
    }

    try {
      dalServices.startTransaction();
      if (checkContact(contact.getUser().getId(), contact.getCompany().getId()) != null) {
        throw new BusinessException("Contact already exists");
      }
      contactDAO.insertContact(contact);
      dalServices.commitTransaction();
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.close();
    }
  }

  /**
   * Updates a contact.
   *
   * @param contactToUpdate the contact to update
   */
  public void updateContact(ContactDTO contactToUpdate) {
    ContactDTO contactToVerif = checkContact(contactToUpdate.getUser()
        .getId(), contactToUpdate.getCompany().getId());

    if (contactToVerif == null) {
      throw new NotFoundException("Contact not found");
    }

    contactToUpdate.setId(contactToVerif.getId());

    if (contactToVerif.getMeetingPlace() != null) {
      contactToUpdate.setMeetingPlace(contactToVerif.getMeetingPlace());
    }

    if (contactToVerif.getRefusalReason() != null) {
      contactToUpdate.setRefusalReason(contactToVerif.getRefusalReason());
    }

    try {
      dalServices.startTransaction();
      if (contactToUpdate.getContactState().equals("pris")) {
        checkContactTaken(contactToUpdate);
      } else if (contactToUpdate.getContactState().equals("accepté")) {
        checkContactAccepted(contactToUpdate);
      } else if (contactToUpdate.getContactState().equals("refusé")) {
        checkContactRefused(contactToUpdate);
      } else if (contactToUpdate.getContactState().equals("non suivi")) {
        checkContactUnsupervised(contactToUpdate);
      } else {
        throw new BusinessException("Invalid state");
      }
      contactToUpdate.setYear(contactToVerif.getYear());
      contactDAO.updateContact(contactToUpdate);
      dalServices.commitTransaction();
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.close();
    }
  }

  /**
   * Updates a contact to the 'taken' state.
   *
   * @param contact the contact to update
   * @return true if the contact can be updated, false otherwise
   */
  public boolean checkContactTaken(ContactDTO contact) {
    return checkContactAndState(contact.getUser().getId(),
        contact.getCompany().getId(), "initié");
  }

  /**
   * Updates a contact to the 'accepted' state.
   *
   * @param contact the contact to update
   * @return true if the contact can be updated, false otherwise
   */
  public boolean checkContactAccepted(ContactDTO contact) {
    return checkContactAndState(contact.getUser().getId(),
        contact.getCompany().getId(), "pris");
  }

  /**
   * Updates a contact to the 'refused' state.
   *
   * @param contact the contact to update
   * @return true if the contact can be updated, false otherwise
   */
  public boolean checkContactRefused(ContactDTO contact) {
    return checkContactAndState(contact.getUser().getId(),
        contact.getCompany().getId(), "pris")
        && contact.getRefusalReason() != null;
  }

  /**
   * Updates a contact to the 'unsupervised' state.
   *
   * @param contact the contact to update
   * @return true if the contact can be updated, false otherwise
   */
  public boolean checkContactUnsupervised(ContactDTO contact) {
    return checkContactAndState(contact.getUser().getId(),
        contact.getCompany().getId(), "initié")
        || checkContactAndState(contact.getUser().getId(),
        contact.getCompany().getId(), "pris");
  }

  /**
   * Checks if a contact exists between a user and a company.
   *
   * @param userId       the ID of the user
   * @param companyId the ID of the company
   * @return true if the contact exists, false otherwise
   */
  public ContactDTO checkContact(int userId, int companyId) {
    return contactDAO.checkContactExists(userId, companyId);
  }


  /**
   * Checks if a contact exists between a user and a company and if the contact is in a specific
   * state.
   *
   * @param userId       the ID of the user
   * @param companyId the ID of the company
   * @param state         the state of the contact
   * @return true if the contact exists and is in the specified state, false otherwise
   */
  public boolean checkContactAndState(int userId, int companyId, String state) {
    return contactDAO.checkContactAndState(userId, companyId, state);
  }


}
