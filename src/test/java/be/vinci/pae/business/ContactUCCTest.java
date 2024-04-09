package be.vinci.pae.business;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.dal.contact.ContactDAO;
import be.vinci.pae.dal.entreprise.EntrepriseDAO;
import be.vinci.pae.dal.user.UserDAO;
import be.vinci.pae.exceptions.ConflictException;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.exceptions.NotFoundException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import utils.ApplicationBinderTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    Mockito.reset(contactDAO, userDAO, companyDAO);
  }

  @Test
  @DisplayName("Test InsertContact of ContactUCC class with valid information")
  void testInsertContactDefault() {
    // Create a dummy Contact
    ContactDTO contact = factory.getContactDTO();
    UserDTO user = factory.getPublicUser();
    EntrepriseDTO company = factory.getEntrepriseDTO();

    user.setId(1);
    company.setId(1);

    contact.setId(1);
    contact.setUtilisateur(user); // Default user
    contact.setEntreprise(company); // Default company

    // Define the behavior of the mock
    Mockito.when(userDAO.getOneById(contact.getUtilisateur().getId())).thenReturn(user);
    Mockito.when(companyDAO.getEntreprise(contact.getEntreprise().getId())).thenReturn(company);
    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(null);

    // Everything return the expected value
    assertDoesNotThrow(() -> contactUCC.insertContact(contact));
  }

  @Test
  @DisplayName("Test InsertContact of ContactUCC class with non-existing user")
  void testInsertContactNonExistingUser() {
    // Create a dummy Contact
    ContactDTO contact = factory.getContactDTO();
    UserDTO user = factory.getPublicUser();
    EntrepriseDTO company = factory.getEntrepriseDTO();

    user.setId(1);
    company.setId(1);

    contact.setId(1);
    contact.setUtilisateur(user); // Default user
    contact.setEntreprise(company); // Default company

    // Define the behavior of the mock
    Mockito.when(userDAO.getOneById(contact.getUtilisateur().getId()))
        .thenThrow(new NotFoundException("User not found"));
    Mockito.when(companyDAO.getEntreprise(contact.getEntreprise().getId())).thenReturn(company);
    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(null);

    // user not found
    assertThrows(NotFoundException.class, () -> {
      contactUCC.insertContact(contact);
    });
  }

  @Test
  @DisplayName("Test InsertContact of ContactUCC class with non-existing company")
  void testInsertContactNonExistingCompany() {
    // Create a dummy Contact
    ContactDTO contact = factory.getContactDTO();
    UserDTO user = factory.getPublicUser();
    EntrepriseDTO company = factory.getEntrepriseDTO();

    user.setId(1);
    company.setId(1);

    contact.setId(1);
    contact.setUtilisateur(user); // Default user
    contact.setEntreprise(company); // Default company

    // Define the behavior of the mock
    Mockito.when(userDAO.getOneById(contact.getUtilisateur().getId())).thenReturn(user);
    Mockito.when(companyDAO.getEntreprise(contact.getEntreprise().getId()))
        .thenThrow(new NotFoundException("Company not found"));
    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(null);

    // company not found
    assertThrows(NotFoundException.class, () -> {
      contactUCC.insertContact(contact);
    });
  }

  @Test
  @DisplayName("Test InsertContact of ContactUCC class with existing contact")
  void testInsertContactAlreadyExistingContact() {
    // Create a dummy Contact
    ContactDTO contact = factory.getContactDTO();
    UserDTO user = factory.getPublicUser();
    EntrepriseDTO company = factory.getEntrepriseDTO();

    user.setId(1);
    company.setId(1);

    contact.setId(1);
    contact.setUtilisateur(user); // Default user
    contact.setEntreprise(company); // Default company

    // Define the behavior of the mock
    Mockito.when(userDAO.getOneById(contact.getUtilisateur().getId())).thenReturn(user);
    Mockito.when(companyDAO.getEntreprise(contact.getEntreprise().getId())).thenReturn(company);
    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(contact);

    // contact already exist
    assertThrows(ConflictException.class, () -> {
      contactUCC.insertContact(contact);
    });
  }

  @Test
  @DisplayName("Test InsertContact of ContactUCC class when insert in the DAO failed")
  void testInsertContactThrowFatalException() {
    // Create a dummy Contact
    ContactDTO contact = factory.getContactDTO();
    UserDTO user = factory.getPublicUser();
    EntrepriseDTO company = factory.getEntrepriseDTO();

    user.setId(1);
    company.setId(1);

    contact.setId(1);
    contact.setUtilisateur(user); // Default user
    contact.setEntreprise(company); // Default company

    // Define the behavior of the mock
    Mockito.when(userDAO.getOneById(contact.getUtilisateur().getId())).thenReturn(user);
    Mockito.when(companyDAO.getEntreprise(contact.getEntreprise().getId())).thenReturn(company);
    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(null);
    Mockito.doThrow(new FatalException("SQL Connection Error"))
            .when(contactDAO).insertContact(contact);

    assertThrows(FatalException.class, () ->{
      contactUCC.insertContact(contact);
    });
  }

}
