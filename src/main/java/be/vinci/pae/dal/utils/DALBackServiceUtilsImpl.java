package be.vinci.pae.dal.utils;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.manager.ManagerDTO;
import be.vinci.pae.business.user.User;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.year.YearDTO;
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
    user.setidSchoolYear(rs.getInt("user_school_year_id"));
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
    contact.setEtatContact(rs.getString("contact_status"));
    contact.setLieuxRencontre(rs.getString("contact_meeting_place"));
    contact.setRaisonRefus(rs.getString("contact_refusal_reason"));
    if (method.equals("update")) {
      contact.setVersion(rs.getInt("contact_version") + 1);
    } else {
      contact.setVersion(rs.getInt("contact_version"));
    }
    return contact;
  }

  /**
   * Fills a ResponsableDTO with data from a ResultSet.
   *
   * @param rs the ResultSet containing manager data.
   * @return ResponsableDTO filled with data from the ResultSet.
   * @throws SQLException if there is an issue accessing the ResultSet data.
   */
  public ManagerDTO fillResponsableDTO(ResultSet rs, String method) throws SQLException {
    ManagerDTO responsable = (ManagerDTO) factory.getManagerDTO();
    responsable.setId(rs.getInt("manager_id"));
    responsable.setName(rs.getString("manager_lastname"));
    responsable.setFirstName(rs.getString("manager_firstname"));
    responsable.setPhone(rs.getString("manager_phone_number"));
    responsable.setEmail(rs.getString("manager_email"));
    responsable.setIdCompany(rs.getInt("manager_company_id"));
    if (method.equals("update")) {
      responsable.setVersion(rs.getInt("manager_version") + 1);
    } else {
      responsable.setVersion(rs.getInt("manager_version"));
    }
    return responsable;
  }

  /**
   * Fills an EntrepriseDTO with data from a ResultSet.
   *
   * @param rs the ResultSet containing company data.
   * @return EntrepriseDTO filled with data from the ResultSet.
   * @throws SQLException if there is an issue accessing the ResultSet data.
   */
  public CompanyDTO fillCompanyDTO(ResultSet rs, String method) throws SQLException {
    CompanyDTO entreprise = (CompanyDTO) factory.getCompanyDTO();
    entreprise.setId(rs.getInt("company_id"));
    entreprise.setName(rs.getString("company_name"));
    entreprise.setDesignation(rs.getString("company_designation"));
    entreprise.setAdresse(rs.getString("company_address"));
    entreprise.setCity(rs.getString("company_city"));
    entreprise.setPhone(rs.getString("company_phone_number"));
    entreprise.setEmail(rs.getString("company_email"));
    entreprise.setBlackListed(rs.getBoolean("company_is_blacklisted"));
    entreprise.setMotivation_blacklist(rs.getString("company_blacklist_reason"));
    if (method.equals("update")) {
      entreprise.setVersion(rs.getInt("company_version") + 1);
    } else {
      entreprise.setVersion(rs.getInt("company_version"));
    }
    return entreprise;
  }

  /**
   * Fills a ManagerDTO with data from a ResultSet.
   *
   * @param rs the ResultSet containing contact data.
   * @return ContactDTO filled with data from the ResultSet.
   * @throws SQLException if there is an issue accessing the ResultSet data.
   */
  public ManagerDTO fillManagerDTO(ResultSet rs, String method) throws SQLException {
    ManagerDTO manager = (ManagerDTO) factory.getManagerDTO();
    manager.setId(rs.getInt("manager_id"));
    manager.setFirstName(rs.getString("manager_firstname"));
    manager.setName(rs.getString("manager_lastname"));
    manager.setPhone(rs.getString("manager_phone_number"));
    manager.setEmail(rs.getString("manager_email"));
    manager.setIdCompany(rs.getInt("manager_company_id"));
    manager.setVersion(rs.getInt("manager_version"));

    return manager;
  }

  /**
   * Fills a YearDTO with data from a ResultSet.
   *
   * @param rs the ResultSet containing year data.
   * @return YearDTO filled with data from the ResultSet.
   * @throws SQLException if there is an issue accessing the ResultSet data.
   */
  public YearDTO fillYearDTO(ResultSet rs) throws SQLException {
    YearDTO year = (YearDTO) factory.getYearDTO();
    year.setId(rs.getInt("school_year_id"));
    year.setAnnee(rs.getString("year"));
    year.setVersion(rs.getInt("school_year_version"));
    return year;
  }

}