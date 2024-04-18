package be.vinci.pae.business;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.stage.StageDTO;
import be.vinci.pae.business.stage.StageUCC;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.dal.contact.ContactDAO;
import be.vinci.pae.dal.stage.StageDAO;
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

  private StageUCC stageUCC;
  private StageDAO stageDAO;
  private Factory factory;
  private ContactDAO contactDAO;
  private UserDAO userDAO;

  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    stageUCC = locator.getService(StageUCC.class);
    factory = locator.getService(Factory.class);
    stageDAO = locator.getService(StageDAO.class);
    contactDAO = locator.getService(ContactDAO.class);
    userDAO = locator.getService(UserDAO.class);
    Mockito.reset(stageDAO, contactDAO, userDAO);
  }

  @Test
  @DisplayName("test get all stage succes")
  void testgetALL() {
    StageDTO stage1 = factory.getStageDTO();
    StageDTO stage2 = factory.getStageDTO();

    Mockito.when(stageDAO.getStages()).thenReturn(Arrays.asList(stage1, stage2));
    List<StageDTO> stage = stageUCC.getStages();
    assertEquals(2, stage.size());
  }

  @Test
  @DisplayName("fatal exception when trying to get all stage")
  void catchFatal() {
    Mockito.when(stageDAO.getStages()).thenThrow(new FatalException());
    assertThrows(FatalException.class, () -> stageUCC.getStages());
  }


  @Test
  @DisplayName("Succes get stage by user")
  void getStageByUser() {

    int userId = 7;
    StageDTO mockStage = factory.getStageDTO();
    Mockito.when(stageDAO.getStageById(userId)).thenReturn(mockStage);

    StageDTO resultStage = stageUCC.getInternshipByUserId(userId);

    assertNotNull(resultStage, "The result stage should not be null.");
    assertEquals(mockStage, resultStage,
        "The result stage should match the mock stage returned by DAO.");
  }


  @Test
  @DisplayName("Not Found Exception when stage by user is not found")
  void notFound() {
    // Arrange
    int userId = 1;
    Mockito.when(stageDAO.getStageById(userId)).thenReturn(null);
    assertNull(stageUCC.getInternshipByUserId(userId),
        "The result stage should be null when no stage is found for the user.");
  }


  @Test
  @DisplayName("Fatal exception when trying to get a stage")
  void catchFatale() {

    int userId = 1;
    Mockito.when(stageDAO.getStageById(userId)).thenThrow(new FatalException("Database error"));

    assertThrows(FatalException.class, () -> stageUCC.getInternshipByUserId(userId),
        "FatalException should be thrown when there is a database error.");
  }

  @Test
  @DisplayName("Test insert internship")
  void testInsertInternship() {
    StageDTO stage = factory.getStageDTO();
    ContactDTO contact1 = factory.getContactDTO();
    contact1.setId(1);
    contact1.setEtatContact("pris");
    ContactDTO contact2 = factory.getContactDTO();
    contact2.setEtatContact("pris");
    contact2.setId(1);
    UserDTO user = factory.getPublicUser();
    user.setId(1);
    EntrepriseDTO entreprise = factory.getEntrepriseDTO();
    entreprise.setId(1);
    contact1.setEntreprise(entreprise);
    contact1.setUtilisateur(user);
    contact2.setEntreprise(entreprise);
    contact2.setUtilisateur(user);
    stage.setEtudiant(user);
    stage.setContact(contact1);
    stage.setEntreprise(entreprise);
    Mockito.when(contactDAO.getContactById(contact1.getId())).thenReturn(contact1, contact2);
    Mockito.when(userDAO.getOneById(user.getId())).thenReturn(user);

    assertDoesNotThrow(() -> stageUCC.insertInternship(stage));
  }

  @Test
  @DisplayName("Test insert internship with ConflictException")
  void testInsertInternshipConflictException() {
      StageDTO stage = factory.getStageDTO();
      ContactDTO contact1 = factory.getContactDTO();
      contact1.setId(1);
      contact1.setEtatContact("pris");
      ContactDTO contact2 = factory.getContactDTO();
      contact2.setEtatContact("pris");
      contact2.setId(1);
      UserDTO user = factory.getPublicUser();
      user.setId(1);
      EntrepriseDTO entreprise = factory.getEntrepriseDTO();
      entreprise.setId(1);
      contact1.setEntreprise(entreprise);
      contact1.setUtilisateur(user);
      contact2.setEntreprise(entreprise);
      contact2.setUtilisateur(user);
      stage.setEtudiant(user);
      stage.setContact(contact1);
      stage.setEntreprise(entreprise);
      Mockito.when(contactDAO.getContactById(contact1.getId())).thenReturn(contact1, contact2);
      Mockito.when(userDAO.getOneById(user.getId())).thenReturn(user);
      Mockito.doThrow(new ConflictException()).when(stageDAO).insertInternship(stage);
      assertThrows(ConflictException.class, () -> stageUCC.insertInternship(stage));
  }

  @Test
  @DisplayName("Test insert internship with NotFoundException")
  void testInsertInternshipNotFoundException() {
      StageDTO stage = factory.getStageDTO();
      ContactDTO contact1 = factory.getContactDTO();
      contact1.setId(1);
      contact1.setEtatContact("pris");
      ContactDTO contact2 = factory.getContactDTO();
      contact2.setEtatContact("pris");
      contact2.setId(1);
      UserDTO user = factory.getPublicUser();
      user.setId(1);
      EntrepriseDTO entreprise = factory.getEntrepriseDTO();
      entreprise.setId(1);
      contact1.setEntreprise(entreprise);
      contact1.setUtilisateur(user);
      contact2.setEntreprise(entreprise);
      contact2.setUtilisateur(user);
      stage.setEtudiant(user);
      stage.setContact(contact1);
      stage.setEntreprise(entreprise);
      Mockito.when(contactDAO.getContactById(contact1.getId())).thenReturn(contact1, contact2);
      Mockito.when(userDAO.getOneById(user.getId())).thenReturn(user);
      Mockito.doThrow(new NotFoundException()).when(stageDAO).insertInternship(stage);
      assertThrows(NotFoundException.class, () -> stageUCC.insertInternship(stage));
  }

  @Test
  @DisplayName("Test insert internship with FatalException")
  void testInsertInternshipFatalException() {
      StageDTO stage = factory.getStageDTO();
      ContactDTO contact1 = factory.getContactDTO();
      contact1.setId(1);
      contact1.setEtatContact("pris");
      ContactDTO contact2 = factory.getContactDTO();
      contact2.setEtatContact("pris");
      contact2.setId(1);
      UserDTO user = factory.getPublicUser();
      user.setId(1);
      EntrepriseDTO entreprise = factory.getEntrepriseDTO();
      entreprise.setId(1);
      contact1.setEntreprise(entreprise);
      contact1.setUtilisateur(user);
      contact2.setEntreprise(entreprise);
      contact2.setUtilisateur(user);
      stage.setEtudiant(user);
      stage.setContact(contact1);
      stage.setEntreprise(entreprise);
      Mockito.when(contactDAO.getContactById(contact1.getId())).thenReturn(contact1, contact2);
      Mockito.when(userDAO.getOneById(user.getId())).thenReturn(user);
      Mockito.doThrow(new FatalException()).when(stageDAO).insertInternship(stage);
      assertThrows(FatalException.class, () -> stageUCC.insertInternship(stage));
  }

    @Test
    @DisplayName("Test insert internship with NotFoundException")
    void testInsertInternshipNotFoundException2() {
        StageDTO stage = factory.getStageDTO();
        ContactDTO contact1 = factory.getContactDTO();
        contact1.setId(1);
        contact1.setEtatContact("pris");
        ContactDTO contact2 = factory.getContactDTO();
        contact2.setEtatContact("pris");
        contact2.setId(1);
        UserDTO user = factory.getPublicUser();
        user.setId(1);
        EntrepriseDTO entreprise = factory.getEntrepriseDTO();
        entreprise.setId(1);
        contact1.setEntreprise(entreprise);
        contact1.setUtilisateur(user);
        contact2.setEntreprise(entreprise);
        contact2.setUtilisateur(user);
        stage.setEtudiant(user);
        stage.setContact(contact1);
        stage.setEntreprise(entreprise);
        Mockito.when(contactDAO.getContactById(contact1.getId())).thenReturn(contact1, contact2);
        Mockito.when(userDAO.getOneById(user.getId())).thenReturn(user);
        Mockito.doThrow(new NotFoundException()).when(stageDAO).insertInternship(stage);
        assertThrows(NotFoundException.class, () -> stageUCC.insertInternship(stage));
    }

  @Test
  @DisplayName("Test insertInternship with ConflictException for invalid contact")
  void testInsertInternshipConflictExceptionForInvalidContact() {
    StageDTO stage = factory.getStageDTO();
    ContactDTO contact = factory.getContactDTO();
    contact.setId(1);
    contact.setEtatContact("pris");
    UserDTO user = factory.getPublicUser();
    user.setId(1);
    EntrepriseDTO entreprise = factory.getEntrepriseDTO();
    entreprise.setId(1);
    contact.setEntreprise(entreprise);
    contact.setUtilisateur(user);
    stage.setEtudiant(user);
    stage.setContact(contact);
    stage.setEntreprise(entreprise);
    Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(null);
    assertThrows(NotFoundException.class, () -> stageUCC.insertInternship(stage));
  }

    @Test
    @DisplayName("Test insertInternship with ConflictException for invalid user")
    void testInsertInternshipConflictExceptionForInvalidUser() {
        StageDTO stage = factory.getStageDTO();
        ContactDTO contact = factory.getContactDTO();
        contact.setId(1);
        contact.setEtatContact("pris");
        UserDTO user = factory.getPublicUser();
        user.setId(1);
        EntrepriseDTO entreprise = factory.getEntrepriseDTO();
        entreprise.setId(1);
        contact.setEntreprise(entreprise);
        contact.setUtilisateur(user);
        stage.setEtudiant(user);
        stage.setContact(contact);
        stage.setEntreprise(entreprise);
        Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(contact);
        Mockito.when(userDAO.getOneById(user.getId())).thenReturn(null);
        assertThrows(NotFoundException.class, () -> stageUCC.insertInternship(stage));
    }

    @Test
    @DisplayName("Test insertInternship with ConflictException for invalid entreprise")
    void testInsertInternshipConflictExceptionForInvalidEntreprise() {
        StageDTO stage = factory.getStageDTO();
        ContactDTO contact = factory.getContactDTO();
        contact.setId(1);
        contact.setEtatContact("pris");
        UserDTO user = factory.getPublicUser();
        user.setId(1);
        EntrepriseDTO entreprise = factory.getEntrepriseDTO();
        entreprise.setId(1);
        contact.setEntreprise(entreprise);
        contact.setUtilisateur(user);
        stage.setEtudiant(user);
        stage.setContact(contact);
        stage.setEntreprise(entreprise);
        Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(contact);
        Mockito.when(userDAO.getOneById(user.getId())).thenReturn(user);
        Mockito.when(stageDAO.getStageById(user.getId())).thenReturn(stage);
        assertThrows(ConflictException.class, () -> stageUCC.insertInternship(stage));
    }

    @Test
    @DisplayName("Test insertInternship with ConflictException for invalid entreprise")
    void testInsertInternshipConflictExceptionForInvalidEntreprise2() {
        StageDTO stage = factory.getStageDTO();
        ContactDTO contact = factory.getContactDTO();
        contact.setId(1);
        contact.setEtatContact("pris");
        UserDTO user = factory.getPublicUser();
        user.setId(1);
        EntrepriseDTO entreprise = factory.getEntrepriseDTO();
        EntrepriseDTO entreprise2 = factory.getEntrepriseDTO();
        entreprise.setId(1);
        contact.setEntreprise(entreprise);
        contact.setUtilisateur(user);
        stage.setEtudiant(user);
        stage.setContact(contact);
        stage.setEntreprise(entreprise2);
        Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(contact);
        Mockito.when(userDAO.getOneById(user.getId())).thenReturn(user);
        assertThrows(NotFoundException.class, () -> stageUCC.insertInternship(stage));
    }

    @Test
    @DisplayName("Test insertInternship with ConflictException for invalid idUser")
    void testInsertInternshipConflictExceptionForInvalidIdUser() {
      StageDTO stage = factory.getStageDTO();
      ContactDTO contact = factory.getContactDTO();
      contact.setId(1);
      contact.setEtatContact("pris");
      UserDTO user = factory.getPublicUser();
      UserDTO user2 = factory.getPublicUser();
      user2.setId(2);
      user.setId(1);
      EntrepriseDTO entreprise = factory.getEntrepriseDTO();
      EntrepriseDTO entreprise2 = factory.getEntrepriseDTO();
      entreprise.setId(1);
      contact.setEntreprise(entreprise);
      contact.setUtilisateur(user);
      stage.setEtudiant(user);
      stage.setContact(contact);
      stage.setEntreprise(entreprise2);
      Mockito.when(contactDAO.getContactById(contact.getId())).thenReturn(contact);
      Mockito.when(userDAO.getOneById(user.getId())).thenReturn(user2);
      assertThrows(NotFoundException.class, () -> stageUCC.insertInternship(stage));
    }

    @Test
    @DisplayName("Test update internship")
    void testUpdateInternship() {
        StageDTO stage = factory.getStageDTO();
        stage.setId(1);
        ContactDTO contact1 = factory.getContactDTO();
        contact1.setId(1);
        contact1.setEtatContact("pris");
        ContactDTO contact2 = factory.getContactDTO();
        contact2.setEtatContact("pris");
        contact2.setId(1);
        UserDTO user = factory.getPublicUser();
        user.setId(1);
        EntrepriseDTO entreprise = factory.getEntrepriseDTO();
        entreprise.setId(1);
        contact1.setEntreprise(entreprise);
        contact1.setUtilisateur(user);
        contact2.setEntreprise(entreprise);
        contact2.setUtilisateur(user);
        stage.setEtudiant(user);
        stage.setContact(contact1);
        stage.setEntreprise(entreprise);
        Mockito.when(contactDAO.getContactById(contact1.getId())).thenReturn(contact1, contact2);
        Mockito.when(userDAO.getOneById(user.getId())).thenReturn(user);
        assertDoesNotThrow(() -> stageUCC.updateInternshipTopic(stage, stage.getId()));
    }

    @Test
    @DisplayName("Test update internship with ConflictException")
    void testUpdateInternshipConflictException() {
        StageDTO stage = factory.getStageDTO();
        stage.setId(1);
        ContactDTO contact1 = factory.getContactDTO();
        contact1.setId(1);
        contact1.setEtatContact("pris");
        ContactDTO contact2 = factory.getContactDTO();
        contact2.setEtatContact("pris");
        contact2.setId(1);
        UserDTO user = factory.getPublicUser();
        user.setId(1);
        EntrepriseDTO entreprise = factory.getEntrepriseDTO();
        entreprise.setId(1);
        contact1.setEntreprise(entreprise);
        contact1.setUtilisateur(user);
        contact2.setEntreprise(entreprise);
        contact2.setUtilisateur(user);
        stage.setEtudiant(user);
        stage.setContact(contact1);
        stage.setEntreprise(entreprise);
        Mockito.when(contactDAO.getContactById(contact1.getId())).thenReturn(contact1, contact2);
        Mockito.when(userDAO.getOneById(user.getId())).thenReturn(user);
        Mockito.doThrow(new FatalException()).when(stageDAO).updateInternshipTopic(stage);
        assertThrows(FatalException.class, () -> stageUCC.updateInternshipTopic(stage, stage.getId()));
    }

}
