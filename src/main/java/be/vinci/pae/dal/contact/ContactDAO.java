package be.vinci.pae.dal.contact;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.contact.ContactDetailledDTO;
import java.util.List;

/**
 * Represents the ContactDAO interface.
 */

public interface ContactDAO {


  /**
   * Gets the contacts.
   *
   * @param id the user ID.
   */
  List<ContactDetailledDTO> getContacts(int id);

  /**
   * Fetches all contact information for a specific user.
   *
   * @param idUser the user ID.
   */
  List<ContactDTO> getContactsAllInfo(int idUser);

  /**
   * Inserts a new contact into the database.
   *
   * @param contact The contact information to insert.
   */
  void insertContact(ContactDTO contact);

  /**
   * Updates a contact in the database.
   *
   * @param contact The contact information to update.
   */
  void updateContact(ContactDTO contact);
}
