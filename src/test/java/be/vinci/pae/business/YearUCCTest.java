package be.vinci.pae.business;

import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.year.YearDTO;
import be.vinci.pae.business.year.YearUCC;
import be.vinci.pae.dal.year.YearDAO;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import utils.ApplicationBinderTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class YearUCCTest {

  private Factory factory;
  private YearDAO yearDAO;
  private YearUCC yearUCC;

  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    factory = locator.getService(Factory.class);
    yearDAO = locator.getService(YearDAO.class);
    yearUCC = locator.getService(YearUCC.class);
    Mockito.reset(yearDAO);
  }

  @Test
  @DisplayName("Test get year by id")
  void testGetYearById() {
    YearDTO year = factory.getYearDTO();
    Mockito.when(yearDAO.getOneById(1)).thenReturn(year);
    assertEquals(year, yearUCC.getYearById(1));
  }

  @Test
  @DisplayName("Test get year by year")
  void testGetYearByYear() {
    YearDTO year = factory.getYearDTO();
    Mockito.when(yearDAO.getOneByYear("2021")).thenReturn(year);
    assertEquals(year, yearUCC.getYearByYear("2021"));
  }
}
