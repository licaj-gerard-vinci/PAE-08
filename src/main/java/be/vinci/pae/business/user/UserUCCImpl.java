package be.vinci.pae.business.user;

import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.year.Year;
import be.vinci.pae.business.year.YearDTO;
import be.vinci.pae.business.year.YearUCC;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.user.UserDAO;
import be.vinci.pae.exceptions.BusinessException;
import be.vinci.pae.exceptions.ConflictException;
import be.vinci.pae.exceptions.NotFoundException;
import jakarta.inject.Inject;
import java.util.Date;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

/**
 * The {@code UserUCCImpl} class provides methods for managing user-related operations, such as
 * registering new users and retrieving user data. It makes use of dependency injection to obtain a
 * reference to the {@code UserDAO} for accessing user-related data.
 */
public class UserUCCImpl implements UserUCC {

  @Inject
  private UserDAO userDAO;
  @Inject
  private DALServices dalServices;
  @Inject
  private YearUCC yearUCC;
  @Inject
  private Factory factory;



  /**
   * Login a new user.
   *
   * @return the registered user.
   */
  @Override
  public UserDTO login(String email, String password) {
    try {
      dalServices.openConnection();
      User user = (User) userDAO.getOneByEmail(email);
      if (user == null) {
        throw new NotFoundException("User not found");
      }
      if (!user.checkPassword(password)) {
        throw new BusinessException("Password incorrect");
      }
      return user;
    } finally {
      dalServices.close();
    }
  }

  /**
   * Retrieves a single user by their ID.
   *
   * @param id the user's ID.
   * @return the user with the specified ID or null if not found.
   */
  @Override
  public UserDTO getOne(int id) {
    try {
      dalServices.openConnection();
      User user = (User) userDAO.getOneById(id);
      if (user == null) {
        throw new NotFoundException("User not found");
      }
      return user;
    } finally {
      dalServices.close();
    }
  }

  /**
   * Retrieves all users.
   */
  @Override
  public List<UserDTO> getAll() {
    try {
      dalServices.openConnection();
      List<UserDTO> users = userDAO.getAllUsers();
      if (users == null) {
        throw new NotFoundException("No users found");
      }
      return users;
    } finally {
      dalServices.close();
    }
  }

  /**
   * Registers a new user.
   *
   * @return the registered user.
   */
  @Override
  public UserDTO register(UserDTO userDTO) {
    if (userDTO.getEmail().endsWith("@student.vinci.be")) {
      userDTO.setRole("E");
    } else if (userDTO.getEmail().endsWith("@vinci.be")) {
      User userRole = (User) userDTO;
      if (!userRole.renderRole(userDTO).equals("A") && !userRole.renderRole(userDTO).equals("P")) {
        throw new BusinessException("Invalid role");
      }
    } else {
      throw new BusinessException("Invalid email");
    }
    // Get the current date in the format YYYY-MM-DD
    Year year = (Year) factory.getYearDTO();
    String academicYear = year.renderCurrentYear();

    YearDTO yearDTO = yearUCC.getYearByYear(academicYear);
    userDTO.setSchoolYear(yearDTO);
    // Set the year and year ID for the user
    userDTO.setIdSchoolYear(yearDTO.getId());

    try {
      dalServices.startTransaction();
      UserDTO user = userDAO.getOneByEmail(userDTO.getEmail());
      if (user != null) {
        throw new ConflictException("Email already used");
      }
      userDTO.setPassword(((User) userDTO).hashPassword(userDTO.getPassword()));
      Date registrationDate = new java.sql.Date(System.currentTimeMillis());
      userDTO.setRegistrationDate(registrationDate);
      user = userDAO.insertUser(userDTO);
      dalServices.commitTransaction();
      return user;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }


  /**
   * Updates a user.
   *
   * @return the updated user.
   */
  public boolean update(int id, UserDTO user) {

    UserDTO userBeforeUpdate = userDAO.getOneById(id);

    if (userBeforeUpdate == null) {
      return false;
    }
    if (user.getEmail() != null) {
      userBeforeUpdate.setEmail(user.getEmail());
    }
    if (user.getLastname() != null) {
      userBeforeUpdate.setLastname(user.getLastname());
    }
    if (user.getFirstname() != null) {
      userBeforeUpdate.setFirstname(user.getFirstname());
    }
    if (user.getPhone() != null) {
      userBeforeUpdate.setPhone(user.getPhone());
    }

    if (user.getPassword() != null && !user.getPassword().isEmpty()) {
      String passwordHashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
      userBeforeUpdate.setPassword(passwordHashed);
    }

    if (user.getHasInternship()) {
      userBeforeUpdate.setHasInternship(user.getHasInternship());
    }

    try {
      dalServices.startTransaction();
      boolean result = userDAO.updateUser(userBeforeUpdate);
      dalServices.commitTransaction();
      return result;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  /**
   * Verifies a user.
   *
   * @return the verified user.
   */
  public boolean checkPassword(int id, String password) {
    try {
      dalServices.openConnection();
      User user = (User) userDAO.getOneById(id);
      if (user == null) {
        throw new NotFoundException("User not found");
      }
      return user.checkPassword(password);
    } finally {
      dalServices.close();
    }
  }


}