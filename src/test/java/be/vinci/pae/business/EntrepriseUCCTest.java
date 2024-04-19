package be.vinci.pae.business;

import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.entreprise.EntrepriseUCC;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.dal.entreprise.EntrepriseDAO;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
    Mockito.reset(entrepriseDAO);
  }

}
