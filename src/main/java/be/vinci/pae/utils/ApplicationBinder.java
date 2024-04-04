package be.vinci.pae.utils;

import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.business.contact.ContactUCCImpl;
import be.vinci.pae.business.entreprise.EntrepriseUCC;
import be.vinci.pae.business.entreprise.EntrepriseUCCImpl;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.factory.FactoryImpl;
import be.vinci.pae.business.responsable.ResponsableUCC;
import be.vinci.pae.business.responsable.ResponsableUCCImpl;
import be.vinci.pae.business.stage.StageUCC;
import be.vinci.pae.business.stage.StageUCCImpl;
import be.vinci.pae.business.user.UserUCC;
import be.vinci.pae.business.user.UserUCCImpl;
import be.vinci.pae.dal.DALBackService;
import be.vinci.pae.dal.DALServiceImpl;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.contact.ContactDAO;
import be.vinci.pae.dal.contact.ContactDAOImpl;
import be.vinci.pae.dal.entreprise.EntrepriseDAO;
import be.vinci.pae.dal.entreprise.EntrepriseDAOImpl;
import be.vinci.pae.dal.manager.ManagerDAO;
import be.vinci.pae.dal.manager.ManagerDAOImpl;
import be.vinci.pae.dal.stage.StageDAO;
import be.vinci.pae.dal.stage.StageDAOImpl;
import be.vinci.pae.dal.user.UserDAO;
import be.vinci.pae.dal.user.UserDAOImpl;
import be.vinci.pae.dal.utils.DALBackServiceUtils;
import be.vinci.pae.dal.utils.DALBackServiceUtilsImpl;
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
    bind(DALServiceImpl.class).to(DALBackService.class).to(DALServices.class).in(Singleton.class);
    bind(DALBackServiceUtilsImpl.class).to(DALBackServiceUtils.class).in(Singleton.class);
    bind(StageUCCImpl.class).to(StageUCC.class).in(Singleton.class);
    bind(StageDAOImpl.class).to(StageDAO.class).in(Singleton.class);
    bind(ContactUCCImpl.class).to(ContactUCC.class).in(Singleton.class);
    bind(ContactDAOImpl.class).to(ContactDAO.class).to(Singleton.class);
    bind(EntrepriseUCCImpl.class).to(EntrepriseUCC.class).in(Singleton.class);
    bind(EntrepriseDAOImpl.class).to(EntrepriseDAO.class).in(Singleton.class);
    bind(ResponsableUCCImpl.class).to(ResponsableUCC.class).in(Singleton.class);
    bind(ManagerDAOImpl.class).to(ManagerDAO.class).in(Singleton.class);
  }
}
