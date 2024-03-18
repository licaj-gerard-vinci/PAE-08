package be.vinci.pae.business.contact;

import be.vinci.pae.dal.contact.ContactDAO;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Represents the ContactUCCImpl class.
 */

public class ContactUCCImpl implements ContactUCC {


  @Inject
  ContactDAO contactDAO;


  /**
   * Gets the contacts.
   *
   * @param idUser the user ID.
   * @return the contacts.
   */
  @Override
  public List<ContactDetailledDTO> getContacts(int idUser) {
    return contactDAO.getContacts(idUser);
  }
}
