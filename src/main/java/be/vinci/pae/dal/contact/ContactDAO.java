package be.vinci.pae.dal.contact;

import be.vinci.pae.business.contact.ContactDTO;
import java.util.List;

/**
 * Represents the ContactDAO interface.
 */

public interface ContactDAO {


  /**
   * Gets the contacts.
   *
   * @return the contacts.
   */
  List<ContactDTO> getContacts();

  /**
   * Fetches all contact information for a specific user.
   *
   * @param idUser the user ID.
   * @return A list of contact information.
   */
  List<ContactDTO> getContactsAllInfo(int idUser);

  /**
   * Get a contact by its ID.
   */
  ContactDTO getContactById(int idContact);

  /**
   * Check if a contact can be updated to the 'taken' state.
   *
   * @param idUser The contact to update.
   * @param idEntreprise The contact to update.
   * @return true if the contact can be updated, false otherwise.
   */

  ContactDTO checkContactExists(int idUser, int idEntreprise);


  boolean checkContactAndState(int idUser, int idEntreprise, String expectedState);

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
