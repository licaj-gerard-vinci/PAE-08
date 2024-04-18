package be.vinci.pae.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.user.User;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserUCC;
import be.vinci.pae.business.year.YearDTO;
import be.vinci.pae.dal.user.UserDAO;
import be.vinci.pae.dal.year.YearDAO;
import be.vinci.pae.exceptions.BusinessException;
import be.vinci.pae.exceptions.ConflictException;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.exceptions.NotFoundException;
import java.util.Arrays;
import java.util.List;
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
  private YearDAO yearDAO;

  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    userUCC = locator.getService(UserUCC.class);
    factory = locator.getService(Factory.class);
    userDAO = locator.getService(UserDAO.class);
    yearDAO = locator.getService(YearDAO.class);
  }

  @Test
  @DisplayName("Test login of UserUCCImpl class")
  void testLoginDefault() {
    String email = "test@vinci.be";
    String password = "test";
    User user = (User) factory.getPublicUser();
    user.setPassword(user.hashPassword(password));
    Mockito.when(userDAO.getOneByEmail(email)).thenReturn(user);
    assertNotNull(userUCC.login(email, password));
  }

  @Test
  @DisplayName("Test login of UserUCCImpl class with wrong email")
  void testLoginWrongEmail() {
    String email = "wrongEmail";
    String password = "test";
    User user = (User) factory.getPublicUser();
    user.setPassword(user.hashPassword(password));
    Mockito.when(userDAO.getOneByEmail(email)).thenReturn(null);
    assertThrows(NotFoundException.class, () -> {
      userUCC.login(email, password);
    });
  }

  @Test
  @DisplayName("Test login of UserUCCImpl class with wrong password")
  void testLoginWrongPassword() {
    String email = "test@vinci.be";
    String password = "wrongPassword";
    User user = (User) factory.getPublicUser();
    user.setPassword(user.hashPassword("test"));
    Mockito.when(userDAO.getOneByEmail(email)).thenReturn(user);
    assertThrows(BusinessException.class, () -> {
      userUCC.login(email, password);
    });
  }

  @Test
  @DisplayName("Test login of UserUCCImpl class with SQLException")
  void testLoginSQLException() {
    String email = "prenom.nom";
    String password = "password";
    Mockito.when(userDAO.getOneByEmail(email))
        .thenThrow(new FatalException("SQL Connection Error"));
    assertThrows(FatalException.class, () -> {
      userUCC.login(email, password);
    });
  }

  @Test
  @DisplayName("Test UserUCC registration")
  void testUserUCCRegistration() {
    YearDTO year = factory.getYearDTO();
    year.setId(1);
    year.setAnnee("2023-2024");
    Mockito.when(yearDAO.getOneByYear("2023-2024")).thenReturn(year);
    User user = (User) factory.getPublicUser();
    user.setEmail("prenom.nom@vinci.be");
    user.setPassword("password");
    user.setRole("A");
    user.setFirstname("prenom");
    user.setLastname("nom");
    user.setPhone("phone");
    Mockito.when(userDAO.getOneByEmail("prenom.nom@vinci.be")).thenReturn(null);
    Mockito.when(userDAO.insertUser(user)).thenReturn(user);
    User registeredUser = (User) userUCC.register(user);
    assertEquals("prenom.nom@vinci.be", registeredUser.getEmail());
    assertEquals("A", registeredUser.getRole());
  }

  @Test
  @DisplayName("Test UserUCC registration with an existing email")
  void testUserUCCRegistrationWithExistingEmail() {
    YearDTO year = factory.getYearDTO();
    year.setId(1);
    year.setAnnee("2023-2024");
    Mockito.when(yearDAO.getOneByYear("2023-2024")).thenReturn(year);
    User user = (User) factory.getPublicUser();
    user.setEmail("prenom.nom@vinci.be");
    user.setPassword("password");
    user.setRole("A");
    user.setFirstname("prenom");
    user.setLastname("nom");
    user.setPhone("phone");
    user.setidSchoolYear(1);
    Mockito.when(userDAO.getOneByEmail("prenom.nom@vinci.be")).thenReturn(user);
    assertThrows(ConflictException.class, () -> {
      userUCC.register(user);
    });
  }

  @Test
  @DisplayName("Test UserUCC registration with student email")
  void testUserUCCRegistrationWithStudentEmail() {
    User user = (User) factory.getPublicUser();
    user.setEmail("prenom.nom@student.vinci.be");
    user.setFirstname("prenom");
    user.setLastname("nom");
    user.setPhone("phone");
    user.setPassword("password");
    Mockito.when(userDAO.getOneByEmail("prenom.nom@student.vinci.be")).thenReturn(null);
    Mockito.when(userDAO.insertUser(user)).thenReturn(user);
    User registeredUser = (User) userUCC.register(user);
    assertEquals("prenom.nom@student.vinci.be", registeredUser.getEmail());
    assertEquals("E", registeredUser.getRole());
  }

  @Test
  @DisplayName("Test UserUCC registration with invalid email format")
  void testUserUCCRegistrationWithInvalidEmailFormat() {
    User user = (User) factory.getPublicUser();
    user.setEmail("invalidEmail");
    user.setPassword("password");
    user.setFirstname("prenom");
    user.setLastname("nom");
    user.setPhone("phone");
    user.setRole("E");
    assertThrows(BusinessException.class, () -> {
      userUCC.register(user);
    });
  }

  @Test
  @DisplayName("Test UserUCC registration with invalid role")
  void testUserUCCRegistrationWithInvalidRole() {
    User user = (User) factory.getPublicUser();
    user.setEmail("prenom.nom@vinci.be");
    user.setPassword("password");
    user.setFirstname("prenom");
    user.setLastname("nom");
    user.setPhone("phone");
    user.setRole("InvalidRole");
    assertThrows(BusinessException.class, () -> {
      userUCC.register(user);
    });
  }

  @Test
  @DisplayName("Test UserUCC registration with SqlConnectionException")
  void testUserUCCRegistrationWithSqlConnectionException() {
    User user = (User) factory.getPublicUser();
    user.setEmail("prenom.nom@student.vinci.be");
    user.setRole("E");
    Mockito.when(userDAO.getOneByEmail(user.getEmail())).thenReturn(null);
    Mockito.when(userDAO.insertUser(user)).thenThrow(new FatalException("SQL Connection Error"));
    assertThrows(FatalException.class, () -> {
      userUCC.register(user);
    });
  }


  @Test
  @DisplayName("Test getOne of UserUCCImpl class")
  void testGetOne() {
    User user = (User) factory.getPublicUser();
    Mockito.when(userDAO.getOneById(1)).thenReturn(user);
    assertEquals(user, userUCC.getOne(1));
  }

  @Test
  @DisplayName("Test getOne of UserUCCImpl class with wrong id")
  void testGetOneWrongId() {
    User user = (User) factory.getPublicUser();
    Mockito.when(userDAO.getOneById(1)).thenReturn(user);
    assertThrows(NotFoundException.class, () -> {
      userUCC.getOne(2);
    });
  }

  @Test
  @DisplayName("Test getOne of UserUCCImpl class with SQLException")
  void testGetOneSQLException() {
    User user = (User) factory.getPublicUser();
    Mockito.when(userDAO.getOneById(user.getId()))
            .thenThrow(new FatalException("SQL Connection Error"));
    assertThrows(FatalException.class, () -> {
      userUCC.getOne(user.getId());
    });
  }

  @Test
  @DisplayName("Test getAll of UserUCCImpl class - Not Null Check")
  void testGetAllNotNull() {
    User user1 = (User) factory.getPublicUser();
    User user2 = (User) factory.getPublicUser();

    Mockito.when(userDAO.getAllUsers()).thenReturn(Arrays.asList(user1, user2));
    List<UserDTO> result = userUCC.getAll();
    assertNotNull(result);
  }

  @Test
  @DisplayName("Test getAll of UserUCCImpl class - Size Check")
  void testGetAllSize() {
    User user1 = (User) factory.getPublicUser();
    User user2 = (User) factory.getPublicUser();

    Mockito.when(userDAO.getAllUsers()).thenReturn(Arrays.asList(user1, user2));
    List<UserDTO> result = userUCC.getAll();
    assertEquals(2, result.size());
  }

  @Test
  @DisplayName("Test method getAllUsers of UserDAOImpl class if null")
  void testGetAllUsersNull() {
    Mockito.when(userDAO.getAllUsers()).thenReturn(null);
    assertThrows(NotFoundException.class, () -> {
      userUCC.getAll();
    });
  }

  @Test
  @DisplayName("Test method getAllUsers of UserDAOImpl class if SQLException")
  void testGetAllUsersSQLException() {
    Mockito.when(userDAO.getAllUsers()).thenThrow(new FatalException("SQL Connection Error"));
    assertThrows(FatalException.class, () -> {
      userUCC.getAll();
    });
  }


  @Test
  @DisplayName("Test update of UserUCCImpl class with an password")
  void testUpdate() {
    User user = (User) factory.getPublicUser();
    user.setId(1);
    user.setPassword("password");
    Mockito.when(userDAO.getOneById(user.getId())).thenReturn(user);
    Mockito.when(userDAO.updateUser(user)).thenReturn(true);
    assertEquals(true, userUCC.update(user.getId(), user));
  }


  @Test
  @DisplayName("Test update of UserUCCImpl class with a email")
  void testUpdateEmail() {
    User user = (User) factory.getPublicUser();
    user.setId(1);
    user.setEmail("nom.prenom@vinci.be");
    Mockito.when(userDAO.getOneById(user.getId())).thenReturn(user);
    Mockito.when(userDAO.updateUser(user)).thenReturn(true);
    assertEquals(true, userUCC.update(user.getId(), user));

  }

  @Test
  @DisplayName("Test update of UserUCCImpl class with a Lastname")
  void testUpdateLastname() {
    User user = (User) factory.getPublicUser();
    user.setId(1);
    user.setLastname("nom");
    Mockito.when(userDAO.getOneById(user.getId())).thenReturn(user);
    Mockito.when(userDAO.updateUser(user)).thenReturn(true);
    assertEquals(true, userUCC.update(user.getId(), user));

  }


  @Test
  @DisplayName("Test update of UserUCCImpl class with a Firstname")
  void testUpdateFirstname() {
    User user = (User) factory.getPublicUser();
    user.setId(1);
    user.setFirstname("prenom");
    Mockito.when(userDAO.getOneById(user.getId())).thenReturn(user);
    Mockito.when(userDAO.updateUser(user)).thenReturn(true);
    assertEquals(true, userUCC.update(user.getId(), user));

  }

  @Test
  @DisplayName("Test update of UserUCCImpl class with a Phone")
  void testUpdatePhone() {
    User user = (User) factory.getPublicUser();
    user.setId(1);
    user.setPhone("phone");
    Mockito.when(userDAO.getOneById(user.getId())).thenReturn(user);
    Mockito.when(userDAO.updateUser(user)).thenReturn(true);
    assertEquals(true, userUCC.update(user.getId(), user));

  }

  @Test
  @DisplayName("Test update of UserUCCImpl class with an Internship")
  void testUpdateInternship() {
    User user = (User) factory.getPublicUser();
    user.setId(1);
    user.setHasInternship(true);
    Mockito.when(userDAO.getOneById(user.getId())).thenReturn(user);
    Mockito.when(userDAO.updateUser(user)).thenReturn(true);
    assertEquals(true, userUCC.update(user.getId(), user));

  }

  @Test
  @DisplayName("Test update of UserUCCImpl class with a fatal exception")
  void testUpdateFatalException() {
    User user = (User) factory.getPublicUser();
    user.setId(1);
    user.setHasInternship(true);
    Mockito.when(userDAO.getOneById(user.getId())).thenReturn(user);
    Mockito.when(userDAO.updateUser(user)).thenThrow(new FatalException("SQL Connection Error"));
    assertThrows(FatalException.class, () -> {
      userUCC.update(user.getId(), user);
    });
  }

  @Test
  @DisplayName("Test update of UserUCCImpl class with an invalid id")
  void testUpdateInvalidId() {
    User user = (User) factory.getPublicUser();
    user.setLastname("nom");
    Mockito.when(userDAO.getOneById(user.getId())).thenReturn(null);
    assertFalse(userUCC.update(user.getId(), user));
    }



}