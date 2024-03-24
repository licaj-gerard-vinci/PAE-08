package utils;

import be.vinci.pae.business.contact.ContactUCCImpl;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.factory.FactoryImpl;
import be.vinci.pae.business.user.UserUCC;
import be.vinci.pae.business.user.UserUCCImpl;
import be.vinci.pae.dal.DALServiceImpl;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.contact.ContactDAO;
import be.vinci.pae.dal.contact.ContactDAOImpl;
import be.vinci.pae.dal.user.UserDAO;
import be.vinci.pae.dal.user.UserDAOImpl;
import jakarta.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.mockito.Mockito;

/**
 * Binds the classes for testing.
 */
public class ApplicationBinderTest extends AbstractBinder {

  /**
   * Configures the binding.
   */
  @Override
  protected void configure() {
    bind(UserUCCImpl.class).to(UserUCC.class).in(Singleton.class);
    UserDAO userDAO = Mockito.mock(UserDAOImpl.class);
    bind(userDAO).to(UserDAO.class);
    bind(FactoryImpl.class).to(Factory.class).in(Singleton.class);
    bind(ContactUCCImpl.class).to(ContactUCCImpl.class).in(Singleton.class);
    bind(ContactDAOImpl.class).to(ContactDAO.class).in(Singleton.class);
    bind(Mockito.mock(UserDAOImpl.class)).to(UserDAO.class);
    bind(Mockito.mock(DALServiceImpl.class)).to(DALServices.class);

  }
}
  