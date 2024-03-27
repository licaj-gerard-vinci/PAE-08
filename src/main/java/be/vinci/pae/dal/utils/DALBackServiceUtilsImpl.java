package be.vinci.pae.dal.utils;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.manager.ManagerDTO;
import be.vinci.pae.business.user.User;
import be.vinci.pae.business.user.UserDTO;
import jakarta.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Utility class for data access layer back-end services.
 * Provides methods to fill DTOs from SQL ResultSet data.
 */
public class DALBackServiceUtilsImpl implements DALBackServiceUtils {

  @Inject
  private Factory factory;

  /**
   * Fills a UserDTO with data from a ResultSet.
   *
   * @param rs the ResultSet containing user data.
   * @return UserDTO filled with data from the ResultSet.
   * @throws SQLException if there is an issue accessing the ResultSet data.
   */
  @Override
  public  UserDTO fillUserDTO(ResultSet rs, String method) throws SQLException {
    User user = (User) factory.getPublicUser();
    user.setId(rs.getInt("user_id"));
    user.setEmail(rs.getString("user_email"));
    user.setLastname(rs.getString("user_lastname"));
    user.setFirstname(rs.getString("user_firstname"));
    user.setPhone(rs.getString("user_phone_number"));
    user.setPassword(rs.getString("user_password"));
    user.setRegistrationDate(rs.getDate("user_registration_date"));
    user.setRole(rs.getString("user_role"));
    user.setHasInternship(rs.getBoolean("user_has_internship"));
    if (method.equals("update")) {
      user.setVersion(rs.getInt("user_version") + 1);
    } else {
      user.setVersion(rs.getInt("user_version"));
    }
    return user;
  }

  /**
   * Fills a ContactDTO with data from a ResultSet.
   *
   * @param rs the ResultSet containing contact data.
   * @return ContactDTO filled with data from the ResultSet.
   * @throws SQLException if there is an issue accessing the ResultSet data.
   */
  public ContactDTO fillContactDTO(ResultSet rs, String method) throws SQLException {
    ContactDTO contact = (ContactDTO) factory.getContactDTO();
    contact.setId(rs.getInt("contact_id"));
    contact.setContactState(rs.getString("contact_status"));
    contact.setMeetingPlace(rs.getString("contact_meeting_place"));
    contact.setRefusalReason(rs.getString("contact_refusal_reason"));
    if (method.equals("update")) {
      contact.setVersion(rs.getInt("contact_version") + 1);
    } else {
      contact.setVersion(rs.getInt("contact_version"));
    }
    return contact;
  }

  /**
   * Fills a ManagerDTO with data from a ResultSet.
   *
   * @param rs the ResultSet containing manager data.
   * @return ManagerDTO filled with data from the ResultSet.
   * @throws SQLException if there is an issue accessing the ResultSet data.
   */
  public ManagerDTO fillManagerDTO(ResultSet rs, String method) throws SQLException {
    ManagerDTO manager = (ManagerDTO) factory.getManagerDTO();
    manager.setId(rs.getInt("manager_id"));
    manager.setLastname(rs.getString("manager_lastname"));
    manager.setFirstname(rs.getString("manager_firstname"));
    manager.setPhoneNumber(rs.getString("manager_phone_number"));
    manager.setEmail(rs.getString("manager_email"));
    manager.setCompanyId(rs.getInt("manager_company_id"));
    if (method.equals("update")) {
      manager.setVersion(rs.getInt("manager_version") + 1);
    } else {
      manager.setVersion(rs.getInt("manager_version"));
    }
    return manager;
  }

  /**
   * Fills an CompanyDTO with data from a ResultSet.
   *
   * @param rs the ResultSet containing company data.
   * @return CompanyDTO filled with data from the ResultSet.
   * @throws SQLException if there is an issue accessing the ResultSet data.
   */
  public CompanyDTO fillCompanyDTO(ResultSet rs, String method) throws SQLException {
    CompanyDTO company = (CompanyDTO) factory.getCompanyDTO();
    company.setId(rs.getInt("company_id"));
    company.setName(rs.getString("company_name"));
    company.setDesignation(rs.getString("company_designation"));
    company.setAddress(rs.getString("company_address"));
    company.setCity(rs.getString("company_city"));
    company.setPhoneNumber(rs.getString("company_phone_number"));
    company.setEmail(rs.getString("company_email"));
    company.setBlackListed(rs.getBoolean("company_is_blacklisted"));
    company.setBlacklistReason(rs.getString("company_blacklist_reason"));
    if (method.equals("update")) {
      company.setVersion(rs.getInt("company_version") + 1);
    } else {
      company.setVersion(rs.getInt("company_version"));
    }
    return company;
  }

}