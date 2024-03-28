package be.vinci.pae.business.contact;

import be.vinci.pae.business.entreprise.EntrepriseUCC;
import be.vinci.pae.business.user.UserUCC;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.contact.ContactDAO;
import be.vinci.pae.presentation.exceptions.BusinessException;
import be.vinci.pae.presentation.exceptions.FatalException;
import be.vinci.pae.presentation.exceptions.NotFoundException;
import jakarta.inject.Inject;
import java.util.List;

/**
 * The type Contact UCC.
 */
public class ContactUCCImpl implements ContactUCC {

  @Inject
  ContactDAO contactDAO;

  @Inject
  private DALServices dalServices;

  @Inject
  private UserUCC myUser;

  @Inject
  private EntrepriseUCC myCompany;

  /**
   * Retrieves a list of contacts for a specific user.
   *
   * @return a list of contacts
   */
  @Override
  public List<ContactDTO> getContacts() {
    try {
      dalServices.startTransaction();
      List<ContactDTO> contacts = contactDAO.getContacts();
      dalServices.commitTransaction();
      return contacts;
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  /**
   * Retrieves all contact information for a specific user.
   *
   * @param idUser the ID of the user
   * @return a list of contacts with all information
   */
  @Override
  public List<ContactDTO> getContactsAllInfo(int idUser) {
    if (myUser.getOne(idUser) == null) {
      throw new NotFoundException("User not found");
    }
    try {
      dalServices.startTransaction();
      List<ContactDTO> contacts = contactDAO.getContactsAllInfo(idUser);
      dalServices.commitTransaction();
      return contacts;
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  /**
   * Retrieves a contact by its ID.
   *
   * @param idContact the ID of the contact
   * @return the contact
   */
  @Override
  public ContactDTO getContactById(int idContact) {
    try {
      dalServices.startTransaction();
      ContactDTO contact = contactDAO.getContactById(idContact);
      if (contact == null) {
        throw new NotFoundException("Contact not found");
      }
      dalServices.commitTransaction();
      return contact;
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  /**
   * Inserts a new contact.
   *
   * @param contact the contact to insert
   */
  public void insertContact(ContactDTO contact) {
    if (myUser.getOne(contact.getUtilisateur().getId()) == null) {
      return;
    }

    if (myCompany.getEntreprise(contact.getEntreprise().getId()) == null) {
      return;
    }

    try {
      dalServices.startTransaction();
      if (checkContact(contact.getUtilisateur().getId(), contact.getEntreprise().getId()) != null) {
        throw new BusinessException("Contact already exists");
      }
      contactDAO.insertContact(contact);
      dalServices.commitTransaction();
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  /**
   * Updates a contact.
   *
   * @param contactToUpdate the contact to update
   */
  public void updateContact(ContactDTO contactToUpdate) {
    ContactDTO contactToVerif = checkContact(contactToUpdate.getUtilisateur()
        .getId(), contactToUpdate.getEntreprise().getId());

    if (contactToVerif == null) {
      throw new NotFoundException("Contact not found");
    }

    contactToUpdate.setId(contactToVerif.getId());

    if (contactToVerif.getLieuxRencontre() != null) {
      contactToUpdate.setLieuxRencontre(contactToVerif.getLieuxRencontre());
    }

    if (contactToVerif.getRaisonRefus() != null) {
      contactToUpdate.setRaisonRefus(contactToVerif.getRaisonRefus());
    }

    try {
      dalServices.startTransaction();
      if (contactToUpdate.getEtatContact().equals("pris")) {
        checkContactTaken(contactToUpdate);
      } else if (contactToUpdate.getEtatContact().equals("accepté")) {
        checkContactAccepted(contactToUpdate);
      } else if (contactToUpdate.getEtatContact().equals("refusé")) {
        checkContactRefused(contactToUpdate);
      } else if (contactToUpdate.getEtatContact().equals("non suivi")) {
        checkContactUnsupervised(contactToUpdate);
      } else {
        throw new BusinessException("Invalid state");
      }
      contactToUpdate.setAnnee(contactToVerif.getAnnee());
      contactDAO.updateContact(contactToUpdate);
      dalServices.commitTransaction();
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  /**
   * Updates a contact to the 'taken' state.
   *
   * @param contact the contact to update
   * @return true if the contact can be updated, false otherwise
   */
  public boolean checkContactTaken(ContactDTO contact) {
    return checkContactAndState(contact.getUtilisateur().getId(),
        contact.getEntreprise().getId(), "initié");
  }

  /**
   * Updates a contact to the 'accepted' state.
   *
   * @param contact the contact to update
   * @return true if the contact can be updated, false otherwise
   */
  public boolean checkContactAccepted(ContactDTO contact) {
    return checkContactAndState(contact.getUtilisateur().getId(),
        contact.getEntreprise().getId(), "pris");
  }

  /**
   * Updates a contact to the 'refused' state.
   *
   * @param contact the contact to update
   * @return true if the contact can be updated, false otherwise
   */
  public boolean checkContactRefused(ContactDTO contact) {
    return checkContactAndState(contact.getUtilisateur().getId(),
        contact.getEntreprise().getId(), "pris")
        && contact.getRaisonRefus() != null;
  }

  /**
   * Updates a contact to the 'unsupervised' state.
   *
   * @param contact the contact to update
   * @return true if the contact can be updated, false otherwise
   */
  public boolean checkContactUnsupervised(ContactDTO contact) {
    return checkContactAndState(contact.getUtilisateur().getId(),
        contact.getEntreprise().getId(), "initié")
        || checkContactAndState(contact.getUtilisateur().getId(),
        contact.getEntreprise().getId(), "pris");
  }

  /**
   * Checks if a contact exists between a user and a company.
   *
   * @param idUser       the ID of the user
   * @param idEntreprise the ID of the company
   * @return true if the contact exists, false otherwise
   */
  public ContactDTO checkContact(int idUser, int idEntreprise) {
    return contactDAO.checkContactExists(idUser, idEntreprise);
  }


  /**
   * Checks if a contact exists between a user and a company and if the contact is in a specific
   * state.
   *
   * @param idUser       the ID of the user
   * @param idEntreprise the ID of the company
   * @param etat         the state of the contact
   * @return true if the contact exists and is in the specified state, false otherwise
   */
  public boolean checkContactAndState(int idUser, int idEntreprise, String etat) {
    return contactDAO.checkContactAndState(idUser, idEntreprise, etat);
  }


}
