package utils;

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
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.contact.ContactDAO;
import be.vinci.pae.dal.contact.ContactDAOImpl;
import be.vinci.pae.dal.entreprise.EntrepriseDAO;
import be.vinci.pae.dal.manager.ManagerDAO;
import be.vinci.pae.dal.manager.ManagerDAOImpl;
import be.vinci.pae.dal.stage.StageDAO;
import be.vinci.pae.dal.stage.StageDAOImpl;
import be.vinci.pae.dal.user.UserDAO;
import be.vinci.pae.dal.user.UserDAOImpl;
import be.vinci.pae.dal.utils.DALBackServiceUtils;
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
    bind(Mockito.mock(UserDAOImpl.class)).to(UserDAO.class);

    bind(FactoryImpl.class).to(Factory.class).in(Singleton.class);

    bind(ContactUCCImpl.class).to(ContactUCC.class).in(Singleton.class);
    bind(ContactDAOImpl.class).to(ContactDAO.class).in(Singleton.class);

    bind(Mockito.mock(DALServices.class)).to(DALServices.class);

    bind(Mockito.mock(DALBackService.class)).to(DALBackService.class);
    bind(Mockito.mock(DALBackServiceUtils.class)).to(DALBackServiceUtils.class);

    bind(EntrepriseUCCImpl.class).to(EntrepriseUCC.class).in(Singleton.class);
    bind(Mockito.mock(EntrepriseDAO.class)).to(EntrepriseDAO.class);

    bind(StageUCCImpl.class).to(StageUCC.class).in(Singleton.class);
    bind(Mockito.mock(StageDAOImpl.class)).to(StageDAO.class);

    bind(ResponsableUCCImpl.class).to(ResponsableUCC.class).in(Singleton.class);
    bind(Mockito.mock(ManagerDAOImpl.class)).to(ManagerDAO.class);
  }
}
  