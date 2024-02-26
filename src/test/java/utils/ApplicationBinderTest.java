package utils;

import be.vinci.pae.business.Factory;
import be.vinci.pae.business.FactoryImpl;
import be.vinci.pae.business.UserUCC;
import be.vinci.pae.business.UserUCCImpl;
import be.vinci.pae.dal.DALService;
import be.vinci.pae.dal.DALServiceImpl;
import be.vinci.pae.dal.UserDAO;
import be.vinci.pae.dal.UserDAOImpl;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.mockito.Mockito;

/**
 * The {@code ApplicationBinder} class provides a custom HK2 binder to configure the application's
 * dependency injection. It binds the application's business and data access classes to their
 * respective interfaces, and sets the scope of the classes to singleton.
 */
@Provider
public class ApplicationBinderTest extends AbstractBinder {

  /**
   * Configures the application's dependency injection.
   */
  @Override
  protected void configure() {
    bind(UserUCCImpl.class).to(UserUCC.class);
    bind(Mockito.mock(UserDAOImpl.class)).to(UserDAO.class);
    bind(FactoryImpl.class).to(Factory.class);
    bind(DALServiceImpl.class).to(DALService.class);
  }
}