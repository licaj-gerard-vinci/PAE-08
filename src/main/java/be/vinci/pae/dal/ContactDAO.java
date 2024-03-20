package be.vinci.pae.dal;

import be.vinci.pae.business.ContactDTO;
import be.vinci.pae.business.ContactDetailledDTO;
import java.util.List;

/**
 * Represents the ContactDAO interface.
 */

public interface ContactDAO {


  /**
   * Gets the contacts.
   *
   * @param id the user ID.
   * @return the contacts.
   */
  List<ContactDetailledDTO> getContacts(int id);

  List<ContactDTO> getContactsAllInfo(int idUser);

  void insertContact(ContactDTO contact);

  void updateContact(ContactDTO contact);
}
