package be.vinci.pae.dal.utils;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.responsable.ResponsableDTO;
import be.vinci.pae.business.user.User;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.user.UserDTO;
import jakarta.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DALBackServiceUtilsImpl implements DALBackServiceUtils {

  @Inject
  private Factory factory;

  @Override
  public  UserDTO fillUserDTO(ResultSet rs) throws SQLException {
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
    return user;
  }

  public ContactDTO fillContactDTO(ResultSet rs) throws SQLException {
    ContactDTO contact = (ContactDTO) factory.getContactDTO();
    contact.setId(rs.getInt("contact_id"));
    contact.setEtatContact(rs.getString("contact_status"));
    contact.setLieuxRencontre(rs.getString("contact_meeting_place"));
    contact.setRaisonRefus(rs.getString("contact_refusal_reason"));
    return contact;
  }

  public ResponsableDTO fillResponsableDTO(ResultSet rs) throws SQLException {
    ResponsableDTO responsable = (ResponsableDTO) factory.getResponsableDTO();
    responsable.setId(rs.getInt("manager_id"));
    responsable.setNom(rs.getString("manager_lastname"));
    responsable.setPrenom(rs.getString("manager_firstname"));
    responsable.setNumTel(rs.getString("manager_phone_number"));
    responsable.setEmail(rs.getString("manager_email"));
    responsable.setIdEntreprise(rs.getInt("manager_company_id"));
    return responsable;
  }

  public EntrepriseDTO fillEntrepriseDTO(ResultSet rs) throws SQLException {
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
    return entreprise;
  }

}