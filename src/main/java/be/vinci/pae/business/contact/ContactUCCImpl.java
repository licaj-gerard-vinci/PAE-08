package be.vinci.pae.business.contact;

import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.contact.ContactDAO;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Represents the ContactUCCImpl class.
 */

public class ContactUCCImpl implements ContactUCC {


  @Inject
  ContactDAO contactDAO;

  @Inject
  private DALServices dalServices;


  /**
   * Gets the contacts.
   *
   * @param idUser the user ID.
   * @return the contacts.
   */
  @Override
  public List<ContactDTO> getContacts(int idUser) {
    try {
      dalServices.startTransaction();
      List<ContactDTO> contacts = contactDAO.getContacts(idUser);
      dalServices.commitTransaction();
      return contacts;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.close();
    }
  }

  /**
   * Fetches all contact information for a specific user.
   *
   * @param idUser The ID of the user whose contacts are to be fetched.
   * @return A list of ContactDTO objects containing all contact information for the user.
   */
  public List<ContactDTO> getContactsAllInfo(int idUser) {
    try {
      dalServices.startTransaction();
      List<ContactDTO> contacts = contactDAO.getContactsAllInfo(idUser);
      dalServices.commitTransaction();
      return contacts;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.close();
    }
  }

  /**
   * Inserts a new contact into the database.
   *
   * @param contact A ContactDTO object containing the information of the contact to be inserted.
   */
  public void insertContact(ContactDTO contact) {
    try {
      dalServices.startTransaction();
      contactDAO.insertContact(contact);
      dalServices.commitTransaction();
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.close();
    }

  }

  /**
   * Updates the information of an existing contact in the database.
   *
   * @param contact A ContactDTO object containing the updated information of the contact.
   */
  public void updateContact(ContactDTO contact) {
    try {
      dalServices.startTransaction();
      contactDAO.updateContact(contact);
      dalServices.commitTransaction();
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.close();
    }
  }

}
