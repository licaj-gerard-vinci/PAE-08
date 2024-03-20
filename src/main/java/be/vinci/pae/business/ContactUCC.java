package be.vinci.pae.business;

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

  List<ContactDetailledDTO> getContacts(int idUser);

  List<ContactDTO> getContactsAllInfo(int idUser);

  void insertContact(ContactDTO contact);

  void updateContact(ContactDTO contact);
}
