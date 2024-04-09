package be.vinci.pae.business.contact;

import be.vinci.pae.business.entreprise.EntrepriseUCC;
import be.vinci.pae.business.user.UserUCC;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.contact.ContactDAO;
import be.vinci.pae.exceptions.BusinessException;
import be.vinci.pae.exceptions.ConflictException;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.exceptions.NotFoundException;
import jakarta.inject.Inject;
import java.util.List;

/**
 * The type Contact UCC.
 */
public class ContactUCCImpl implements ContactUCC {

  @Inject
  private ContactDAO contactDAO;

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
      if (contactDAO.getContactById(contact.getId()) != null) {
        throw new ConflictException("Contact already exists");
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
    Contact contactToVerif = (Contact) contactDAO.getContactById(contactToUpdate.getId());

    if (contactToVerif == null) {
      throw new NotFoundException("Contact not found");
    }

    if (!contactToVerif.checkState(contactToVerif.getEtatContact(),
        contactToUpdate.getEtatContact())) {
      throw new BusinessException("Invalid state");
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
      contactToUpdate.setAnnee(contactToVerif.getAnnee());
      contactDAO.updateContact(contactToUpdate);
      dalServices.commitTransaction();
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  /**
   * Retrieves a contact by the company ID.
   *
   * @param idCompany the ID of the contact.
   * @return the contact.
   */
  public List<ContactDTO> getContactsByCompanyId(int idCompany) {
    if (myCompany.getEntreprise(idCompany) == null) {
      throw new NotFoundException("Company not found");
    }

    try {
      dalServices.startTransaction();
      List<ContactDTO> contacts = contactDAO.getContactsByCompanyId(idCompany);
      dalServices.commitTransaction();
      return contacts;
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  /**
   * Blacklist a company.
   *
   * @param idCompany the ID of the company to blacklist
   */
  public void blackListContact(int idCompany) {
    if (myCompany.getEntreprise(idCompany) == null) {
      throw new NotFoundException("Company not found");
    }
    try {
      dalServices.startTransaction();
      List<ContactDTO> contacts = contactDAO.getContactsByCompanyId(idCompany);
      for (ContactDTO contact : contacts) {
        if (contact.getEtatContact().equals("pris") || contact.getEtatContact().equals("initié")) {
          contact.setEtatContact("blacklisté");
          updateContact(contact);
        }
      }
      dalServices.commitTransaction();
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }
}
