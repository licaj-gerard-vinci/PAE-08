package be.vinci.pae.dal.contact;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.contact.ContactDetailledDTO;
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
