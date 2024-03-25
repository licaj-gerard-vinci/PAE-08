package be.vinci.pae.business.contact;

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
   *
   * @param idContact The ID of the contact to fetch.
   * @return A ContactDTO object containing the contact information.
   */
  ContactDTO getContactById(int idContact);

  /**
   * Inserts a new contact into the database.
   *
   * @param contact A ContactDTO object containing the information of the contact to be inserted.
   */
  void insertContact(ContactDTO contact);

  /**
   * Updates the information of a contact in the database.
   *
   * @param contact A ContactDTO object containing the updated information of the contact.
   */
  void updateContact(ContactDTO contact);

}
