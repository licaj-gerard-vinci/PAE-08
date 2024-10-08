package be.vinci.pae.business;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.contact.Contact;
import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.year.Year;
import be.vinci.pae.business.year.YearDTO;
import be.vinci.pae.dal.company.CompanyDAO;
import be.vinci.pae.dal.contact.ContactDAO;
import be.vinci.pae.dal.user.UserDAO;
import be.vinci.pae.dal.year.YearDAO;
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
  private CompanyDAO companyDAO;
  private YearDAO yearDAO;
  private Year mockYear;

  @BeforeEach
  void setUpBeforeEach() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    contactUCC = locator.getService(ContactUCC.class);
    factory = locator.getService(Factory.class);
    contactDAO = locator.getService(ContactDAO.class);
    userDAO = locator.getService(UserDAO.class);
    companyDAO = locator.getService(CompanyDAO.class);
    yearDAO = locator.getService(YearDAO.class);
    mockYear = locator.getService(Year.class);
    Mockito.reset(contactDAO, userDAO, companyDAO, yearDAO, mockYear);
  }

  @Test
  @DisplayName("Test InsertContact of ContactUCC class with valid information")
  void testInsertContactDefault() {
    YearDTO year = factory.getYearDTO();
    year.setId(1);
    year.setYear("2021");

    UserDTO user = factory.getPublicUser();
    user.setId(1);

    CompanyDTO company = factory.getCompanyDTO();
    company.setId(1);

    ContactDTO contact = factory.getContactDTO();
    contact.setId(1);
    contact.setStudent(user);
    contact.setCompany(company);
    contact.setIdYear(year.getId());
    contact.setYear(year);

    Mockito.when(mockYear.renderCurrentYear()).thenReturn(year.getYear());
    Mockito.when(userDAO.getOneById(contact.getStudent().getId())).thenReturn(user);
    Mockito.when(companyDAO.getCompany(contact.getCompany().getId())).thenReturn(company);
    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(null);
    Mockito.when(yearDAO.getOneByYear(year.getYear())).thenReturn(year);

    assertDoesNotThrow(() -> contactUCC.insertContact(contact));
  }

  @Test
  @DisplayName("Test InsertContact of ContactUCC class with non-existing user")
  void testInsertContactNonExistingUser() {
    UserDTO user = factory.getPublicUser();
    user.setId(1);

    CompanyDTO company = factory.getCompanyDTO();
    company.setId(1);

    ContactDTO contact = factory.getContactDTO();
    contact.setId(1);
    contact.setStudent(user);
    contact.setCompany(company);

    Mockito.when(userDAO.getOneById(contact.getStudent().getId()))
            .thenReturn(null);
    Mockito.when(companyDAO.getCompany(contact.getCompany().getId())).thenReturn(company);
    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(null);

    assertThrows(NotFoundException.class, () -> {
      contactUCC.insertContact(contact);
    });
  }

  @Test
  @DisplayName("Test InsertContact of ContactUCC class with non-existing company")
  void testInsertContactNonExistingCompany() {
    UserDTO user = factory.getPublicUser();
    user.setId(1);

    CompanyDTO company = factory.getCompanyDTO();
    company.setId(1);

    ContactDTO contact = factory.getContactDTO();
    contact.setId(1);
    contact.setStudent(user);
    contact.setCompany(company);

    Mockito.when(userDAO.getOneById(contact.getStudent().getId())).thenReturn(user);
    Mockito.when(companyDAO.getCompany(contact.getCompany().getId()))
            .thenReturn(null);
    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(null);

    assertThrows(NotFoundException.class, () -> {
      contactUCC.insertContact(contact);
    });
  }

  @Test
  @DisplayName("Test InsertContact of ContactUCC class with existing contact")
  void testInsertContactAlreadyExistingContact() {
    UserDTO user = factory.getPublicUser();
    user.setId(1);

    CompanyDTO company = factory.getCompanyDTO();
    company.setId(1);

    ContactDTO contact = factory.getContactDTO();
    contact.setId(1);
    contact.setStudent(user);
    contact.setCompany(company);

    Mockito.when(userDAO.getOneById(contact.getStudent().getId())).thenReturn(user);
    Mockito.when(companyDAO.getCompany(contact.getCompany().getId())).thenReturn(company);
    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(contact);

    assertThrows(ConflictException.class, () -> {
      contactUCC.insertContact(contact);
    });
  }

  @Test
  @DisplayName("Test InsertContact of ContactUCC class when insert in the DAO failed")
  void testInsertContactThrowFatalException() {
    YearDTO year = factory.getYearDTO();
    year.setId(1);
    year.setYear("2021");

    UserDTO user = factory.getPublicUser();
    user.setId(1);

    CompanyDTO company = factory.getCompanyDTO();
    company.setId(1);

    ContactDTO contact = factory.getContactDTO();
    contact.setId(1);
    contact.setStudent(user);
    contact.setCompany(company);
    contact.setIdYear(year.getId());
    contact.setYear(year);

    Mockito.when(mockYear.renderCurrentYear()).thenReturn(year.getYear());
    Mockito.when(userDAO.getOneById(contact.getStudent().getId())).thenReturn(user);
    Mockito.when(companyDAO.getCompany(contact.getCompany().getId())).thenReturn(company);
    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(null);
    Mockito.when(yearDAO.getOneByYear(year.getYear())).thenReturn(year);
    Mockito.doThrow(FatalException.class)
            .when(contactDAO).insertContact(contact);

    assertThrows(FatalException.class, () -> {
      contactUCC.insertContact(contact);
    });
  }

  @Test
  @DisplayName("Test updateContact of ContactUCC class with valid information")
  void testUpdateContactDefault() {
    ContactDTO contact = factory.getContactDTO();
    ContactDTO contactReceived = factory.getContactDTO();

    contact.setId(1);
    contact.setContactStatus("valid state");

    contactReceived.setId(contact.getId());
    contactReceived.setContactStatus("valid state");
    contactReceived.setMeetingPlace("valid place");
    contactReceived.setRefusalReason("valid reason");

    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(contactReceived);

    assertAll(
            () -> assertNull(contact.getRefusalReason()),
            () -> assertNull(contact.getMeetingPlace()),
            () -> assertDoesNotThrow(() -> contactUCC.updateContact(contact)),
            () -> assertNotNull(contact.getMeetingPlace()),
            () -> assertNotNull(contact.getRefusalReason())
    );
  }

  @Test
  @DisplayName("Test updateContact of ContactUCC class with valid refusalReason")
  void testUpdateContactDefaultWithRefusalReason() {
    ContactDTO contact = factory.getContactDTO();
    contact.setId(1);
    contact.setContactStatus("valid state");
    contact.setRefusalReason("valid reason");

    ContactDTO contactReceived = factory.getContactDTO();
    contactReceived.setId(contact.getId());
    contactReceived.setContactStatus("valid state");
    contactReceived.setMeetingPlace("valid place");

    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(contactReceived);

    assertAll(
            () -> assertNotNull(contact.getRefusalReason()),
            () -> assertNull(contactReceived.getRefusalReason()),
            () -> assertDoesNotThrow(() -> contactUCC.updateContact(contact)),
            () -> assertNotNull(contact.getRefusalReason())
    );
  }

  @Test
  @DisplayName("Test updateContact of ContactUCC class with valid refusalReason")
  void testUpdateContactDefaultWithMeetingPlace() {
    ContactDTO contact = factory.getContactDTO();
    contact.setId(1);
    contact.setContactStatus("valid state");
    contact.setMeetingPlace("valid place");

    ContactDTO contactReceived = factory.getContactDTO();
    contactReceived.setId(contact.getId());
    contactReceived.setContactStatus("valid state");

    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(contactReceived);

    assertAll(
            () -> assertNotNull(contact.getMeetingPlace()),
            () -> assertNull(contactReceived.getMeetingPlace()),
            () -> assertDoesNotThrow(() -> contactUCC.updateContact(contact)),
            () -> assertNotNull(contact.getMeetingPlace())
    );
  }

  @Test
  @DisplayName("Test updateContact of ContactUCC class with valid information")
  void testUpdateContactInvalidState() {
    ContactDTO contact = factory.getContactDTO();
    Contact contactReceived = Mockito.mock(Contact.class);

    contact.setId(1);
    contact.setContactStatus("invalid state");

    Mockito.when(contactReceived.getId()).thenReturn(1);
    Mockito.when(contactReceived.getContactStatus()).thenReturn("valid state");
    Mockito.when(contactReceived.getMeetingPlace()).thenReturn("valid place");
    Mockito.when(contactReceived.getRefusalReason()).thenReturn("valid reason");

    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(contactReceived);
    Mockito.when(contactReceived.checkState(contactReceived.getContactStatus(),
            contact.getContactStatus())).thenReturn(false);

    assertThrows(BusinessException.class, () -> {
      contactUCC.updateContact(contact);
    });
  }

  @Test
  @DisplayName("Test InsertContact of ContactUCC class when insert in the DAO failed")
  void testUpdateContactNotFound() {
    ContactDTO contact = factory.getContactDTO();

    contact.setId(1);
    contact.setContactStatus("valid state");

    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(null);

    assertThrows(NotFoundException.class, () -> {
      contactUCC.updateContact(contact);
    });
  }

  @Test
  @DisplayName("Test InsertContact of ContactUCC class when insert in the DAO failed")
  void testUpdateContactFatalException() {
    ContactDTO contact = factory.getContactDTO();

    contact.setId(1);
    contact.setContactStatus("valid state");

    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(contact);
    Mockito.doThrow(FatalException.class).when(contactDAO).updateContact(contact);

    assertThrows(FatalException.class, () -> {
      contactUCC.updateContact(contact);
    });
  }

  @Test
  @DisplayName("Test getContactById of ContactUCC class with valid information")
  void testGetContactByIdDefault() {
    int idContact = 1;
    ContactDTO contact = factory.getContactDTO();

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
    int idContact = 1;

    Mockito.when(contactDAO.getContactById(idContact)).thenReturn(null);

    assertThrows(NotFoundException.class,
            () -> contactUCC.getContactByContactId(idContact));

  }

  @Test
  @DisplayName("Test getContactById of ContactUCC class when the DAO failed")
  void testGetContactByIdFatalException() {
    int idContact = 1;

    Mockito.when(contactDAO.getContactById(idContact))
            .thenThrow(FatalException.class);

    assertThrows(FatalException.class, () -> {
      contactUCC.getContactByContactId(idContact);
    });

  }

  @Test
  @DisplayName("Test getContactByCompanyId of ContactUCC class with valid information")
  void testGetContactByCompanyIdDefault() {
    int idCompany = 1;
    CompanyDTO company = factory.getCompanyDTO();
    ContactDTO contact1 = factory.getContactDTO();
    ContactDTO contact2 = factory.getContactDTO();

    Mockito.when(companyDAO.getCompany(idCompany)).thenReturn(company);
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
    int idCompany = 1;

    Mockito.when(companyDAO.getCompany(idCompany)).thenReturn(null);

    assertThrows(NotFoundException.class,
            () -> contactUCC.getContactsByCompanyId(idCompany));
  }

  @Test
  @DisplayName("Test getContactById of ContactUCC class when the DAO failed")
  void testGetContactByCompanyIdFatalException() {
    int idCompany = 1;
    CompanyDTO company = factory.getCompanyDTO();

    Mockito.when(companyDAO.getCompany(idCompany)).thenReturn(company);
    Mockito.when(contactDAO.getContactsByCompanyId(idCompany))
            .thenThrow(FatalException.class);

    assertThrows(FatalException.class, () -> {
      contactUCC.getContactsByCompanyId(idCompany);
    });
  }

  @Test
  @DisplayName("Test getContacts of ContactUCC class with contacts")
  void testGetContactsDefaultWithContacts() {
    ContactDTO contact1 = factory.getContactDTO();
    ContactDTO contact2 = factory.getContactDTO();

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
    Mockito.when(contactDAO.getContacts())
            .thenThrow(FatalException.class);

    assertThrows(FatalException.class, () -> contactUCC.getContacts());
  }

  @Test
  @DisplayName("Test getContactsAllInfo of ContactUCC class with contacts")
  void testGetContactsAllInfoDefaultWithContacts() {
    int userId = 1;
    UserDTO user = factory.getPublicUser();
    ContactDTO contact1 = factory.getContactDTO();
    ContactDTO contact2 = factory.getContactDTO();

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
    int userId = 1;
    UserDTO user = factory.getPublicUser();

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
    int userId = 1;

    Mockito.when(userDAO.getOneById(userId)).thenReturn(null);
    Mockito.when(contactDAO.getContactsAllInfo(userId))
            .thenReturn(new ArrayList<>());

    assertThrows(NotFoundException.class, () -> contactUCC.getContactsByUserId(userId));
  }

  @Test
  @DisplayName("Test getContactsAllInfo of ContactUCC class with non-existing user")
  void testGetContactsAllInfoFatalException() {
    int userId = 1;
    UserDTO user = factory.getPublicUser();

    Mockito.when(userDAO.getOneById(userId)).thenReturn(user);
    Mockito.when(contactDAO.getContactsAllInfo(userId))
            .thenThrow(FatalException.class);

    assertThrows(FatalException.class, () -> contactUCC.getContactsByUserId(userId));
  }

  @Test
  @DisplayName("Suspend contacts with valid user and contact")
  void suspendContactsWithValidUserAndContact() {
    int userId = 1;

    UserDTO user = factory.getPublicUser();
    user.setId(userId);

    ContactDTO contact1 = factory.getContactDTO();
    contact1.setId(2);
    contact1.setContactStatus("pris");

    ContactDTO contact12 = factory.getContactDTO();
    contact12.setId(2);
    contact12.setContactStatus("pris");

    ContactDTO contact2 = factory.getContactDTO();
    contact2.setId(3);
    contact2.setContactStatus("initié");

    ContactDTO contact22 = factory.getContactDTO();
    contact22.setId(3);
    contact22.setContactStatus("initié");

    int contactId = 1;

    ContactDTO contact3 = factory.getContactDTO();
    contact3.setId(contactId);
    contact3.setContactStatus("pris");

    ContactDTO contact32 = factory.getContactDTO();
    contact32.setId(contactId);
    contact32.setContactStatus("pris");

    Mockito.when(userDAO.getOneById(userId)).thenReturn(user);
    Mockito.when(contactDAO.getContactById(2))
            .thenReturn(contact12);
    Mockito.when(contactDAO.getContactById(3))
            .thenReturn(contact22);
    Mockito.when(contactDAO.getContactById(contactId))
            .thenReturn(contact32);
    Mockito.when(contactDAO.getContactsAllInfo(userId))
            .thenReturn(Arrays.asList(contact1, contact2, contact3));

    assertDoesNotThrow(() -> contactUCC.suspendContacts(userId, contactId));
    assertEquals("suspendu", contact1.getContactStatus());
    assertEquals("suspendu", contact2.getContactStatus());
    assertEquals("pris", contact3.getContactStatus());
  }

  @Test
  @DisplayName("Suspend contacts with non-existing user")
  void suspendContactsWithNonExistingUser() {
    int userId = 1;
    int contactId = 1;

    Mockito.when(userDAO.getOneById(userId)).thenReturn(null);

    assertThrows(NotFoundException.class, () -> contactUCC.suspendContacts(userId, contactId));
  }

  @Test
  @DisplayName("Suspend contacts with valid user and no contacts")
  void suspendContactsWithValidUserAndNoContacts() {
    int userId = 1;

    UserDTO user = factory.getPublicUser();
    user.setId(userId);

    Mockito.when(userDAO.getOneById(userId)).thenReturn(user);
    Mockito.when(contactDAO.getContactsAllInfo(userId)).thenReturn(new ArrayList<>());

    int contactId = 1;

    assertDoesNotThrow(() -> contactUCC.suspendContacts(userId, contactId));
  }

  @Test
  @DisplayName("Suspend contacts fatal Exception")
  void suspendContactsFatalException() {
    int userId = 1;

    UserDTO user = factory.getPublicUser();
    user.setId(userId);

    Mockito.when(userDAO.getOneById(userId)).thenReturn(user);
    Mockito.when(contactDAO.getContactsAllInfo(userId)).thenThrow(FatalException.class);

    int contactId = 1;

    assertThrows(FatalException.class, () -> contactUCC.suspendContacts(userId, contactId));
  }

}
