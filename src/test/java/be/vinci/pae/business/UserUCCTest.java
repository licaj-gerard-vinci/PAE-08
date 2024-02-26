package be.vinci.pae.business;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import be.vinci.pae.dal.UserDAO;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import utils.ApplicationBinderTest;

class UserUCCTest {

  private UserUCC userUCC;
  private Factory factory;
  private UserDAO userDAO;


  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    userUCC = locator.getService(UserUCC.class);
    factory = locator.getService(Factory.class);
    userDAO = locator.getService(UserDAO.class);
  }

  @Test
  @DisplayName("Test login of UserUCCImpl class")
  void testLogin() {
    String email = "test@vinci.be";
    String password = "test";
    User user = (User) factory.getPublicUser();
    user.setPassword(user.hashPassword(password));
    Mockito.when(userDAO.getOneByEmail(email)).thenReturn(user);
    assertAll("Test login",
        () -> assertNotNull(userUCC.login(email, password)),
        () -> assertNull(userUCC.login(email, "wrongPassword")),
        () -> assertNull(userUCC.login("wrongEmail", password))
    );
  }

  @Test
  void getOne() {
    User user = (User) factory.getPublicUser();
    Mockito.when(userDAO.getOneById(1)).thenReturn(user);
    assertAll("Test getOne",
        () -> assertNull(userUCC.getOne(2)),
        () -> assertEquals(user, userUCC.getOne(1))
    );
  }
}