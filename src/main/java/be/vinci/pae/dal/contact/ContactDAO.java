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
   * @param userId the user ID.
   * @return A list of contact information.
   */
  List<ContactDTO> getContactsAllInfo(int userId);

  /**
   * Get a contact by its ID.
   *
   * @param contactId The ID of the contact to fetch.
   * @return A ContactDTO object containing the contact information.
   */
  ContactDTO getContactById(int contactId);

  /**
   * Check if a contact exists.
   *
   * @param userId The user ID.
   * @param companyId The company ID.
   * @return A ContactDTO object containing the contact information.
   */
  ContactDTO checkContactExists(int userId, int companyId);

  /**
   * Check if a contact can be updated to the 'taken' state.
   *
   * @param idUser The user ID.
   * @param idEntreprise The company ID.
   * @param expectedState The expected state of the contact.
   * @return true if the contact can be updated, false otherwise.
   */
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
