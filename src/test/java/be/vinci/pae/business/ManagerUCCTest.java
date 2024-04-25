package be.vinci.pae.business;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.manager.ManagerDTO;
import be.vinci.pae.business.manager.ManagerUCC;
import be.vinci.pae.dal.manager.ManagerDAO;
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
 * Test ManagerUCC class.
 */
public class ManagerUCCTest {

  private ManagerUCC managerUCC;
  private Factory factory;
  private ManagerDAO responsableDAO;

  @BeforeEach
  void set() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    managerUCC = locator.getService(ManagerUCC.class);
    factory = locator.getService(Factory.class);
    responsableDAO = locator.getService(ManagerDAO.class);
    Mockito.reset(responsableDAO);
  }

  @Test
  @DisplayName("Test getManagers of ResponsableUCCImpl class")
  void testGetManagersDefault() {
    int companyId = 1;
    Mockito.when(responsableDAO.getManagers(companyId))
            .thenReturn(Arrays.asList(factory.getManagerDTO()));

    List<ManagerDTO> managers = managerUCC.getManagersByCompanyId(companyId);
    assertNotNull(managers);
  }

  @Test
  @DisplayName("Test getManagers of ResponsableUCCImpl class")
  void testGetManagersException() {
    int companyId = 1;
    Mockito.when(responsableDAO.getManagers(companyId)).thenReturn(null);

    assertThrows(NotFoundException.class, () -> {
      managerUCC.getManagersByCompanyId(companyId);
    });
  }


  @Test
  @DisplayName("Test addManager of ResponsableUCCImpl class if emails are the same")
  void testAddManagerEmailsAreTheSame() {
    ManagerDTO newManager = factory.getManagerDTO();
    newManager.setEmail("test@test.com");

    ManagerDTO existingManager = factory.getManagerDTO();
    existingManager.setEmail("test@test.com");

    Mockito.when(responsableDAO.getManager(newManager)).thenReturn(List.of(existingManager));

    assertThrows(ConflictException.class, () -> {
      managerUCC.addManager(newManager);
    });
  }

  @Test
  @DisplayName("Test addManager of ResponsableUCCImpl class")
  void testAddManagerDefault() {
    ManagerDTO managerDTO = factory.getManagerDTO();
    managerDTO.setName("test");
    managerDTO.setFirstName("test");
    managerDTO.setEmail("test@gmail.com");
    managerDTO.setPhone("048590000");
    managerDTO.setCompany(factory.getCompanyDTO());

    Mockito.when(responsableDAO.getManager(managerDTO)).thenReturn(null);
    Mockito.when(responsableDAO.getManagerByEmail(managerDTO.getEmail())).thenReturn(null);

    assertDoesNotThrow(() -> {
      managerUCC.addManager(managerDTO);
    });
  }

  @Test
  @DisplayName("addManager does not throw ConflictException "
          + "when manager with same email does not exist in database")
  void addManagerDoesNotThrowConflictWhenEmailDoesNotExistInDatabase() {
    ManagerDTO newManager = factory.getManagerDTO();
    newManager.setEmail("test@test.com");

    Mockito.when(responsableDAO.getManagerByEmail(newManager.getEmail())).thenReturn(null);

    assertDoesNotThrow(() -> {
      managerUCC.addManager(newManager);
    });
  }

  @Test
  @DisplayName("addManager does not throw ConflictException"
          + " when manager with empty email does not exist in database")
  void addManagerDoesNotThrowConflictWhenEmptyEmailDoesNotExistInDatabase() {
    ManagerDTO newManager = factory.getManagerDTO();
    newManager.setEmail("");

    Mockito.when(responsableDAO.getManagerByEmail(newManager.getEmail())).thenReturn(null);

    assertDoesNotThrow(() -> {
      managerUCC.addManager(newManager);
    });
  }

  @Test
  @DisplayName("addManager throws FatalException when an error occurs during the operation")
  void addManagerThrowsFatalExceptionWhenErrorOccurs() {
    ManagerDTO newManager = factory.getManagerDTO();
    newManager.setEmail("test.test@gmail.com");

    Mockito.when(responsableDAO.getManager(newManager)).thenReturn(null);
    Mockito.when(responsableDAO.getManagerByEmail(newManager.getEmail())).thenReturn(null);
    Mockito.doThrow(FatalException.class).when(responsableDAO).addManager(newManager);

    assertThrows(FatalException.class, () -> {
      managerUCC.addManager(newManager);
    });
  }

  @Test
  @DisplayName("addManager when it exists but the manager to "
          + "add has a different email so you can add it")
  void addManagerWhenItInsertsDefault() {
    ManagerDTO newManager = factory.getManagerDTO();
    newManager.setEmail("test@gmail.com");
    newManager.setPhone("13132");

    ManagerDTO existingManager = factory.getManagerDTO();
    existingManager.setEmail("test123@gmail.com");
    existingManager.setPhone("048590000");

    Mockito.when(responsableDAO.getManager(newManager)).thenReturn(List.of(existingManager));
    Mockito.when(responsableDAO.getManagerByEmail(newManager.getEmail())).thenReturn(null);

    assertDoesNotThrow(() -> {
      managerUCC.addManager(newManager);
    });
  }

  @Test
  @DisplayName("addManager when it exists but the manager to "
          + "add has a different email so you can add it")
  void addManagerWhenItInsertSamePhoneNr() {
    ManagerDTO newManager = factory.getManagerDTO();
    newManager.setEmail("test@gmail.com");
    newManager.setPhone("048590000");

    ManagerDTO existingManager = factory.getManagerDTO();
    existingManager.setEmail("test123@gmail.com");
    existingManager.setPhone("048590000");

    Mockito.when(responsableDAO.getManager(newManager)).thenReturn(List.of(existingManager));
    Mockito.when(responsableDAO.getManagerByEmail(newManager.getEmail())).thenReturn(null);

    assertThrows(ConflictException.class, () -> {
      managerUCC.addManager(newManager);
    });
  }

  @Test
  @DisplayName("Test addManager with ConflictException for empty email")
  void testAddManagerConflictExceptionForEmptyEmail() {
    ManagerDTO newManager = factory.getManagerDTO();
    newManager.setEmail("");

    ManagerDTO existingManager = factory.getManagerDTO();
    existingManager.setEmail("");

    Mockito.when(responsableDAO.getManager(newManager)).thenReturn(List.of(existingManager));

    assertThrows(ConflictException.class, () -> {
      managerUCC.addManager(newManager);
    });
  }

  @Test
  @DisplayName("Test addManager with ConflictException for existing email")
  void testAddManagerConflictExceptionForExistingEmail() {
    ManagerDTO newManager = factory.getManagerDTO();
    newManager.setEmail("existingEmail@example.com");

    ManagerDTO existingManager = factory.getManagerDTO();
    existingManager.setEmail("existingEmail@example.com");

    Mockito.when(responsableDAO.getManagerByEmail(newManager.getEmail()))
            .thenReturn(existingManager);

    assertThrows(ConflictException.class, () -> {
      managerUCC.addManager(newManager);
    });
  }


}