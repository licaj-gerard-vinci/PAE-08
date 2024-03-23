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
   * @param idUser the user ID.
   * @return the contacts.
   */

  List<ContactDTO> getContacts(int idUser);

  /**
   * Fetches all contact information for a specific user.
   *
   * @param idUser The ID of the user whose contacts are to be fetched.
   * @return A list of ContactDTO objects containing all contact information for the user.
   */
  List<ContactDTO> getContactsAllInfo(int idUser);

  boolean checkContact(int idUser, int idEntreprise) throws SQLException;

  /**
   * Inserts a new contact into the database.
   *
   * @param contact A ContactDTO object containing the information of the contact to be inserted.
   */
  void insertContact(ContactDTO contact);

  /**
   * Updates the information of an existing contact in the database.
   *
   * @param contact A ContactDTO object containing the updated information of the contact.
   */
  void updateContact(ContactDTO contact);
}
