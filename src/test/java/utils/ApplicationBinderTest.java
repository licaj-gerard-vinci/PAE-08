package utils;

import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.factory.FactoryImpl;
import be.vinci.pae.business.user.UserUCC;
import be.vinci.pae.business.user.UserUCCImpl;
import be.vinci.pae.dal.DALServiceImpl;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.user.UserDAO;
import be.vinci.pae.dal.user.UserDAOImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.mockito.Mockito;

public class ApplicationBinderTest extends AbstractBinder {
    /**
     * Configures the binding.
     */
    @Override
    protected void configure() {
        bind(UserUCCImpl.class).to(UserUCC.class);
        bind(FactoryImpl.class).to(Factory.class);
        bind(Mockito.mock(UserDAOImpl.class)).to(UserDAO.class);
        bind(Mockito.mock(DALServiceImpl.class)).to(DALServices.class);
    }
}
