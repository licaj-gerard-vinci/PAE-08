package be.vinci.pae.business;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.responsable.ResponsableDTO;
import be.vinci.pae.business.responsable.ResponsableUCC;
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

  private ResponsableUCC responsableUCC;
  private Factory factory;
  private ManagerDAO responsableDAO;

  @BeforeEach
  void set() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    responsableUCC = locator.getService(ResponsableUCC.class);
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

    List<ResponsableDTO> managers = responsableUCC.getManagersByCompanyId(companyId);
    assertNotNull(managers);
  }

  @Test
  @DisplayName("Test getManagers of ResponsableUCCImpl class")
  void testGetManagersException() {
    int companyId = 1;
    Mockito.when(responsableDAO.getManagers(companyId)).thenReturn(null);

    assertThrows(NotFoundException.class, () -> {
      responsableUCC.getManagersByCompanyId(companyId);
    });
  }


  @Test
  @DisplayName("Test addManager of ResponsableUCCImpl class if emails are the same")
  void testAddManagerEmailsAreTheSame() {
    ResponsableDTO newManager = factory.getManagerDTO();
    newManager.setEmail("test@test.com");

    ResponsableDTO existingManager = factory.getManagerDTO();
    existingManager.setEmail("test@test.com");

    Mockito.when(responsableDAO.getManager(newManager)).thenReturn(List.of(existingManager));

    assertThrows(ConflictException.class, () -> {
      responsableUCC.addManager(newManager);
    });
  }

  @Test
  @DisplayName("Test addManager of ResponsableUCCImpl class")
  void testAddManagerDefault() {
    ResponsableDTO responsableDTO = factory.getManagerDTO();
    responsableDTO.setNom("test");
    responsableDTO.setPrenom("test");
    responsableDTO.setEmail("test@gmail.com");
    responsableDTO.setNumTel("048590000");
    responsableDTO.setEntreprise(factory.getEntrepriseDTO());

    Mockito.when(responsableDAO.getManager(responsableDTO)).thenReturn(null);
    Mockito.when(responsableDAO.getManagerByEmail(responsableDTO.getEmail())).thenReturn(null);

    assertDoesNotThrow(() -> {
      responsableUCC.addManager(responsableDTO);
    });
  }

  @Test
  @DisplayName("addManager does not throw ConflictException "
          + "when manager with same email does not exist in database")
  void addManagerDoesNotThrowConflictWhenEmailDoesNotExistInDatabase() {
    ResponsableDTO newManager = factory.getManagerDTO();
    newManager.setEmail("test@test.com");

    Mockito.when(responsableDAO.getManagerByEmail(newManager.getEmail())).thenReturn(null);

    assertDoesNotThrow(() -> {
      responsableUCC.addManager(newManager);
    });
  }

  @Test
  @DisplayName("addManager does not throw ConflictException"
          + " when manager with empty email does not exist in database")
  void addManagerDoesNotThrowConflictWhenEmptyEmailDoesNotExistInDatabase() {
    ResponsableDTO newManager = factory.getManagerDTO();
    newManager.setEmail("");

    Mockito.when(responsableDAO.getManagerByEmail(newManager.getEmail())).thenReturn(null);

    assertDoesNotThrow(() -> {
      responsableUCC.addManager(newManager);
    });
  }

  @Test
  @DisplayName("addManager throws FatalException when an error occurs during the operation")
  void addManagerThrowsFatalExceptionWhenErrorOccurs() {
    ResponsableDTO newManager = factory.getManagerDTO();
    newManager.setEmail("test.test@gmail.com");

    Mockito.when(responsableDAO.getManager(newManager)).thenReturn(null);
    Mockito.when(responsableDAO.getManagerByEmail(newManager.getEmail())).thenReturn(null);
    Mockito.doThrow(FatalException.class).when(responsableDAO).addManager(newManager);

    assertThrows(FatalException.class, () -> {
      responsableUCC.addManager(newManager);
    });
  }

  @Test
  @DisplayName("addManager when it exists but the manager to "
          + "add has a different email so you can add it")
  void addManagerWhenItInsertsItNormally() {
    ResponsableDTO newManager = factory.getManagerDTO();
    newManager.setEmail("test@gmail.com");

    ResponsableDTO existingManager = factory.getManagerDTO();
    existingManager.setEmail("test123@gmail.com");

    Mockito.when(responsableDAO.getManager(newManager)).thenReturn(List.of(existingManager));
    Mockito.when(responsableDAO.getManagerByEmail(newManager.getEmail())).thenReturn(null);

    assertDoesNotThrow(() -> {
      responsableUCC.addManager(newManager);
    });
  }


}