package be.vinci.pae.dal.utils;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.manager.ManagerDTO;
import be.vinci.pae.business.user.UserDTO;
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
   * Fills a ManagerDTO with data from a ResultSet.
   *
   * @param rs the ResultSet containing manager data.
   * @param method the method to call
   * @return managerDTO filled with data from the ResultSet.
   * @throws SQLException if there is an issue accessing the ResultSet data.
   */
  ManagerDTO fillManagerDTO(ResultSet rs, String method) throws SQLException;

  /**
   * Fills a CompanyDTO with data from a ResultSet.
   *
   * @param rs the ResultSet containing company data.
   * @param method the method to call
   * @return CompanyDTO filled with data from the ResultSet.
   * @throws SQLException if there is an issue accessing the ResultSet data.
   */
  CompanyDTO fillCompanyDTO(ResultSet rs, String method) throws SQLException;
}
