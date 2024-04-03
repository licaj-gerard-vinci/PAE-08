package be.vinci.pae.dal.utils;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.entreprise.EntrepriseDTO;
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
    responsable.setNom(rs.getString("manager_lastname"));
    responsable.setPrenom(rs.getString("manager_firstname"));
    responsable.setNumTel(rs.getString("manager_phone_number"));
    responsable.setEmail(rs.getString("manager_email"));
    responsable.setIdEntreprise(rs.getInt("manager_company_id"));
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
  public EntrepriseDTO fillEntrepriseDTO(ResultSet rs, String method) throws SQLException {
    EntrepriseDTO entreprise = (EntrepriseDTO) factory.getEntrepriseDTO();
    entreprise.setId(rs.getInt("company_id"));
    entreprise.setNom(rs.getString("company_name"));
    entreprise.setAppellation(rs.getString("company_designation"));
    entreprise.setAdresse(rs.getString("company_address"));
    entreprise.setCity(rs.getString("company_city"));
    entreprise.setNumTel(rs.getString("company_phone_number"));
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

  public ManagerDTO fillManagerDTO(ResultSet rs, String method) throws SQLException {
    ManagerDTO manager = (ManagerDTO) factory.getManagerDTO();
    manager.setId(rs.getInt("manager_id"));
    manager.setPrenom(rs.getString("manager_firstname"));
    manager.setNom(rs.getString("manager_lastname"));
    manager.setNumTel(rs.getString("manager_phone_number"));
    manager.setEmail(rs.getString("manager_email"));
    manager.setIdEntreprise(rs.getInt("manager_company_id"));
    manager.setVersion(rs.getInt("manager_version"));

    return manager;
  }

}