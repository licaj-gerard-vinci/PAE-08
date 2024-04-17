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

  /**
  * Gets the all contacts with the idCompagny.
  *
  * @param idCompany The ID of the contact to be deleted.
  * @return the contact
  */
  List<ContactDTO> getContactsByCompanyId(int idCompany);

  /**
   * Suspend all initiated and taken contacts.
   *
   * @param idUser the user getting all initiated and taken contacts updated to suspend
   * @param idContact the contact that want to be accepted
   */
  void suspendContacts(int idUser, int idContact);

  /**
   * Blacklist a contact.
   *
   * @param idCompany The contact to be blacklisted.
   */
  void blackListContact(int idCompany);
}
