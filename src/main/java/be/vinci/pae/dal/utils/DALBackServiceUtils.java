package be.vinci.pae.dal.utils;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.responsable.ResponsableDTO;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.year.YearDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Interface DALBackServiceUtils.
 */
public interface DALBackServiceUtils {



  /**
   * Fills a UserDTO with data from a ResultSet.
   *
   * @param rs the ResultSet containing user data.
   * @param method the method to call
   * @return UserDTO filled with data from the ResultSet.
   * @throws SQLException if there is an issue accessing the ResultSet data.
   */
  UserDTO fillUserDTO(ResultSet rs, String method) throws SQLException;

  /**
   * Fills a ContactDTO with data from a ResultSet.
   *
   * @param rs the ResultSet containing contact data.
   * @param method the method to call
   * @return ContactDTO filled with data from the ResultSet.
   * @throws SQLException if there is an issue accessing the ResultSet data.
   */
  ContactDTO fillContactDTO(ResultSet rs, String method) throws SQLException;

  /**
   * Fills a ResponsableDTO with data from a ResultSet.
   *
   * @param rs the ResultSet containing responsable data.
   * @param method the method to call
   * @return ResponsableDTO filled with data from the ResultSet.
   * @throws SQLException if there is an issue accessing the ResultSet data.
   */
  ResponsableDTO fillResponsableDTO(ResultSet rs, String method) throws SQLException;

  /**
   * Fills a EntrepriseDTO with data from a ResultSet.
   *
   * @param rs the ResultSet containing entreprise data.
   * @param method the method to call
   * @return EntrepriseDTO filled with data from the ResultSet.
   * @throws SQLException if there is an issue accessing the ResultSet data.
   */
  EntrepriseDTO fillEntrepriseDTO(ResultSet rs, String method) throws SQLException;

  /**
   * Fills a ManagerDTO with data from a ResultSet.
   *
   * @param rs the ResultSet containing manager data.
   * @param method the method to call
   * @return ManagerDTO filled with data from the ResultSet.
   * @throws SQLException if there is an issue accessing the ResultSet data.
   */
  ResponsableDTO fillManagerDTO(ResultSet rs, String method) throws SQLException;

  /**
   * Fills a YearDTO with data from a ResultSet.
   *
   * @param rs the ResultSet containing year data.
   * @return YearDTO filled with data from the ResultSet.
   * @throws SQLException if there is an issue accessing the ResultSet data.
   */
  YearDTO fillYearDTO(ResultSet rs) throws SQLException;
}
