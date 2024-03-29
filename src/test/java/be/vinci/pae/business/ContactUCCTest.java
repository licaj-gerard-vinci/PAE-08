package be.vinci.pae.business;

import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.dal.contact.ContactDAO;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import utils.ApplicationBinderTest;

/**
 * The {@code ContactUCCTest} class tests the {@code ContactUCC} class.
 */
public class ContactUCCTest {

  private static ContactUCC contactUCC;

  private static Factory factory;

  private static ContactDAO contactDAO;

  @BeforeEach
  void setUpBeforeEach() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    contactUCC = locator.getService(ContactUCC.class);
    factory = locator.getService(Factory.class);
    contactDAO = locator.getService(ContactDAO.class);
  }

}
