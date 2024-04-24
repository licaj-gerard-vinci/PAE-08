package utils;

import be.vinci.pae.business.company.CompanyUCC;
import be.vinci.pae.business.company.CompanyUCCImpl;
import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.business.contact.ContactUCCImpl;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.factory.FactoryImpl;
import be.vinci.pae.business.internship.InternshipUCC;
import be.vinci.pae.business.internship.InternshipUCCImpl;
import be.vinci.pae.business.manager.ManagerUCC;
import be.vinci.pae.business.manager.ManagerUCCImpl;
import be.vinci.pae.business.user.UserUCC;
import be.vinci.pae.business.user.UserUCCImpl;
import be.vinci.pae.business.year.Year;
import be.vinci.pae.business.year.YearUCC;
import be.vinci.pae.business.year.YearUCCImpl;
import be.vinci.pae.dal.DALBackService;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.company.CompanyDAO;
import be.vinci.pae.dal.contact.ContactDAO;
import be.vinci.pae.dal.internship.InternshipDAO;
import be.vinci.pae.dal.manager.ManagerDAO;
import be.vinci.pae.dal.user.UserDAO;
import be.vinci.pae.dal.user.UserDAOImpl;
import be.vinci.pae.dal.utils.DALBackServiceUtils;
import be.vinci.pae.dal.year.YearDAO;
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
    bind(Mockito.mock(ContactDAO.class)).to(ContactDAO.class);

    bind(Mockito.mock(DALServices.class)).to(DALServices.class);

    bind(Mockito.mock(DALBackService.class)).to(DALBackService.class);
    bind(Mockito.mock(DALBackServiceUtils.class)).to(DALBackServiceUtils.class);

    bind(CompanyUCCImpl.class).to(CompanyUCC.class).in(Singleton.class);
    bind(Mockito.mock(CompanyDAO.class)).to(CompanyDAO.class);

    bind(InternshipUCCImpl.class).to(InternshipUCC.class).in(Singleton.class);
    bind(Mockito.mock(InternshipDAO.class)).to(InternshipDAO.class);

    bind(ManagerUCCImpl.class).to(ManagerUCC.class).in(Singleton.class);
    bind(Mockito.mock(ManagerDAO.class)).to(ManagerDAO.class);

    bind(YearUCCImpl.class).to(YearUCC.class).in(Singleton.class);
    bind(Mockito.mock(YearDAO.class)).to(YearDAO.class);
    bind(Mockito.mock(Year.class)).to(Year.class);
  }
}
  