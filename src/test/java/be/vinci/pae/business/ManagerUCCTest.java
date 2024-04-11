package be.vinci.pae.business;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.responsable.ResponsableDTO;
import be.vinci.pae.business.responsable.ResponsableUCC;
import be.vinci.pae.dal.manager.ManagerDAO;
import be.vinci.pae.exceptions.ConflictException;
import be.vinci.pae.exceptions.NotFoundException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import utils.ApplicationBinderTest;

import java.util.Arrays;
import java.util.List;

public class ManagerUCCTest {

  private ResponsableUCC responsableUCC;
  private Factory factory;
  private ManagerDAO responsableDAO;

  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    responsableUCC = locator.getService(ResponsableUCC.class);
    factory = locator.getService(Factory.class);
    responsableDAO = locator.getService(ManagerDAO.class);
  }

  @Test
  @DisplayName("Test getManagers of ResponsableUCCImpl class")
  void testGetManagersDefault() {
    int companyId = 1;
    assertNotNull(responsableUCC.getManagers(companyId));
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

    responsableUCC.addManager(responsableDTO);
  }

  @Test
  @DisplayName("Test addManager of ResponsableUCCImpl class")
  void testAddManagerException() {
    ResponsableDTO responsableDTO = factory.getManagerDTO();

    responsableDTO.setNom("test");
    responsableDTO.setPrenom("test");
    responsableDTO.setEmail("");
    responsableDTO.setNumTel("048590000");
    responsableDTO.setEntreprise(factory.getEntrepriseDTO());

    Mockito.when(responsableDAO.getManager(responsableDTO)).thenReturn(Arrays.asList(responsableDTO));

    assertThrows(ConflictException.class, () -> {
      responsableUCC.addManager(responsableDTO);
    });
  }
}