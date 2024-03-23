package be.vinci.pae.business.contact;

import be.vinci.pae.dal.contact.ContactDAO;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Represents the ContactUCCImpl class.
 */

public class ContactUCCImpl implements ContactUCC {


  @Inject
  ContactDAO contactDAO;


  /**
   * Gets the contacts.
   *
   * @param idUser the user ID.
   * @return the contacts.
   */
  @Override
  public List<ContactDTO> getContacts(int idUser) {
    return contactDAO.getContacts(idUser);
  }

  /**
   * Fetches all contact information for a specific user.
   *
   * @param idUser The ID of the user whose contacts are to be fetched.
   * @return A list of ContactDTO objects containing all contact information for the user.
   */
  public List<ContactDTO> getContactsAllInfo(int idUser) {
    return contactDAO.getContactsAllInfo(idUser);
  }

  /**
   * Inserts a new contact into the database.
   *
   * @param contact A ContactDTO object containing the information of the contact to be inserted.
   */
  public void insertContact(ContactDTO contact) {
    contactDAO.insertContact(contact);
  }

  /**
   * Updates the information of an existing contact in the database.
   *
   * @param contact A ContactDTO object containing the updated information of the contact.
   */
  public void updateContact(ContactDTO contact) {
    contactDAO.updateContact(contact);
  }

}
