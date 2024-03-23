package be.vinci.pae.business.contact;

import be.vinci.pae.dal.contact.ContactDAO;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;

public class ContactUCCImpl implements ContactUCC {

  @Inject
  ContactDAO contactDAO;

  /**
   * Retrieves a list of contacts for a specific user.
   *
   * @param idUser the ID of the user
   * @return a list of contacts
   */
  @Override
  public List<ContactDTO> getContacts(int idUser) {
    return contactDAO.getContacts(idUser);
  }

  /**
   * Retrieves all contact information for a specific user.
   *
   * @param idUser the ID of the user
   * @return a list of contacts with all information
   */
  public List<ContactDTO> getContactsAllInfo(int idUser) {
    if (!checkUser(idUser)) {
      throw new RuntimeException("User or company does not exist");
    }
    return contactDAO.getContactsAllInfo(idUser);
  }

  /**
   * Inserts a new contact.
   *
   * @param contact the contact to insert
   */
  public void insertContact(ContactDTO contact) {
    validateContact(contact);

    if (checkContact(contact.getIdUtilisateur(), contact.getIdEntreprise())) {
      throw new RuntimeException("Contact already exists");
    }
    contactDAO.insertContact(contact);
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
   * Checks if a company exists.
   *
   * @param idEntreprise the ID of the company
   * @return true if the company exists, false otherwise
   */
  public boolean checkCompany(int idEntreprise) {
    return contactDAO.checkCompanyExists(idEntreprise);
  }

  /**
   * Checks if a user exists.
   *
   * @param idUser the ID of the user
   * @return true if the user exists, false otherwise
   */
  public boolean checkUser(int idUser) {
    return contactDAO.checkUserExists(idUser);
  }

  /**
   * Checks if a contact exists between a user and a company and if the contact is in a specific state.
   *
   * @param idUser the ID of the user
   * @param idEntreprise the ID of the company
   * @param etat the state of the contact
   * @return true if the contact exists and is in the specified state, false otherwise
   */
  public boolean checkContactAndState(int idUser, int idEntreprise, String etat){
    return contactDAO.checkContactAndState(idUser,idEntreprise,etat);
  }

  private void validateContact(ContactDTO contact) {
    if (contact == null) {
      throw new IllegalArgumentException("Contact cannot be null");
    }
    if (!checkUser(contact.getIdUtilisateur()) || !checkCompany(contact.getIdEntreprise())) {
      throw new RuntimeException("User or company does not exist");
    }
  }

}
