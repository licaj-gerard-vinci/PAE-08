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
import jakarta.ws.rs.ext.ParamConverter.Lazy;
import java.util.List;
import org.glassfish.hk2.api.Immediate;

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
      dalServices.openConnection();
      return contactDAO.getContacts();
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
  @Override
  public List<ContactDTO> getContactsByUserId(int idUser) {
    if (myUser.getOne(idUser) == null) {
      throw new NotFoundException("User not found");
    }
    try {
      dalServices.openConnection();
      return contactDAO.getContactsAllInfo(idUser);
    } finally {
      dalServices.close();
    }
  }

  /**
   * Retrieves a contact by its ID.
   *
   * @param idContact the ID of the contact
   * @return the contact
   */
  @Override
  public ContactDTO getContactByContactId(int idContact) {
    try {
      dalServices.openConnection();
      ContactDTO contact = contactDAO.getContactById(idContact);
      if (contact == null) {
        throw new NotFoundException("Contact not found");
      }
      return contact;
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
    if (contactDAO.getContactById(contact.getId()) != null) {
      throw new ConflictException("Contact already exists");
    }

    if (myUser.getOne(contact.getUtilisateur().getId()) == null) {
      throw new NotFoundException("User not found");
    }

    if (myCompany.getCompanyById(contact.getEntreprise().getId()) == null) {
      throw new NotFoundException("Company not found");
    }

    try {
      dalServices.startTransaction();
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
    Contact contactToVerify = (Contact) contactDAO.getContactById(contactToUpdate.getId());

    if (contactToVerify == null) {
      throw new NotFoundException("Contact not found");
    }

    if (!contactToVerify.checkState(contactToVerify.getEtatContact(),
        contactToUpdate.getEtatContact())) {
      throw new BusinessException("Invalid state");
    }

    if (contactToVerify.getLieuxRencontre() != null) {
      contactToUpdate.setLieuxRencontre(contactToVerify.getLieuxRencontre());
    }

    if (contactToVerify.getRaisonRefus() != null) {
      contactToUpdate.setRaisonRefus(contactToVerify.getRaisonRefus());
    }

    contactToUpdate.setId(contactToVerify.getId());
    contactToUpdate.setAnnee(contactToVerify.getAnnee());

    try {
      dalServices.startTransaction();
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
    if (myCompany.getCompanyById(idCompany) == null) {
      throw new NotFoundException("Company not found");
    }
    try {
      dalServices.openConnection();
      return contactDAO.getContactsByCompanyId(idCompany);
    } finally {
      dalServices.close();
    }
  }

  /**
   * Blacklist a company.
   *
   * @param idCompany the ID of the company to blacklist
   */
  public void blackListContact(int idCompany) {
    if (myCompany.getCompanyById(idCompany) == null) {
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
