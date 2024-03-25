package be.vinci.pae.business.contact;

import be.vinci.pae.business.entreprise.EntrepriseUCC;
import be.vinci.pae.business.user.UserUCC;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.contact.ContactDAO;
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
  @Override
  public List<ContactDTO> getContactsAllInfo(int idUser) {
    if (myUser.getOne(idUser) == null) {
      return null;
    }
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
   * Retrieves a contact by its ID.
   *
   * @param idContact the ID of the contact
   * @return the contact
   */
  @Override
  public ContactDTO getContactById(int idContact) {
    System.out.println("enter getContactById from ucc");
    try {
      dalServices.startTransaction();
      ContactDTO contact = contactDAO.getContactById(idContact);
      System.out.println("contact: " + contact);
      dalServices.commitTransaction();
      return contact;
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
    if (myUser.getOne(contact.getUtilisateur().getId()) == null) {
      return;
    }

    if (myCompany.getEntreprise(contact.getEntreprise().getId()) == null) {
      return;
    }

    try {
      dalServices.startTransaction();

      if (checkContact(contact.getUtilisateur().getId(), contact.getEntreprise().getId()) != null) {
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
   * Updates a contact.
   *
   * @param contact the contact to update
   */
  public void updateContact(ContactDTO contact) {
    System.out.println("enter updateContact method");
    System.out.println("contact status: " + contact.getEtatContact());
    System.out.println("contact userId: " + contact.getUtilisateur().getId());
    System.out.println("contact companyId: " + contact.getEntreprise().getId());
    System.out.println("Contact id: " + contact.getId());
    ContactDTO contact2 = checkContact(contact.getUtilisateur()
            .getId(), contact.getEntreprise().getId());
    System.out.println("contact: " + contact2);
    System.out.println("Contact2 id: " + contact2.getId());

    if (getContactById(contact2.getId()) == null) {
      return;
    }

    if (contact2.getLieuxRencontre() != null) {
      contact.setLieuxRencontre(contact2.getLieuxRencontre());
    }

    if (contact2.getRaisonRefus() != null) {
      contact.setRaisonRefus(contact2.getRaisonRefus());
    }

    try {
      dalServices.startTransaction();
      if (contact.getEtatContact().equals("admitted")) {
        checkContactTaken(contact);
      } else if (contact.getEtatContact().equals("accepted")) {
        System.out.println("enter accepted if");
        checkContactAccepted(contact);
      } else if (contact.getEtatContact().equals("turned down")) {
        System.out.println("enter refused if");
        checkContactRefused(contact);
      } else if (contact.getEtatContact().equals("unsupervised")) {
        System.out.println("enter unsupervised if");
        checkContactUnsupervised(contact);
      } else {
        System.out.println("enter etat non trouvee");
        throw new IllegalArgumentException("etat du contact non valide");
      }
      contactDAO.updateContact(contact);
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
   * @return true if the contact can be updated, false otherwise
   */
  public boolean checkContactTaken(ContactDTO contact) {
    System.out.println("enter taken method");
    return checkContactAndState(contact.getUtilisateur().getId(),
            contact.getEntreprise().getId(), "started");
  }

  /**
   * Updates a contact to the 'accepted' state.
   *
   * @param contact the contact to update
   * @return true if the contact can be updated, false otherwise
   */
  public boolean checkContactAccepted(ContactDTO contact) {
    System.out.println("enter accepted method");
    return checkContactAndState(contact.getUtilisateur().getId(),
            contact.getEntreprise().getId(), "admitted");
  }

  /**
   * Updates a contact to the 'refused' state.
   *
   * @param contact the contact to update
   * @return true if the contact can be updated, false otherwise
   */
  public boolean checkContactRefused(ContactDTO contact) {
    System.out.println("enter refused method");
    return checkContactAndState(contact.getUtilisateur().getId(),
            contact.getEntreprise().getId(), "admitted")
            && contact.getRaisonRefus() != null;
  }

  /**
   * Updates a contact to the 'unsupervised' state.
   *
   * @param contact the contact to update
   * @return true if the contact can be updated, false otherwise
   */
  public boolean checkContactUnsupervised(ContactDTO contact) {
    System.out.println("enter unsupervised method");
    return checkContactAndState(contact.getUtilisateur().getId(),
            contact.getEntreprise().getId(), "started")
            || checkContactAndState(contact.getUtilisateur().getId(),
            contact.getEntreprise().getId(), "admitted");
  }

  /**
   * Checks if a contact exists between a user and a company.
   *
   * @param idUser the ID of the user
   * @param idEntreprise the ID of the company
   * @return true if the contact exists, false otherwise
   */
  public ContactDTO checkContact(int idUser, int idEntreprise) {
    System.out.println("idUser: " + idUser + ", idEntreprise: " + idEntreprise);
    return contactDAO.checkContactExists(idUser, idEntreprise);
  }


  /**
   * Checks if a contact exists between a user and a company and
   * if the contact is in a specific state.
   *
   * @param idUser the ID of the user
   * @param idEntreprise the ID of the company
   * @param etat the state of the contact
   * @return true if the contact exists and is in the specified state, false otherwise
   */
  public boolean checkContactAndState(int idUser, int idEntreprise, String etat) {
    return contactDAO.checkContactAndState(idUser, idEntreprise, etat);
  }


}
