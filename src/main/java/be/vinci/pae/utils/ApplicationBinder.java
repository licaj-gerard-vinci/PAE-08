package be.vinci.pae.utils;

import be.vinci.pae.dal.UserDAOImpl;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

@Provider
public class ApplicationBinder extends AbstractBinder {

  @Override
  protected void configure() {
    bind(UserDAOImpl.class).to(UserDAOImpl.class).in(Singleton.class);
  }
}