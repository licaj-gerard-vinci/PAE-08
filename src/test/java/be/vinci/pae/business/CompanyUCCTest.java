package be.vinci.pae.business;

import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.company.CompanyUCC;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.dal.company.CompanyDAO;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import utils.ApplicationBinderTest;

/**
 * The {@code EntrepriseUCCTest} class tests the {@code EntrepriseUCC} class.
 */
public class CompanyUCCTest {

  private static CompanyUCC companyUCC;

  private static Factory factory;

  private static CompanyDAO companyDAO;

  private CompanyDTO companyDTO;

  @BeforeAll
  static void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    companyUCC = locator.getService(CompanyUCC.class);
    factory = locator.getService(Factory.class);
    companyDAO = locator.getService(CompanyDAO.class);
  }

  @BeforeEach
  void setUpBeforeEach() {
    companyDTO = factory.getCompanyDTO();
    Mockito.reset(companyDAO);
  }

}
