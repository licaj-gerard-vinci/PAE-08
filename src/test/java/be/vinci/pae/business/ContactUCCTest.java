package be.vinci.pae.business;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.vinci.pae.business.contact.Contact;
import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.dal.contact.ContactDAO;
import be.vinci.pae.dal.entreprise.EntrepriseDAO;
import be.vinci.pae.dal.user.UserDAO;
import be.vinci.pae.exceptions.BusinessException;
import be.vinci.pae.exceptions.ConflictException;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.exceptions.NotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import utils.ApplicationBinderTest;

/**
 * The {@code ContactUCCTest} class tests the {@code ContactUCC} class.
 */
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
        .thenReturn(null);
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
        .thenReturn(null);
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

    assertThrows(FatalException.class, () -> {
      contactUCC.insertContact(contact);
    });
  }

  @Test
  @DisplayName("Test updateContact of ContactUCC class with valid information")
  void testUpdateContactDefault() {
    // Create a dummy Contact
    ContactDTO contact = factory.getContactDTO();
    ContactDTO contactReceived = factory.getContactDTO();

    contact.setId(1);
    contact.setEtatContact("valid state");

    contactReceived.setId(contact.getId());
    contactReceived.setEtatContact("valid state");
    contactReceived.setLieuxRencontre("valid place");
    contactReceived.setRaisonRefus("valid reason");

    // Define the behavior of the mock
    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(contactReceived);

    assertAll(
        () -> assertNull(contact.getRaisonRefus()),
        () -> assertNull(contact.getLieuxRencontre()),
        () -> assertDoesNotThrow(() -> contactUCC.updateContact(contact)),
        () -> assertNotNull(contact.getLieuxRencontre()),
        () -> assertNotNull(contact.getRaisonRefus())
    // after updating, the contact now have values for refusal reason and meeting place
    );
  }

  @Test
  @DisplayName("Test updateContact of ContactUCC class with valid refusalReason")
  void testUpdateContactDefaultWithRefusalReason() {
    // Create a dummy Contact
    ContactDTO contact = factory.getContactDTO();
    contact.setId(1);
    contact.setEtatContact("valid state");
    contact.setRaisonRefus("valid reason");

    ContactDTO contactReceived = factory.getContactDTO();
    contactReceived.setId(contact.getId());
    contactReceived.setEtatContact("valid state");
    contactReceived.setLieuxRencontre("valid place");

    // Define the behavior of the mock
    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(contactReceived);

    assertAll(
        () -> assertNotNull(contact.getRaisonRefus()),
        () -> assertNull(contactReceived.getRaisonRefus()),
        () -> assertDoesNotThrow(() -> contactUCC.updateContact(contact)),
        () -> assertNotNull(contact.getRaisonRefus())
    // after updating, the contact now have values for refusal reason and meeting place
    );
  }

  @Test
  @DisplayName("Test updateContact of ContactUCC class with valid refusalReason")
  void testUpdateContactDefaultWithMeetingPlace() {
    // Create a dummy Contact
    ContactDTO contact = factory.getContactDTO();
    contact.setId(1);
    contact.setEtatContact("valid state");
    contact.setLieuxRencontre("valid place");

    ContactDTO contactReceived = factory.getContactDTO();
    contactReceived.setId(contact.getId());
    contactReceived.setEtatContact("valid state");

    // Define the behavior of the mock
    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(contactReceived);

    assertAll(
        () -> assertNotNull(contact.getLieuxRencontre()),
        () -> assertNull(contactReceived.getLieuxRencontre()),
        () -> assertDoesNotThrow(() -> contactUCC.updateContact(contact)),
        () -> assertNotNull(contact.getLieuxRencontre())
    // after updating, the contact now have values for refusal reason and meeting place
    );
  }

  @Test
  @DisplayName("Test updateContact of ContactUCC class with valid information")
  void testUpdateContactInvalidState() {
    // Create a dummy Contact
    ContactDTO contact = factory.getContactDTO();
    Contact contactReceived = Mockito.mock(Contact.class);
    
    contact.setId(1);
    contact.setEtatContact("invalid state");

    Mockito.when(contactReceived.getId()).thenReturn(1);
    Mockito.when(contactReceived.getEtatContact()).thenReturn("valid state");
    Mockito.when(contactReceived.getLieuxRencontre()).thenReturn("valid place");
    Mockito.when(contactReceived.getRaisonRefus()).thenReturn("valid reason");

    // Define the behavior of the mock
    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(contactReceived);
    // Stub the checkState method to return false
    Mockito.when(contactReceived.checkState(contactReceived.getEtatContact(),
                    contact.getEtatContact())).thenReturn(false);

    assertThrows(BusinessException.class, () -> {
      contactUCC.updateContact(contact);
    });
  }

  @Test
  @DisplayName("Test InsertContact of ContactUCC class when insert in the DAO failed")
  void testUpdateContactNotFound() {
    // Create a dummy Contact
    ContactDTO contact = factory.getContactDTO();

    contact.setId(1);
    contact.setEtatContact("valid state");

    // Define the behavior of the mock
    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(null);

    assertThrows(NotFoundException.class, () -> {
      contactUCC.updateContact(contact);
    });
  }

  @Test
  @DisplayName("Test InsertContact of ContactUCC class when insert in the DAO failed")
  void testUpdateContactFatalException() {
    // Create a dummy Contact
    ContactDTO contact = factory.getContactDTO();

    contact.setId(1);
    contact.setEtatContact("valid state");

    // Define the behavior of the mock
    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(contact);
    Mockito.doThrow(FatalException.class).when(contactDAO).updateContact(contact);

    assertThrows(FatalException.class, () -> {
      contactUCC.updateContact(contact);
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
        () -> assertDoesNotThrow(() -> contactUCC.getContactByContactId(idContact)),
        () -> assertNotNull(contactReceived)
    );
  }

  @Test
  @DisplayName("Test getContactById of ContactUCC class with non-existing contact")
  void testGetContactByIdNotExistingContact() {
    // Create a dummy Contact
    int idContact = 1;

    // Define the behavior of the mock
    Mockito.when(contactDAO.getContactById(idContact)).thenReturn(null);

    assertThrows(NotFoundException.class,
        () -> contactUCC.getContactByContactId(idContact));

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
      contactUCC.getContactByContactId(idContact);
    });

  }

  @Test
  @DisplayName("Test getContactByCompanyId of ContactUCC class with valid information")
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
        () -> assertEquals(2, contacts.size()),
        () -> assertDoesNotThrow(() -> contactUCC.getContactsByCompanyId(idCompany))
    );
  }

  @Test
  @DisplayName("Test getContactByCompanyId of ContactUCC class with non-existing company")
  void testGetContactByCompanyIdNonExistingCompany() {
    // Create a dummy Contact
    int idCompany = 1;

    // Define the behavior of the mock
    Mockito.when(companyDAO.getEntreprise(idCompany)).thenReturn(null);

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

  @Test
  @DisplayName("Test getContacts of ContactUCC class with contacts")
  void testGetContactsDefaultWithContacts() {
    // Create a dummy Contact
    ContactDTO contact1 = factory.getContactDTO();
    ContactDTO contact2 = factory.getContactDTO();

    // Define the behavior of the mock
    Mockito.when(contactDAO.getContacts())
            .thenReturn(Arrays.asList(contact1, contact2));

    List<ContactDTO> contacts = contactDAO.getContacts();

    assertAll(
        () -> assertDoesNotThrow(() -> contactUCC.getContacts()),
        () -> assertNotNull(contacts),
        () -> assertEquals(2, contacts.size())
    );
  }

  @Test
  @DisplayName("Test getContacts of ContactUCC class without contacts")
  void testGetContactsDefaultWithoutContacts() {
    // Create a dummy Contact

    // Define the behavior of the mock
    Mockito.when(contactDAO.getContacts())
            .thenReturn(new ArrayList<>());

    List<ContactDTO> contacts = contactDAO.getContacts();

    assertAll(
        () -> assertDoesNotThrow(() -> contactUCC.getContacts()),
        () -> assertNotNull(contacts),
        () -> assertTrue(contacts.isEmpty())
    );
  }

  @Test
  @DisplayName("Test getContacts of ContactUCC class with no contacts")
  void testGetContactsFatalException() {
    // Create a dummy Contact

    // Define the behavior of the mock
    Mockito.when(contactDAO.getContacts())
            .thenThrow(FatalException.class);

    assertThrows(FatalException.class, () -> contactUCC.getContacts());
  }

  @Test
  @DisplayName("Test getContactsAllInfo of ContactUCC class with contacts")
  void testGetContactsAllInfoDefaultWithContacts() {
    // Create a dummy Contact
    int userId = 1;
    UserDTO user = factory.getPublicUser();
    ContactDTO contact1 = factory.getContactDTO();
    ContactDTO contact2 = factory.getContactDTO();

    // Define the behavior of the mock
    Mockito.when(userDAO.getOneById(userId)).thenReturn(user);
    Mockito.when(contactDAO.getContactsAllInfo(userId))
            .thenReturn(Arrays.asList(contact1, contact2));

    List<ContactDTO> contacts = contactDAO.getContactsAllInfo(userId);

    assertAll(
        () -> assertDoesNotThrow(() -> contactUCC.getContactsByUserId(userId)),
        () -> assertNotNull(contacts),
        () -> assertEquals(2, contacts.size())
    );
  }

  @Test
  @DisplayName("Test getContactsAllInfo of ContactUCC class without contacts")
  void testGetContactsAllInfoDefaultWithoutContacts() {
    // Create a dummy Contact
    int userId = 1;
    UserDTO user = factory.getPublicUser();

    // Define the behavior of the mock
    Mockito.when(userDAO.getOneById(userId)).thenReturn(user);
    Mockito.when(contactDAO.getContactsAllInfo(userId))
            .thenReturn(new ArrayList<>());

    List<ContactDTO> contacts = contactDAO.getContacts();

    assertAll(
        () -> assertDoesNotThrow(() -> contactUCC.getContactsByUserId(userId)),
        () -> assertNotNull(contacts),
        () -> assertTrue(contacts.isEmpty())
    );
  }

  @Test
  @DisplayName("Test getContactsAllInfo of ContactUCC class with non-existing user")
  void testGetContactsAllInfoWithNonExistingUser() {
    // Create a dummy Contact
    int userId = 1;

    // Define the behavior of the mock
    Mockito.when(userDAO.getOneById(userId)).thenReturn(null);
    Mockito.when(contactDAO.getContactsAllInfo(userId))
            .thenReturn(new ArrayList<>());

    assertThrows(NotFoundException.class, () -> contactUCC.getContactsByUserId(userId));
  }

  @Test
  @DisplayName("Test getContactsAllInfo of ContactUCC class with non-existing user")
  void testGetContactsAllInfoFatalException() {
    // Create a dummy Contact
    int userId = 1;
    UserDTO user = factory.getPublicUser();

    // Define the behavior of the mock
    Mockito.when(userDAO.getOneById(userId)).thenReturn(user);
    Mockito.when(contactDAO.getContactsAllInfo(userId))
            .thenThrow(FatalException.class);

    assertThrows(FatalException.class, () -> contactUCC.getContactsByUserId(userId));
  }
}
