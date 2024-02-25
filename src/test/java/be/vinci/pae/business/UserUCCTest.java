package be.vinci.pae.business;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.ApplicationBinderTest;

class UserUCCTest {

  private UserUCC userUCC;
  private Factory factory;


  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    userUCC = locator.getService(UserUCC.class);
    factory = locator.getService(Factory.class);
  }

  @Test
  @DisplayName("Test login of UserUCCImpl class")
  void testLogin() {
    String email = "test@student.vinci.be";
    String password = "test";
    User user = (User) factory.getPublicUser();
    user.setPassword(user.hashPassword(password));
  }

  @Test
  void getOne() {
  }
}