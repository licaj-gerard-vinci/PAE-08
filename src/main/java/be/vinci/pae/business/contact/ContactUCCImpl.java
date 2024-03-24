package be.vinci.pae.business.contact;

import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.contact.ContactDAO;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;

public class ContactUCCImpl implements ContactUCC {

  @Inject
  ContactDAO contactDAO;

  @Inject
  private DALServices dalServices;

  /**
   * Retrieves a list of contacts for a specific user.
   *
   * @param idUser the ID of the user
   * @return a list of contacts
   */
  @Override
  public List<ContactDTO> getContacts(int idUser) {
    try {
      dalServices.startTransaction();
      List<ContactDTO> contacts = contactDAO.getContacts(idUser);
      dalServices.commitTransaction();
      return contacts;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.close();
    }
  }

  /**
   * Retrieves all contact information for a specific user.
   *
   * @param idUser the ID of the user
   * @return a list of contacts with all information
   */
  public List<ContactDTO> getContactsAllInfo(int idUser) {
    try {
      dalServices.startTransaction();
      List<ContactDTO> contacts = contactDAO.getContactsAllInfo(idUser);
      dalServices.commitTransaction();
      return contacts;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.close();
    }
  }

  /**
   * Inserts a new contact.
   *
   * @param contact the contact to insert
   */
  public void insertContact(ContactDTO contact) {
    validateContact(contact);

    try {
      dalServices.startTransaction();

      if (checkContact(contact.getIdUtilisateur(), contact.getIdEntreprise())) {
        throw new RuntimeException("Contact already exists");
      }

      contactDAO.insertContact(contact);
      dalServices.commitTransaction();
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.close();
    }

  }

  /**
   * Updates a contact to the 'taken' state.
   *
   * @param contact the contact to update
   */
  public void updateContactTaken(ContactDTO contact) {
    validateContact(contact);

    if (!checkContactAndState(contact.getIdUtilisateur(), contact.getIdEntreprise(), "initiated")) {
      throw new RuntimeException("Contact does not exist or is not in the 'initiated' state");
    }
    contactDAO.updateContact(contact);
  }

  /**
   * Updates a contact to the 'accepted' state.
   *
   * @param contact the contact to update
   */
  public void updateContactAccepted(ContactDTO contact) {
    validateContact(contact);

    if (!checkContactAndState(contact.getIdUtilisateur(), contact.getIdEntreprise(), "taken")) {
      throw new RuntimeException("Contact does not exist or is not in the 'taken' state");
    }
    contactDAO.updateContact(contact);
  }

  /**
   * Updates a contact to the 'refused' state.
   *
   * @param contact the contact to update
   */
  public void updateContactRefused(ContactDTO contact) {
    validateContact(contact);

    if (!checkContactAndState(contact.getIdUtilisateur(), contact.getIdEntreprise(), "taken")) {
      throw new RuntimeException("Contact does not exist or is not in the 'taken' state");
    }
    if (contact.getRaisonRefus() == null) {
      throw new RuntimeException("Reason for refusal cannot be null");
    }
    contactDAO.updateContact(contact);
  }

  /**
   * Updates a contact to the 'unsupervised' state.
   *
   * @param contact the contact to update
   */
  public void updateContactUnsupervised(ContactDTO contact) {
    validateContact(contact);

    if (!checkContactAndState(contact.getIdUtilisateur(), contact.getIdEntreprise(), "initiated") &&
            !checkContactAndState(contact.getIdUtilisateur(), contact.getIdEntreprise(), "taken")) {
      throw new RuntimeException("Contact does not exist or is not in the 'initiated' or 'taken' state");
    }
    contactDAO.updateContact(contact);
  }

  /**
   * Checks if a contact exists between a user and a company.
   *
   * @param idUser the ID of the user
   * @param idEntreprise the ID of the company
   * @return true if the contact exists, false otherwise
   */
  public boolean checkContact(int idUser, int idEntreprise) {
    return contactDAO.checkContactExists(idUser, idEntreprise);
  }


  /**
   * Checks if a contact exists between a user and a company and if the contact is in a specific state.
   *
   * @param idUser the ID of the user
   * @param idEntreprise the ID of the company
   * @param etat the state of the contact
   * @return true if the contact exists and is in the specified state, false otherwise
   */
  public boolean checkContactAndState(int idUser, int idEntreprise, String etat) {
    return contactDAO.checkContactAndState(idUser,idEntreprise,etat);
  }

  private void validateContact(ContactDTO contact) {
    if (contact == null) {
      throw new IllegalArgumentException("Contact cannot be null");
    }
  }

  public void updateContact(ContactDTO contact) {
    try {
      dalServices.startTransaction();
      contactDAO.updateContact(contact);
      dalServices.commitTransaction();
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.close();
    }
  }

}
