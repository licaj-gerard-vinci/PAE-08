package be.vinci.pae.business.contact;

import be.vinci.pae.business.contact.ContactDetailledDTO;

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

}
