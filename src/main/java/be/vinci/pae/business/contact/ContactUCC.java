package be.vinci.pae.business.contact;

import java.sql.SQLException;
import java.util.List;

/**
 * Represents the ContactUCC interface.
 */

public interface ContactUCC {


  /**
   * Gets the contacts.
   *
   * @return the contacts.
   */

  List<ContactDTO> getContacts();

  /**
   * Fetches all contact information for a specific user.
   *
   * @param idUser The ID of the user whose contacts are to be fetched.
   * @return A list of ContactDTO objects containing all contact information for the user.
   */
  List<ContactDTO> getContactsAllInfo(int idUser);

  /**
   * Get a contact by its ID.
   */
  ContactDTO getContactById(int idContact);

  /**
   * Inserts a new contact into the database.
   *
   * @param contact A ContactDTO object containing the information of the contact to be inserted.
   */
  void insertContact(ContactDTO contact);

  void updateContact(ContactDTO contact);

}
