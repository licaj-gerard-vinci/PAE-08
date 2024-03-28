package be.vinci.pae.business.user;

import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.user.UserDAO;
import be.vinci.pae.exceptions.BusinessException;
import be.vinci.pae.exceptions.ConflictException;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.exceptions.NotFoundException;
import jakarta.inject.Inject;
import java.util.Date;
import java.util.List;

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



  /**
   * Login a new user.
   *
   * @return the registered user.
   */
  @Override
  public UserDTO login(String email, String password) {
    try {
      dalServices.startTransaction();
      User user = (User) userDAO.getOneByEmail(email);
      if (user == null) {
        throw new NotFoundException("User not found");
      }
      if (!user.checkPassword(password)) {
        throw new BusinessException("Password incorrect");
      }
      dalServices.commitTransaction();
      return user;
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
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
      dalServices.startTransaction();
      User user = (User) userDAO.getOneById(id);
      if (user == null) {
        throw new NotFoundException("User not found");
      }
      dalServices.commitTransaction();
      return user;
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  /**
   * Retrives all users.
   */
  @Override
  public List<UserDTO> getAll() {
    try {
      dalServices.startTransaction();
      List<UserDTO> users = userDAO.getAllUsers();
      if (users == null) {
        throw new NotFoundException("No users found");
      }
      dalServices.commitTransaction();
      return users;
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
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
      if (!userDTO.getRole().equals("A") && !userDTO.getRole().equals("P")) {
        throw new BusinessException("Invalid role");
      }
    } else {
      throw new BusinessException("Invalid email");
    }

    try {
      dalServices.startTransaction();
      User user = (User) userDAO.getOneByEmail(userDTO.getEmail());
      if (user != null) {
        throw new ConflictException("Email already used");
      }
      userDTO.setPassword(((User) userDTO).hashPassword(userDTO.getPassword()));
      Date dateInscription = new java.sql.Date(System.currentTimeMillis());
      userDTO.setRegistrationDate(dateInscription);
      user = (User) userDAO.insertUser(userDTO);
      dalServices.commitTransaction();
      return user;
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }


  /**
   * Updates a user.
   *
   * @return the updated user.
   */
  public boolean update(UserDTO user) {
    try {
      dalServices.startTransaction();
      boolean result = userDAO.updateUser(user);
      dalServices.commitTransaction();
      return result;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new RuntimeException(e);
    } finally {
      dalServices.close();
    }
  }


}