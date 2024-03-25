package be.vinci.pae.business.contact;

import be.vinci.pae.business.entreprise.EntrepriseUCC;
import be.vinci.pae.business.user.UserUCC;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.contact.ContactDAO;
import jakarta.inject.Inject;
import java.util.List;

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
    if(myUser.getOne(idUser) == null) {
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
      try {
      dalServices.startTransaction();
      ContactDTO contact = contactDAO.getContactById(idContact);
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
    if(getContactById(contact.getId()) == null){
      return;
    };

    if(myUser.getOne(contact.getUtilisateur().getId()) == null){
        return;
    };

    if(myCompany.getEntreprise(contact.getEntreprise().getId()) == null){
        return;
    };


    try {
      dalServices.startTransaction();

      if (checkContact(contact.getUtilisateur().getId(), contact.getEntreprise().getId())) {
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

  public void updateContact(ContactDTO contact) {
    System.out.println("enter updateContact method");
    System.out.println("contact status: " + contact.getEtatContact());
    System.out.println("contact userId: " + contact.getUtilisateur().getId());
    System.out.println("contact companyId: " + contact.getEntreprise().getId());

    if(getContactById(contact.getId()) == null){
      return;
    };

    try {
      dalServices.startTransaction();
      if(contact.getEtatContact().equals("taken")) {
        checkContactTaken(contact);
      } else if(contact.getEtatContact().equals("accepted")) {
        checkContactAccepted(contact);
      } else if(contact.getEtatContact().equals("refused")) {
        checkContactRefused(contact);
      } else if(contact.getEtatContact().equals("unsupervised")) {
        checkContactUnsupervised(contact);
      } else {
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
   */
  public boolean checkContactTaken(ContactDTO contact) {
    return checkContactAndState(contact.getUtilisateur().getId(),
            contact.getEntreprise().getId(), "initiated");
  }

  /**
   * Updates a contact to the 'accepted' state.
   *
   * @param contact the contact to update
   */
  public boolean checkContactAccepted(ContactDTO contact) {
    return checkContactAndState(contact.getUtilisateur().getId(),
            contact.getEntreprise().getId(), "taken");
  }

  /**
   * Updates a contact to the 'refused' state.
   *
   * @param contact the contact to update
   */
  public boolean checkContactRefused(ContactDTO contact) {
    return checkContactAndState(contact.getUtilisateur().getId(),
            contact.getEntreprise().getId(), "taken")
            && contact.getRaisonRefus() != null;
  }

  /**
   * Updates a contact to the 'unsupervised' state.
   *
   * @param contact the contact to update
   */
  public boolean checkContactUnsupervised(ContactDTO contact) {
    return checkContactAndState(contact.getUtilisateur().getId(),
            contact.getEntreprise().getId(), "initiated")
            || checkContactAndState(contact.getUtilisateur().getId(),
            contact.getEntreprise().getId(), "taken");
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


}
