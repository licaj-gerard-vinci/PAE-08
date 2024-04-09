package be.vinci.pae.business;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.dal.contact.ContactDAO;
import be.vinci.pae.dal.entreprise.EntrepriseDAO;
import be.vinci.pae.dal.user.UserDAO;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import utils.ApplicationBinderTest;

public class ContactUCCTest {

  private ContactUCC contactUCC;
  private Factory factory;
  private ContactDAO contactDAO;
  private UserDAO userDAO;
  private EntrepriseDAO companyDAO;

  @BeforeEach
  void setUpBeforeEach() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    contactUCC = locator.getService(ContactUCC.class);
    factory = locator.getService(Factory.class);
    contactDAO = locator.getService(ContactDAO.class);
    userDAO = locator.getService(UserDAO.class);
    companyDAO = locator.getService(EntrepriseDAO.class);
  }

  @Test
  @DisplayName("Test ContactUCC InsertContact")
  void testContactUCCInsertContact() {
    // Create a dummy Contact
    ContactDTO contact = factory.getContactDTO();
    UserDTO user = factory.getPublicUser();
    EntrepriseDTO company = factory.getEntrepriseDTO();

    user.setId(1);
    company.setId(1);

    contact.setId(1);
    contact.setUtilisateur(user); // Default user
    contact.setEntreprise(company); // Default company
    contact.setEtatContact("valid state"); // Default status

    // Define the behavior of the mock
    Mockito.when(userDAO.getOneById(contact.getUtilisateur().getId())).thenReturn(user);
    Mockito.when(companyDAO.getEntreprise(contact.getEntreprise().getId())).thenReturn(company);
    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(null);

    // Call the method to test
    contactUCC.insertContact(contact);

    // Verify that the method was called on the mock
    Mockito.verify(contactDAO).insertContact(contact);
  }
}
