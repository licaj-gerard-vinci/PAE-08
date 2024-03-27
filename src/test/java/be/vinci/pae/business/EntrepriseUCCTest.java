package be.vinci.pae.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.entreprise.EntrepriseUCC;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.dal.entreprise.EntrepriseDAO;
import be.vinci.pae.presentation.exceptions.FatalException;
import be.vinci.pae.presentation.exceptions.NotFoundException;
import java.util.Arrays;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import utils.ApplicationBinderTest;

public class EntrepriseUCCTest {

  private EntrepriseUCC entrepriseUCC;

  private Factory factory;

  private EntrepriseDAO entrepriseDAO;

  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    entrepriseUCC = locator.getService(EntrepriseUCC.class);
    factory = locator.getService(Factory.class);
    entrepriseDAO = locator.getService(EntrepriseDAO.class);
  }


  @Test
  @DisplayName("Test getEntreprise of EntrepriseUCCImpl class")
  void testGetEntrepriseDefault() {
    int id = 1;
    EntrepriseDTO entreprise = factory.getEntrepriseDTO();
    Mockito.when(entrepriseDAO.getEntreprise(id)).thenReturn(entreprise);
    assertNotNull(entrepriseUCC.getEntreprise(id));
  }

  @Test
  @DisplayName("Test getEntreprise of EntrepriseUCCImpl class with wrong id")
  void testGetEntrepriseWrongId() {
    int id = 1;
    Mockito.when(entrepriseDAO.getEntreprise(id)).thenReturn(null);
    assertThrows(NotFoundException.class, () -> {
      entrepriseUCC.getEntreprise(id);
    });
  }

  @Test
  @DisplayName("Test getEntreprise of EntrepriseUCCImpl class with FatalException")
  void testGetEntrepriseFatalException() {
    int id = 1;
    Mockito.when(entrepriseDAO.getEntreprise(id)).thenThrow(new FatalException());
    assertThrows(FatalException.class, () -> {
      entrepriseUCC.getEntreprise(id);
    });
  }

  @Test
  @DisplayName("Test getEntreprises of EntrepriseUCCImpl class")
  void testGetEntreprisesDefault() {
    EntrepriseDTO entreprise1 = factory.getEntrepriseDTO();
    EntrepriseDTO entreprise2 = factory.getEntrepriseDTO();
    Mockito.when(entrepriseDAO.getEntreprises()).thenReturn(Arrays.asList(entreprise1, entreprise2));
    List<EntrepriseDTO> entreprises = entrepriseUCC.getEntreprises();
    assertEquals(2, entreprises.size());
  }

  @Test
  @DisplayName("Test getEntreprises of EntrepriseUCCImpl class with no entreprise")
  void testGetEntreprisesNoEntreprise() {
    Mockito.when(entrepriseDAO.getEntreprises()).thenReturn(null);
    assertThrows(NotFoundException.class, () -> {
      entrepriseUCC.getEntreprises();
    });
  }

  @Test
  @DisplayName("Test getEntreprises of EntrepriseUCCImpl class with FatalException")
  void testGetEntreprisesFatalException() {
    Mockito.when(entrepriseDAO.getEntreprises()).thenThrow(new FatalException());
    assertThrows(FatalException.class, () -> {
      entrepriseUCC.getEntreprises();
    });
  }
}
