package be.vinci.pae.business;

import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.company.CompanyUCC;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.dal.company.CompanyDAO;
import be.vinci.pae.exceptions.ConflictException;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.exceptions.NotFoundException;
import java.util.Arrays;
import java.util.List;
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

  @Test
  @DisplayName("Test getEntrepriseById of EntrepriseUCCImpl class")
  void testGetEntrepriseByIdDefault() {
    int id = 1;
    when(companyDAO.getCompany(id)).thenReturn(companyDTO);
    assertDoesNotThrow(() -> companyUCC.getCompanyById(id));
  }

  @Test
  @DisplayName("Test getEntrepriseById of EntrepriseUCCImpl class")
  void testGetEntrepriseByIdException() {
    int id = 1;
    Mockito.when(companyDAO.getCompany(id)).thenReturn(null);
    assertThrows(NotFoundException.class, () -> companyUCC.getCompanyById(id));
  }


  @Test
  @DisplayName("Test getAllCompanies of EntrepriseUCCImpl class")
  void testGetAllCompaniesDefault() {
    Mockito.when(companyDAO.getCompany()).thenReturn(null);
    assertThrows(NotFoundException.class, () -> companyUCC.getAllCompanies());
  }

  @Test
  @DisplayName("Test getAllCompanies of EntrepriseUCCImpl class")
  public void testGetAllCompanies() {
    CompanyDTO entreprise1 = factory.getCompanyDTO();
    CompanyDTO entreprise2 = factory.getCompanyDTO();

    when(companyDAO.getCompany()).thenReturn(Arrays.asList(entreprise1, entreprise2));
    List<CompanyDTO> entreprises = companyUCC.getAllCompanies();
    assertEquals(2, entreprises.size());
  }





  @Test
  @DisplayName("Test blackListCompany of EntrepriseUCCImpl class")
  void testBlackListCompanyFatalException() {
    CompanyDTO entreprise = factory.getCompanyDTO();
    entreprise.setBlackListed(true);
    Mockito.when(companyDAO.getCompany(entreprise.getId())).thenReturn(entreprise);
    assertThrows(ConflictException.class, () -> companyUCC.blackListCompany(entreprise));
  }


  @Test
  @DisplayName("Test blackListCompany of EntrepriseUCCImpl class by default")
  void testBlackListCompanyDefault() {
    CompanyDTO entreprise = factory.getCompanyDTO();
    Mockito.when(companyDAO.getCompany(entreprise.getId())).thenReturn(entreprise);
    assertDoesNotThrow(() -> companyUCC.blackListCompany(entreprise));
  }

  @Test
  @DisplayName("Test blackListCompany of EntrepriseUCCImpl ")
  void testBlackListCompanyException() {
    CompanyDTO entreprise = factory.getCompanyDTO();
    entreprise.setId(1);
    entreprise.setName("test");
    entreprise.setDesignation("test");
    entreprise.setBlackListed(false);

    Mockito.when(companyDAO.getCompany(entreprise.getId())).thenReturn(entreprise);
    Mockito.doThrow(FatalException.class).when(companyDAO).updateCompany(entreprise);
    assertThrows(FatalException.class, () -> companyUCC.blackListCompany(entreprise));
  }

  @Test
  @DisplayName("Test addCompany of EntrepriseUCCImpl class Not Found")
  public void testAddCompanyNotFoundException() {
    CompanyDTO entreprise = factory.getCompanyDTO();
    entreprise.setName("test");
    entreprise.setDesignation("test");
    Mockito.when(companyDAO.getCompanyByNameDesignation(entreprise.getName(),
            entreprise.getDesignation())).thenReturn(entreprise);
    assertThrows(ConflictException.class, () -> companyUCC.addCompany(entreprise));
  }


  @Test
  @DisplayName("Test addCompany of EntrepriseUCCImpl class")
  public void testAddCompanyFatalException() {
    CompanyDTO entreprise = factory.getCompanyDTO();
    entreprise.setName("test");
    entreprise.setDesignation("test");
    Mockito.when(companyDAO.getCompanyByNameDesignation(entreprise.getName(),
            entreprise.getDesignation())).thenReturn(null);
    Mockito.doThrow(FatalException.class).when(companyDAO).addCompany(entreprise);
    assertThrows(FatalException.class, () -> companyUCC.addCompany(entreprise));
  }

  @Test
  @DisplayName("Test addCompany of EntrepriseUCCImpl class")
  void testAddCompanyException() {
    CompanyDTO entreprise = factory.getCompanyDTO();
    Mockito.when(companyDAO.getCompany(entreprise.getId())).thenReturn(entreprise);
  }

  @Test
  @DisplayName("Test addCompany of EntrepriseUCCImpl class by default")
  void testAddCompanyDefault() {
    CompanyDTO entreprise = factory.getCompanyDTO();
    entreprise.setName("test");
    entreprise.setDesignation("test");
    Mockito.when(companyDAO.getCompanyByNameDesignation(entreprise.getName(),
            entreprise.getDesignation())).thenReturn(null);
    assertDoesNotThrow(() -> companyUCC.addCompany(entreprise));
  }



}





