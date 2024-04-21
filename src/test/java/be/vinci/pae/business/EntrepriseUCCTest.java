package be.vinci.pae.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.entreprise.EntrepriseUCC;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.dal.entreprise.EntrepriseDAO;
import be.vinci.pae.exceptions.ConflictException;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.exceptions.NotFoundException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import utils.ApplicationBinderTest;

import java.util.Arrays;
import java.util.List;

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

    @Test
    @DisplayName("Test getEntrepriseById of EntrepriseUCCImpl class")
    void testGetEntrepriseByIdDefault() {
      int id = 1;
      Mockito.when(entrepriseDAO.getEntreprise(id)).thenReturn(entrepriseDTO);
      assertDoesNotThrow(() -> entrepriseUCC.getCompanyById(id));
    }

    @Test
    @DisplayName("Test getEntrepriseById of EntrepriseUCCImpl class")
    void testGetEntrepriseByIdException() {
      int id = 1;
      Mockito.when(entrepriseDAO.getEntreprise(id)).thenReturn(null);
      assertThrows(NotFoundException.class, () -> entrepriseUCC.getCompanyById(id));
    }


    @Test
    @DisplayName("Test getAllCompanies of EntrepriseUCCImpl class")
    void testGetAllCompaniesDefault() {
      Mockito.when(entrepriseDAO.getEntreprises()).thenReturn(null);
      assertThrows(NotFoundException.class, () -> entrepriseUCC.getAllCompanies());
    }

  @Test
  @DisplayName("Test getAllCompanies of EntrepriseUCCImpl class")
  public void testGetAllCompanies() {
    EntrepriseDTO entreprise1 = factory.getEntrepriseDTO();
    EntrepriseDTO entreprise2 = factory.getEntrepriseDTO();

    when(entrepriseDAO.getEntreprises()).thenReturn(Arrays.asList(entreprise1, entreprise2));
    List<EntrepriseDTO> entreprises = entrepriseUCC.getAllCompanies();
    assertEquals(2, entreprises.size());
  }





    @Test
    @DisplayName("Test blackListCompany of EntrepriseUCCImpl class")
    void testBlackListCompanyFatalException() {
      EntrepriseDTO entreprise = factory.getEntrepriseDTO();
      entreprise.setBlackListed(true);
      Mockito.when(entrepriseDAO.getEntreprise(entreprise.getId())).thenReturn(entreprise);
      assertThrows(ConflictException.class, () -> entrepriseUCC.blackListCompany(entreprise));
    }


    @Test
    @DisplayName("Test blackListCompany of EntrepriseUCCImpl class by default")
    void testBlackListCompanyDefault() {
      EntrepriseDTO entreprise = factory.getEntrepriseDTO();
      Mockito.when(entrepriseDAO.getEntreprise(entreprise.getId())).thenReturn(entreprise);
      assertDoesNotThrow(() -> entrepriseUCC.blackListCompany(entreprise));
    }

    @Test
    @DisplayName("Test blackListCompany of EntrepriseUCCImpl ")
    void testBlackListCompanyException() {
      EntrepriseDTO entreprise = factory.getEntrepriseDTO();
      entreprise.setId(1);
      entreprise.setNom("test");
      entreprise.setAppellation("test");
      entreprise.setBlackListed(false);

      Mockito.when(entrepriseDAO.getEntreprise(entreprise.getId())).thenReturn(entreprise);
      Mockito.doThrow(FatalException.class).when(entrepriseDAO).updateEntreprise(entreprise);
      assertThrows(FatalException.class, () -> entrepriseUCC.blackListCompany(entreprise));
    }

    @Test
     @DisplayName("Test addCompany of EntrepriseUCCImpl class Not Found")
     public void testAddCompanyNotFoundException() {
         EntrepriseDTO entreprise = factory.getEntrepriseDTO();
         entreprise.setNom("test");
         entreprise.setAppellation("test");
         Mockito.when(entrepriseDAO.getEntrepriseByNameDesignation(entreprise.getNom(),
                entreprise.getAppellation())).thenReturn(entreprise);
         assertThrows(ConflictException.class, () -> entrepriseUCC.addEntreprise(entreprise));
 }


  @Test
  @DisplayName("Test addCompany of EntrepriseUCCImpl class")
  public void testAddCompanyFatalException() {
    EntrepriseDTO entreprise = factory.getEntrepriseDTO();
    entreprise.setNom("test");
    entreprise.setAppellation("test");
    Mockito.when(entrepriseDAO.getEntrepriseByNameDesignation(entreprise.getNom(),
            entreprise.getAppellation())).thenReturn(null);
    Mockito.doThrow(FatalException.class).when(entrepriseDAO).addEntreprise(entreprise);
    assertThrows(FatalException.class, () -> entrepriseUCC.addEntreprise(entreprise));
  }

  @Test
  @DisplayName("Test addCompany of EntrepriseUCCImpl class")
  void testAddCompanyException() {
    EntrepriseDTO entreprise = factory.getEntrepriseDTO();
    Mockito.when(entrepriseDAO.getEntreprise(entreprise.getId())).thenReturn(entreprise);
  }

    @Test
    @DisplayName("Test addCompany of EntrepriseUCCImpl class by default")
    void testAddCompanyDefault() {
      EntrepriseDTO entreprise = factory.getEntrepriseDTO();
      entreprise.setNom("test");
      entreprise.setAppellation("test");
      Mockito.when(entrepriseDAO.getEntrepriseByNameDesignation(entreprise.getNom(),
              entreprise.getAppellation())).thenReturn(null);
      assertDoesNotThrow(() -> entrepriseUCC.addEntreprise(entreprise));
    }



}





