package be.vinci.pae.business.user;

import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.user.UserDAO;
import be.vinci.pae.presentation.exceptions.TokenDecodingException;
import jakarta.inject.Inject;
import jakarta.xml.bind.ValidationException;

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
      if (user == null || !user.checkPassword(password)) {
        throw new ValidationException("Invalid email or password");
      }
      dalServices.commitTransaction();
      return user;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new RuntimeException(e);
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
      dalServices.startTransaction();
      User user = (User) userDAO.getOneById(id);
      dalServices.commitTransaction();
      return user;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new RuntimeException(e);
    } finally {
      dalServices.close();
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
        return null;
      }
      dalServices.commitTransaction();
      return users;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new RuntimeException(e);
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

    if (!userDTO.getEmail().matches("^[a-zA-Z]+\\.[a-zA-Z]+@.*")) {
      return null;
    }
    if (userDTO.getEmail().endsWith("@student.vinci.be")) {
      userDTO.setRole("E");
    } else if (userDTO.getEmail().endsWith("@vinci.be")) {
      if (!userDTO.getRole().equals("A") && !userDTO.getRole().equals("P")) {
        return null;
      }
    } else {
      return null;
    }

    try {
      dalServices.startTransaction();
      User user = (User) userDAO.getOneByEmail(userDTO.getEmail());
      System.out.println(user);
      if (user != null) {
        throw new ValidationException("Email already used");
      }
      userDTO.setPassword(((User) userDTO).hashPassword(userDTO.getPassword()));
      Date dateInscription = new java.sql.Date(System.currentTimeMillis());
      userDTO.setRegistrationDate(dateInscription);
      user = (User) userDAO.insertUser(userDTO);
      dalServices.commitTransaction();
      return user;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new RuntimeException(e);
    } finally {
      dalServices.close();
    }
  }
}