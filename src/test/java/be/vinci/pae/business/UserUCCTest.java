package be.vinci.pae.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.user.User;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserUCC;
import be.vinci.pae.business.user.UserUCCImpl;
import be.vinci.pae.business.year.YearDTO;
import be.vinci.pae.dal.user.UserDAO;
import be.vinci.pae.dal.year.YearDAO;
import be.vinci.pae.exceptions.BusinessException;
import be.vinci.pae.exceptions.ConflictException;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.exceptions.NotFoundException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.MockedStatic;
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
    Mockito.reset(userDAO);
    Mockito.reset(yearDAO);
  }

  @Test
  @DisplayName("Test login of UserUCCImpl class")
  void testLoginDefault() {
    String email = "test@vinci.be";
    String password = "test";
    User user = (User) factory.getPublicUser();
    user.setPassword(user.hashPassword(password));
    when(userDAO.getOneByEmail(email)).thenReturn(user);
    assertNotNull(userUCC.login(email, password));
  }

  @Test
  @DisplayName("Test login of UserUCCImpl class with wrong email")
  void testLoginWrongEmail() {
    String email = "wrongEmail";
    String password = "test";
    User user = (User) factory.getPublicUser();
    user.setPassword(user.hashPassword(password));
    when(userDAO.getOneByEmail(email)).thenReturn(null);
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
    when(userDAO.getOneByEmail(email)).thenReturn(user);
    assertThrows(BusinessException.class, () -> {
      userUCC.login(email, password);
    });
  }

  @Test
  @DisplayName("Test login of UserUCCImpl class with SQLException")
  void testLoginSQLException() {
    String email = "prenom.nom";
    String password = "password";
    when(userDAO.getOneByEmail(email))
        .thenThrow(new FatalException("SQL Connection Error"));
    assertThrows(FatalException.class, () -> {
      userUCC.login(email, password);
    });
  }

  @Test
  @DisplayName("Test register of UserUCCImpl class")
  void testRegister() {
    YearDTO year = factory.getYearDTO();
    year.setId(1);
    year.setAnnee("2023-2024");
    when(yearDAO.getOneByYear("2023-2024")).thenReturn(year);
    User user1 = (User) factory.getPublicUser();
    user1.setEmail("prenom.nom@vinci.be");
    user1.setPassword("password");
    user1.setRole("A");
    user1.setFirstname("prenom");
    user1.setLastname("nom");
    user1.setPhone("phone");
    user1.setidSchoolYear(1);
    User user2 = (User) factory.getPublicUser();
    user2.setEmail("nom.prenom@vinci.be");
    user2.setPassword("password");
    user2.setRole("P");
    user2.setFirstname("nom");
    user2.setLastname("prenom");
    user2.setPhone("phone");
    user2.setidSchoolYear(1);
    when(userDAO.getOneByEmail("prenom.nom@vinci.be")).thenReturn(null);
    when(userDAO.insertUser(user1)).thenReturn(user1);
    when(userDAO.getOneByEmail("nom.prenom@vinci.be")).thenReturn(null);
    when(userDAO.insertUser(user2)).thenReturn(user2);
    User registeredUser1 = (User) userUCC.register(user1);
    User registeredUser2 = (User) userUCC.register(user2);
    assertEquals("A", registeredUser1.renderRole(registeredUser1));
    assertEquals("P", registeredUser2.renderRole(registeredUser2));
    assertEquals("prenom.nom@vinci.be", registeredUser1.getEmail());
    assertEquals("nom.prenom@vinci.be", registeredUser2.getEmail());
  }

  @Test
  @DisplayName("Test UserUCC registration with an existing email")
  void testUserUCCRegistrationWithExistingEmail() {
    YearDTO year = factory.getYearDTO();
    year.setId(1);
    year.setAnnee("2023-2024");
    when(yearDAO.getOneByYear("2023-2024")).thenReturn(year);
    User user = (User) factory.getPublicUser();
    user.setEmail("prenom.nom@vinci.be");
    user.setPassword("password");
    user.setRole("A");
    user.setFirstname("prenom");
    user.setLastname("nom");
    user.setPhone("phone");
    user.setidSchoolYear(1);
    when(userDAO.getOneByEmail("prenom.nom@vinci.be")).thenReturn(user);
    assertThrows(ConflictException.class, () -> {
      userUCC.register(user);
    });
  }

  @Test
  @DisplayName("Test UserUCC registration with student email")
  void testUserUCCRegistrationWithStudentEmail() {
    YearDTO year = factory.getYearDTO();
    year.setId(1);
    year.setAnnee("2023-2024");
    when(yearDAO.getOneByYear("2023-2024")).thenReturn(year);
    User user = (User) factory.getPublicUser();
    user.setEmail("prenom.nom@student.vinci.be");
    user.setFirstname("prenom");
    user.setLastname("nom");
    user.setPhone("phone");
    user.setPassword("password");
    when(userDAO.getOneByEmail("prenom.nom@student.vinci.be")).thenReturn(null);
    when(userDAO.insertUser(user)).thenReturn(user);
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
    YearDTO year = factory.getYearDTO();
    year.setId(1);
    year.setAnnee("2023-2024");
    when(yearDAO.getOneByYear("2023-2024")).thenReturn(year);
    User user = (User) factory.getPublicUser();
    user.setEmail("prenom.nom@student.vinci.be");
    user.setRole("E");
    when(userDAO.getOneByEmail(user.getEmail())).thenReturn(null);
    when(userDAO.insertUser(user)).thenThrow(new FatalException("SQL Connection Error"));
    assertThrows(FatalException.class, () -> {
      userUCC.register(user);
    });
  }

  @Test
  @DisplayName("Test getOne of UserUCCImpl class")
  void testGetOne() {
    User user = (User) factory.getPublicUser();
    when(userDAO.getOneById(1)).thenReturn(user);
    assertEquals(user, userUCC.getOne(1));
  }

  @Test
  @DisplayName("Test getOne of UserUCCImpl class with wrong id")
  void testGetOneWrongId() {
    User user = (User) factory.getPublicUser();
    when(userDAO.getOneById(1)).thenReturn(user);
    assertThrows(NotFoundException.class, () -> {
      userUCC.getOne(2);
    });
  }

  @Test
  @DisplayName("Test getOne of UserUCCImpl class with SQLException")
  void testGetOneSQLException() {
    User user = (User) factory.getPublicUser();
    when(userDAO.getOneById(user.getId()))
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

    when(userDAO.getAllUsers()).thenReturn(Arrays.asList(user1, user2));
    List<UserDTO> result = userUCC.getAll();
    assertNotNull(result);
  }

  @Test
  @DisplayName("Test getAll of UserUCCImpl class - Size Check")
  void testGetAllSize() {
    User user1 = (User) factory.getPublicUser();
    User user2 = (User) factory.getPublicUser();

    when(userDAO.getAllUsers()).thenReturn(Arrays.asList(user1, user2));
    List<UserDTO> result = userUCC.getAll();
    assertEquals(2, result.size());
  }

  @Test
  @DisplayName("Test method getAllUsers of UserDAOImpl class if null")
  void testGetAllUsersNull() {
    when(userDAO.getAllUsers()).thenReturn(null);
    assertThrows(NotFoundException.class, () -> {
      userUCC.getAll();
    });
  }

  @Test
  @DisplayName("Test method getAllUsers of UserDAOImpl class if SQLException")
  void testGetAllUsersSQLException() {
    when(userDAO.getAllUsers()).thenThrow(new FatalException("SQL Connection Error"));
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
    when(userDAO.getOneById(user.getId())).thenReturn(user);
    when(userDAO.updateUser(user)).thenReturn(true);
    assertEquals(true, userUCC.update(user.getId(), user));
  }


  @Test
  @DisplayName("Test update of UserUCCImpl class with a email")
  void testUpdateEmail() {
    User user = (User) factory.getPublicUser();
    user.setId(1);
    user.setEmail("nom.prenom@vinci.be");
    when(userDAO.getOneById(user.getId())).thenReturn(user);
    when(userDAO.updateUser(user)).thenReturn(true);
    assertEquals(true, userUCC.update(user.getId(), user));

  }

  @Test
  @DisplayName("Test update of UserUCCImpl class with a Lastname")
  void testUpdateLastname() {
    User user = (User) factory.getPublicUser();
    user.setId(1);
    user.setLastname("nom");
    when(userDAO.getOneById(user.getId())).thenReturn(user);
    when(userDAO.updateUser(user)).thenReturn(true);
    assertEquals(true, userUCC.update(user.getId(), user));

  }


  @Test
  @DisplayName("Test update of UserUCCImpl class with a Firstname")
  void testUpdateFirstname() {
    User user = (User) factory.getPublicUser();
    user.setId(1);
    user.setFirstname("prenom");
    when(userDAO.getOneById(user.getId())).thenReturn(user);
    when(userDAO.updateUser(user)).thenReturn(true);
    assertEquals(true, userUCC.update(user.getId(), user));

  }

  @Test
  @DisplayName("Test update of UserUCCImpl class with a Phone")
  void testUpdatePhone() {
    User user = (User) factory.getPublicUser();
    user.setId(1);
    user.setPhone("phone");
    when(userDAO.getOneById(user.getId())).thenReturn(user);
    when(userDAO.updateUser(user)).thenReturn(true);
    assertEquals(true, userUCC.update(user.getId(), user));

  }

  @Test
  @DisplayName("Test update of UserUCCImpl class with an Internship")
  void testUpdateInternship() {
    User user = (User) factory.getPublicUser();
    user.setId(1);
    user.setHasInternship(true);
    when(userDAO.getOneById(user.getId())).thenReturn(user);
    when(userDAO.updateUser(user)).thenReturn(true);
    assertEquals(true, userUCC.update(user.getId(), user));

  }

  @Test
  @DisplayName("Test update of UserUCCImpl class with a fatal exception")
  void testUpdateFatalException() {
    User user = (User) factory.getPublicUser();
    user.setId(1);
    user.setHasInternship(true);
    when(userDAO.getOneById(user.getId())).thenReturn(user);
    when(userDAO.updateUser(user)).thenThrow(new FatalException("SQL Connection Error"));
    assertThrows(FatalException.class, () -> {
      userUCC.update(user.getId(), user);
    });
  }

  @Test
  @DisplayName("Test update of UserUCCImpl class with an invalid id")
  void testUpdateInvalidId() {
    User user = (User) factory.getPublicUser();
    user.setLastname("nom");
    when(userDAO.getOneById(user.getId())).thenReturn(null);
    assertFalse(userUCC.update(user.getId(), user));
  }

  @Test
  @DisplayName("test check password OK")
  void testCheckPasswordOK() {
    // Initialise l'utilisateur
    UserDTO user = factory.getPublicUser();
    user.setId(1);
    String password = "password";

    // Utilise un sel fixe pour le test (cela ne devrait être utilisé que pour les tests)
    String salt = BCrypt.gensalt();
    String hashpassword = BCrypt.hashpw(password, salt);
    user.setPassword(hashpassword);

    // Configure le mock pour retourner l'utilisateur
    when(userDAO.getOneById(user.getId())).thenReturn(user);

    // Teste la méthode checkPassword
    boolean passwordMatch = userUCC.checkPassword(user.getId(), password);

    // Assert que le mot de passe est correct
    assertTrue(passwordMatch, "Le mot de passe doit correspondre à celui haché");
  }

  @Test
  @DisplayName("test checkPassword with null user")
  void testCheckPasswordWithNullUser() {
    // ID pour lequel userDAO.getOneById retournera null
    int userId = 1;

    // Configure le mock pour retourner null
    when(userDAO.getOneById(userId)).thenReturn(null);

    // Teste que la bonne exception est levée
    assertThrows(NotFoundException.class, () -> {
      userUCC.checkPassword(userId, "anyPassword");
    }, "NotFoundException should be thrown when the user is not found");
  }




}