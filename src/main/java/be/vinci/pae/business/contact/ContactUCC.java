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

  /**
   * Inserts a new contact into the database.
   *
   * @param contact A ContactDTO object containing the information of the contact to be inserted.
   */
  void insertContact(ContactDTO contact) throws SQLException;

  /**
   * Updates a contact to the 'taken' state.
   *
   * @param contact the contact to update
   */
  void updateContactTaken(ContactDTO contact);

  /**
   * Updates a contact to the 'accepted' state.
   *
   * @param contact the contact to update
   */
  void updateContactAccepted(ContactDTO contact);

  /**
   * Updates a contact to the 'refused' state.
   *
   * @param contact the contact to update
   */
  void updateContactRefused(ContactDTO contact);

  /**
   * Updates a contact to the 'unsupervised' state.
   *
   * @param contact the contact to update
   */
  void updateContactUnsupervised(ContactDTO contact);

  /**
   * Checks if a contact exists between a user and a company.
   *
   * @param idUser the ID of the user
   * @param idEntreprise the ID of the company
   * @return true if the contact exists, false otherwise
   */
  boolean checkContact(int idUser, int idEntreprise);

  /**
   * Checks if a contact exists between a user and a company and if the contact is in a specific state.
   *
   * @param idUser the ID of the user
   * @param idEntreprise the ID of the company
   * @param etat the state of the contact
   * @return true if the contact exists and is in the specified state, false otherwise
   */
  boolean checkContactAndState(int idUser, int idEntreprise, String etat);

  void updateContact(ContactDTO contact);
}
