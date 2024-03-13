package be.vinci.pae.utils;

import be.vinci.pae.business.ContactUCC;
import be.vinci.pae.business.ContactUCCImpl;
import be.vinci.pae.business.EntrepriseUCC;
import be.vinci.pae.business.EntrepriseUCCImpl;
import be.vinci.pae.business.Factory;
import be.vinci.pae.business.FactoryImpl;
import be.vinci.pae.business.StageUCC;
import be.vinci.pae.business.StageUCCImpl;
import be.vinci.pae.business.UserUCC;
import be.vinci.pae.business.UserUCCImpl;
import be.vinci.pae.dal.ContactDAO;
import be.vinci.pae.dal.ContactDAOImpl;
import be.vinci.pae.dal.DALService;
import be.vinci.pae.dal.DALServiceImpl;
import be.vinci.pae.dal.EntrepriseDAO;
import be.vinci.pae.dal.EntrepriseDAOImpl;
import be.vinci.pae.dal.StageDAO;
import be.vinci.pae.dal.StageDAOImpl;
import be.vinci.pae.dal.UserDAO;
import be.vinci.pae.dal.UserDAOImpl;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * The {@code ApplicationBinder} class provides a custom HK2 binder to configure the application's
 * dependency injection. It binds the application's business and data access classes to their
 * respective interfaces, and sets the scope of the classes to singleton.
 */
@Provider
public class ApplicationBinder extends AbstractBinder {

  /**
   * Configures the application's dependency injection.
   */
  @Override
  protected void configure() {
    bind(UserUCCImpl.class).to(UserUCC.class).in(Singleton.class);
    bind(UserDAOImpl.class).to(UserDAO.class).in(Singleton.class);
    bind(FactoryImpl.class).to(Factory.class).in(Singleton.class);
    bind(DALServiceImpl.class).to(DALService.class).in(Singleton.class);
    bind(StageUCCImpl.class).to(StageUCC.class).in(Singleton.class);
    bind(StageDAOImpl.class).to(StageDAO.class).in(Singleton.class);
    bind(ContactUCCImpl.class).to(ContactUCC.class).in(Singleton.class);
    bind(ContactDAOImpl.class).to(ContactDAO.class).to(Singleton.class);
    bind(EntrepriseUCCImpl.class).to(EntrepriseUCC.class).in(Singleton.class);
    bind(EntrepriseDAOImpl.class).to(EntrepriseDAO.class).in(Singleton.class);
  }
}