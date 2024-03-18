package be.vinci.pae.business;

import be.vinci.pae.dal.ContactDAO;
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


  public List<ContactDTO> getContactsAllInfo(int idUser){
    return contactDAO.getContactsAllInfo(idUser);
  }


  public void insertContact(ContactDTO contact){
    contactDAO.insertContact(contact);
  }
}
