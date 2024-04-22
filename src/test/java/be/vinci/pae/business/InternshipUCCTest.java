package be.vinci.pae.business;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.internship.InternshipDTO;
import be.vinci.pae.business.internship.InternshipUCC;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.dal.contact.ContactDAO;
import be.vinci.pae.dal.internship.InternshipDAO;
import be.vinci.pae.dal.user.UserDAO;
import be.vinci.pae.exceptions.ConflictException;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.exceptions.NotFoundException;
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
 * The {@code InternshipUCCTest} class tests the {@code InternshipUCC} class.
 */
public class InternshipUCCTest {

  private InternshipUCC internshipUCC;
  private InternshipDAO internshipDAO;
  private Factory factory;
  private ContactDAO contactDAO;
  private UserDAO userDAO;

  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    internshipUCC = locator.getService(InternshipUCC.class);
    factory = locator.getService(Factory.class);
    internshipDAO = locator.getService(InternshipDAO.class);
    contactDAO = locator.getService(ContactDAO.class);
    userDAO = locator.getService(UserDAO.class);
    Mockito.reset(internshipDAO, contactDAO, userDAO);
  }

  @Test
  @DisplayName("test get all stage succes")
  void testgetALL() {
    InternshipDTO stage1 = factory.getInternshipDTO();
    InternshipDTO stage2 = factory.getInternshipDTO();

    Mockito.when(internshipDAO.getInternship()).thenReturn(Arrays.asList(stage1, stage2));
    List<InternshipDTO> stage = internshipUCC.getInternship();
    assertEquals(2, stage.size());
  }

  @Test
  @DisplayName("fatal exception when trying to get all stage")
  void catchFatal() {
    Mockito.when(internshipDAO.getInternship()).thenThrow(new FatalException());
    assertThrows(FatalException.class, () -> internshipUCC.getInternship());
  }


  @Test
  @DisplayName("Succes get stage by user")
  void getStageByUser() {

    int userId = 7;
    InternshipDTO mockStage = factory.getInternshipDTO();
    Mockito.when(internshipDAO.getInternshipById(userId)).thenReturn(mockStage);

    InternshipDTO resultStage = internshipUCC.getInternshipByUserId(userId);

    assertNotNull(resultStage, "The result stage should not be null.");
    assertEquals(mockStage, resultStage,
        "The result stage should match the mock stage returned by DAO.");
  }


  @Test
  @DisplayName("Not Found Exception when stage by user is not found")
  void notFound() {
    // Arrange
    int userId = 1;
    Mockito.when(internshipDAO.getInternshipById(userId)).thenReturn(null);
    assertNull(internshipUCC.getInternshipByUserId(userId),
        "The result stage should be null when no stage is found for the user.");
  }


  @Test
  @DisplayName("Fatal exception when trying to get a stage")
  void catchFatale() {

    int userId = 1;
    Mockito.when(internshipDAO.getInternshipById(userId)).thenThrow(
        new FatalException("Database error"));

    assertThrows(FatalException.class, () -> internshipUCC.getInternshipByUserId(userId),
        "FatalException should be thrown when there is a database error.");
  }

  @Test
  @DisplayName("Test insert internship")
  void testInsertInternship() {
    ContactDTO contact1 = factory.getContactDTO();
    contact1.setId(1);
    contact1.setContactStatus("pris");
    ContactDTO contact2 = factory.getContactDTO();
    contact2.setContactStatus("pris");
    contact2.setId(1);
    UserDTO user = factory.getPublicUser();
    user.setId(1);
    CompanyDTO entreprise = factory.getCompanyDTO();
    entreprise.setId(1);
    contact1.setCompany(entreprise);
    contact1.setStudent(user);
    contact2.setCompany(entreprise);
    contact2.setStudent(user);
    InternshipDTO stage = factory.getInternshipDTO();
    stage.setStudent(user);
    stage.setContact(contact1);
    stage.setCompany(entreprise);
    Mockito.when(contactDAO.getContactById(contact1.getId())).thenReturn(contact1, contact2);
    Mockito.when(userDAO.getOneById(user.getId())).thenReturn(user);

    assertDoesNotThrow(() -> internshipUCC.insertInternship(stage));
  }

  @Test
  @DisplayName("Test insert internship with ConflictException")
  void testInsertInternshipConflictException() {
    ContactDTO contact1 = factory.getContactDTO();
    contact1.setId(1);
    contact1.setContactStatus("pris");
    ContactDTO contact2 = factory.getContactDTO();
    contact2.setContactStatus("pris");
    contact2.setId(1);
    UserDTO user = factory.getPublicUser();
    user.setId(1);
    CompanyDTO entreprise = factory.getCompanyDTO();
    entreprise.setId(1);
    contact1.setCompany(entreprise);
    contact1.setStudent(user);
    contact2.setCompany(entreprise);
    contact2.setStudent(user);
    InternshipDTO stage = factory.getInternshipDTO();
    stage.setStudent(user);
    stage.setContact(contact1);
    stage.setCompany(entreprise);
    Mockito.when(contactDAO.getContactById(contact1.getId())).thenReturn(contact1, contact2);
    Mockito.when(userDAO.getOneById(user.getId())).thenReturn(user);
    Mockito.doThrow(new ConflictException()).when(internshipDAO).insertInternship(stage);
    assertThrows(ConflictException.class, () -> internshipUCC.insertInternship(stage));
  }

  @Test
  @DisplayName("Test insert internship with NotFoundException")
  void testInsertInternshipNotFoundException() {
    ContactDTO contact1 = factory.getContactDTO();
    contact1.setId(1);
    contact1.setContactStatus("pris");
    ContactDTO contact2 = factory.getContactDTO();
    contact2.setContactStatus("pris");
    contact2.setId(1);
    UserDTO user = factory.getPublicUser();
    user.setId(1);
    CompanyDTO entreprise = factory.getCompanyDTO();
    entreprise.setId(1);
    contact1.setCompany(entreprise);
    contact1.setStudent(user);
    contact2.setCompany(entreprise);
    contact2.setStudent(user);
    InternshipDTO stage = factory.getInternshipDTO();
    stage.setStudent(user);
    stage.setContact(contact1);
    stage.setCompany(entreprise);
    Mockito.when(contactDAO.getContactById(contact1.getId())).thenReturn(contact1, contact2);
    Mockito.when(userDAO.getOneById(user.getId())).thenReturn(user);
    Mockito.doThrow(new NotFoundException()).when(internshipDAO).insertInternship(stage);
    assertThrows(NotFoundException.class, () -> internshipUCC.insertInternship(stage));
  }

  @Test
  @DisplayName("Test insert internship with FatalException")
  void testInsertInternshipFatalException() {
    ContactDTO contact1 = factory.getContactDTO();
    contact1.setId(1);
    contact1.setContactStatus("pris");
    ContactDTO contact2 = factory.getContactDTO();
    contact2.setContactStatus("pris");
    contact2.setId(1);
    UserDTO user = factory.getPublicUser();
    user.setId(1);
    CompanyDTO entreprise = factory.getCompanyDTO();
    entreprise.setId(1);
    contact1.setCompany(entreprise);
    contact1.setStudent(user);
    contact2.setCompany(entreprise);
    contact2.setStudent(user);
    InternshipDTO Internship = factory.getInternshipDTO();
    Internship.setStudent(user);
    Internship.setContact(contact1);
    Internship.setCompany(entreprise);
    Mockito.when(contactDAO.getContactById(contact1.getId())).thenReturn(contact1, contact2);
    Mockito.when(userDAO.getOneById(user.getId())).thenReturn(user);
    Mockito.doThrow(new FatalException()).when(internshipDAO).insertInternship(Internship);
    assertThrows(FatalException.class, () -> internshipUCC.insertInternship(Internship));
  }

  @Test
  @DisplayName("Test insertInternship with NotFoundException "
          +  "for null contact, invalid user and ConflictException for invalid entreprise")
  void testInsertInternshipWithMultipleExceptions() {
    assertAll(
            () -> {
              InternshipDTO Internship = factory.getInternshipDTO();
              UserDTO user = factory.getPublicUser();
              user.setId(1);
              CompanyDTO entreprise = factory.getCompanyDTO();
              entreprise.setId(1);
              Internship.setStudent(user);
              ContactDTO contact = factory.getContactDTO();
              Internship.setContact(contact);
              Internship.setCompany(entreprise);
              Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(null);
              assertThrows(NotFoundException.class, () ->
                  internshipUCC.insertInternship(Internship));
            },
            () -> {
              ContactDTO contact = factory.getContactDTO();
              contact.setId(1);
              contact.setContactStatus("pris");
              UserDTO user = factory.getPublicUser();
              user.setId(1);
              UserDTO differentUser = factory.getPublicUser();
              differentUser.setId(2);
              CompanyDTO company = factory.getCompanyDTO();
              company.setId(1);
              contact.setCompany(company);
              contact.setStudent(differentUser);
              InternshipDTO Internship = factory.getInternshipDTO();
              Internship.setStudent(user);
              Internship.setContact(contact);
              Internship.setCompany(company);
              Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(contact);
              assertThrows(NotFoundException.class, () ->
                  internshipUCC.insertInternship(Internship));
            },
            () -> {
              ContactDTO contact = factory.getContactDTO();
              contact.setId(1);
              contact.setContactStatus("pris");
              UserDTO user = factory.getPublicUser();
              user.setId(1);
              CompanyDTO entreprise = factory.getCompanyDTO();
              entreprise.setId(1);
              contact.setCompany(entreprise);
              contact.setStudent(user);
              InternshipDTO Internship = factory.getInternshipDTO();
              Internship.setStudent(user);
              Internship.setContact(contact);
              Internship.setCompany(entreprise);
              Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(contact);
              Mockito.when(userDAO.getOneById(user.getId())).thenReturn(user);
              Mockito.when(internshipDAO.getInternshipById(user.getId())).thenReturn(Internship);
              assertThrows(ConflictException.class, () ->
                  internshipUCC.insertInternship(Internship));
            }
    );
  }

  @Test
  @DisplayName("Test insertInternship with ConflictException for invalid entreprise")
  void testInsertInternshipConflictExceptionForInvalidEntreprise2() {
    ContactDTO contact = factory.getContactDTO();
    contact.setId(1);
    contact.setContactStatus("pris");
    UserDTO user = factory.getPublicUser();
    user.setId(1);
    CompanyDTO entreprise = factory.getCompanyDTO();
    entreprise.setId(1);
    contact.setCompany(entreprise);
    contact.setStudent(user);
    InternshipDTO stage = factory.getInternshipDTO();
    stage.setStudent(user);
    stage.setContact(contact);
    CompanyDTO entreprise2 = factory.getCompanyDTO();
    stage.setCompany(entreprise2);
    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(contact);
    Mockito.when(userDAO.getOneById(user.getId())).thenReturn(user);
    assertThrows(NotFoundException.class, () -> internshipUCC.insertInternship(stage));
  }

  @Test
  @DisplayName("Test insertInternship with NotFoundException for invalid contact")
  void testInsertInternshipNotFoundExceptionForInvalidContact() {
    ContactDTO contact = factory.getContactDTO();
    contact.setId(1);
    contact.setContactStatus("pris");
    UserDTO user = factory.getPublicUser();
    user.setId(1);
    CompanyDTO entreprise = factory.getCompanyDTO();
    entreprise.setId(1);
    contact.setCompany(entreprise);
    contact.setStudent(user);
    InternshipDTO stage = factory.getInternshipDTO();
    stage.setStudent(user);
    stage.setContact(contact);
    stage.setCompany(entreprise);
    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(null);
    assertThrows(NotFoundException.class, () -> internshipUCC.insertInternship(stage));
  }

  @Test
  @DisplayName("Test insertInternship with NotFoundException for invalid contact or user")
  void testInsertInternshipNotFoundExceptionForInvalidContactOrUser() {
    ContactDTO contact = factory.getContactDTO();
    contact.setId(1);
    contact.setContactStatus("pris");
    UserDTO user = factory.getPublicUser();
    user.setId(1);
    CompanyDTO entreprise = factory.getCompanyDTO();
    entreprise.setId(1);
    contact.setCompany(entreprise);
    contact.setStudent(user);
    InternshipDTO stage = factory.getInternshipDTO();
    stage.setStudent(user);
    stage.setContact(contact);
    stage.setCompany(entreprise);
    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(null);
    assertThrows(NotFoundException.class, () -> internshipUCC.insertInternship(stage));
  }

  @Test
  @DisplayName("Test update internship")
  void testUpdateInternship() {
    ContactDTO contact1 = factory.getContactDTO();
    contact1.setId(1);
    contact1.setContactStatus("pris");
    ContactDTO contact2 = factory.getContactDTO();
    contact2.setContactStatus("pris");
    contact2.setId(1);
    UserDTO user = factory.getPublicUser();
    user.setId(1);
    CompanyDTO entreprise = factory.getCompanyDTO();
    entreprise.setId(1);
    contact1.setCompany(entreprise);
    contact1.setStudent(user);
    contact2.setCompany(entreprise);
    contact2.setStudent(user);
    InternshipDTO stage = factory.getInternshipDTO();
    stage.setId(1);
    stage.setStudent(user);
    stage.setContact(contact1);
    stage.setCompany(entreprise);
    Mockito.when(contactDAO.getContactById(contact1.getId())).thenReturn(contact1, contact2);
    Mockito.when(userDAO.getOneById(user.getId())).thenReturn(user);
    assertDoesNotThrow(() -> internshipUCC.updateInternshipTopic(stage, stage.getId()));
  }

  @Test
  @DisplayName("Test update internship with ConflictException")
  void testUpdateInternshipConflictException() {
    ContactDTO contact1 = factory.getContactDTO();
    contact1.setId(1);
    contact1.setContactStatus("pris");
    ContactDTO contact2 = factory.getContactDTO();
    contact2.setContactStatus("pris");
    contact2.setId(1);
    UserDTO user = factory.getPublicUser();
    user.setId(1);
    CompanyDTO entreprise = factory.getCompanyDTO();
    entreprise.setId(1);
    contact1.setCompany(entreprise);
    contact1.setStudent(user);
    contact2.setCompany(entreprise);
    contact2.setStudent(user);
    InternshipDTO stage = factory.getInternshipDTO();
    stage.setId(1);
    stage.setStudent(user);
    stage.setContact(contact1);
    stage.setCompany(entreprise);
    Mockito.when(contactDAO.getContactById(contact1.getId())).thenReturn(contact1, contact2);
    Mockito.when(userDAO.getOneById(user.getId())).thenReturn(user);
    Mockito.doThrow(new FatalException()).when(internshipDAO).updateInternshipTopic(stage);
    assertThrows(FatalException.class, () ->
        internshipUCC.updateInternshipTopic(stage, stage.getId()));
  }

}
