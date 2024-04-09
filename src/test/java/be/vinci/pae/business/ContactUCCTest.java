package be.vinci.pae.business;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import java.util.Arrays;
import java.util.List;

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
    Mockito.doThrow(FatalException.class)
            .when(contactDAO).insertContact(contact);

    assertThrows(FatalException.class, () ->{
      contactUCC.insertContact(contact);
    });
  }

  @Test
  @DisplayName("Test getContactById of ContactUCC class with valid information")
  void testGetContactByIdDefault() {
    // Create a dummy Contact
    int idContact = 1;
    ContactDTO contact = factory.getContactDTO();

    // Define the behavior of the mock
    Mockito.when(contactDAO.getContactById(idContact)).thenReturn(contact);
    ContactDTO contactReceived = contactDAO.getContactById(idContact);

    assertAll(
        () -> assertNotNull(contactReceived),
        () -> assertDoesNotThrow(() -> contactUCC.getContactById(idContact))
    );
  }

  @Test
  @DisplayName("Test getContactById of ContactUCC class with non-existing contact")
  void testGetContactByIdNotFound() {
    // Create a dummy Contact
    int idContact = 1;

    // Define the behavior of the mock
    Mockito.when(contactDAO.getContactById(idContact)).thenReturn(null);

    assertThrows(NotFoundException.class,
            () -> contactUCC.getContactById(idContact));

  }

  @Test
  @DisplayName("Test getContactById of ContactUCC class when the DAO failed")
  void testGetContactByIdFatalException() {
    // Create a dummy Contact
    int idContact = 1;

    // Define the behavior of the mock
    Mockito.when(contactDAO.getContactById(idContact))
            .thenThrow(FatalException.class);

    assertThrows(FatalException.class, () -> {
      contactUCC.getContactById(idContact);
    });

  }

  @Test
  @DisplayName("Test getContactById of ContactUCC class when the DAO failed")
  void testGetContactByCompanyIdDefault() {
    // Create a dummy Contact
    int idCompany = 1;
    EntrepriseDTO company = factory.getEntrepriseDTO();
    ContactDTO contact1 = factory.getContactDTO();
    ContactDTO contact2 = factory.getContactDTO();

    // Define the behavior of the mock
    Mockito.when(companyDAO.getEntreprise(idCompany)).thenReturn(company);
    Mockito.when(contactDAO.getContactsByCompanyId(idCompany))
            .thenReturn(Arrays.asList(contact1, contact2));

    List<ContactDTO> contacts = contactDAO.getContactsByCompanyId(idCompany);

    assertAll(
        () -> assertNotNull(contacts),
        () -> assertDoesNotThrow(() -> contactUCC.getContactsByCompanyId(idCompany))
    );
  }

  @Test
  @DisplayName("Test getContactById of ContactUCC class when the DAO failed")
  void testGetContactByCompanyIdNotFound() {
    // Create a dummy Contact
    int idCompany = 1;

    // Define the behavior of the mock
    Mockito.when(contactDAO.getContactsByCompanyId(idCompany)).thenReturn(null);

    assertThrows(NotFoundException.class,
            () -> contactUCC.getContactsByCompanyId(idCompany));
  }

  @Test
  @DisplayName("Test getContactById of ContactUCC class when the DAO failed")
  void testGetContactByCompanyIdFatalException() {
    // Create a dummy Contact
    int idCompany = 1;
    EntrepriseDTO company = factory.getEntrepriseDTO();

    // Define the behavior of the mock
    Mockito.when(companyDAO.getEntreprise(idCompany)).thenReturn(company);
    Mockito.when(contactDAO.getContactsByCompanyId(idCompany))
            .thenThrow(FatalException.class);

    assertThrows(FatalException.class, () -> {
      contactUCC.getContactsByCompanyId(idCompany);
    });
  }
}
