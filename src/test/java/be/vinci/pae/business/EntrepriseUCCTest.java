package be.vinci.pae.business;

import static org.junit.jupiter.api.Assertions.assertThrows;

import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.entreprise.EntrepriseUCC;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.dal.entreprise.EntrepriseDAO;
import be.vinci.pae.presentation.exceptions.FatalException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import utils.ApplicationBinderTest;

/**
 * The {@code EntrepriseUCCTest} class tests the {@code EntrepriseUCC} class.
 */
public class EntrepriseUCCTest {

  private static EntrepriseUCC entrepriseUCC;

  private static Factory factory;

  private static EntrepriseDAO entrepriseDAO;

  private EntrepriseDTO entrepriseDTO;

  @BeforeAll
  static void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    entrepriseUCC = locator.getService(EntrepriseUCC.class);
    factory = locator.getService(Factory.class);
    entrepriseDAO = locator.getService(EntrepriseDAO.class);
  }

  @BeforeEach
  void setUpBeforeEach() {
    entrepriseDTO = factory.getEntrepriseDTO();
  }

  /**
   * @Test
   * @DisplayName("Test getEntreprise of EntrepriseUCCImpl class") void testGetEntrepriseDefault() {
   * int id = 1; System.out.println(entrepriseUCC.getEntreprise(id));
   * Mockito.when(entrepriseDAO.getEntreprise(id)).thenReturn(entrepriseDTO);
   * assertNotNull(entrepriseUCC.getEntreprise(id)); }
   * @Test
   * @DisplayName("Test getEntreprise of EntrepriseUCCImpl class with wrong id") void
   * testGetEntrepriseWrongId() { int id = 1;
   * Mockito.when(entrepriseDAO.getEntreprise(id)).thenReturn(null);
   * assertThrows(NotFoundException.class, () -> { entrepriseUCC.getEntreprise(id); }); }
   */

  @Test
  @DisplayName("Test getEntreprise of EntrepriseUCCImpl class with FatalException")
  void testGetEntrepriseFatalException() {
    int id = 1;
    Mockito.when(entrepriseDAO.getEntreprise(id)).thenThrow(new FatalException());
    assertThrows(FatalException.class, () -> {
      entrepriseUCC.getEntreprise(id);
    });
  }

  /**
   * @Test
   * @DisplayName("Test getEntreprises of EntrepriseUCCImpl class") void testGetEntreprisesDefault()
   * { EntrepriseDTO entreprise1 = factory.getEntrepriseDTO(); EntrepriseDTO entreprise2 =
   * factory.getEntrepriseDTO(); Mockito.when(entrepriseDAO.getEntreprises())
   * .thenReturn(Arrays.asList(entreprise1, entreprise2)); List<EntrepriseDTO> entreprises =
   * entrepriseUCC.getEntreprises(); assertEquals(2, entreprises.size()); }
   * @Test
   * @DisplayName("Test getEntreprises of EntrepriseUCCImpl class with no entreprise") void
   * testGetEntreprisesNoEntreprise() {
   * Mockito.when(entrepriseDAO.getEntreprises()).thenReturn(null);
   * assertThrows(NotFoundException.class, () -> { entrepriseUCC.getEntreprises(); }); }
   */

  @Test
  @DisplayName("Test getEntreprises of EntrepriseUCCImpl class with FatalException")
  void testGetEntreprisesFatalException() {
    Mockito.when(entrepriseDAO.getEntreprises()).thenThrow(new FatalException());
    assertThrows(FatalException.class, () -> {
      entrepriseUCC.getEntreprises();
    });
  }
}
