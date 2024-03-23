package be.vinci.pae.dal.contact;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.year.YearDTO;
import be.vinci.pae.dal.DALBackService;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class ContactDAOImpl.
 */
public class ContactDAOImpl implements ContactDAO {

  @Inject
  private DALBackService dalBackService;

  @Inject
  private Factory factory;

  /**
   * Gets the contacts.
   *
   * @param id the user ID.
   * @return the contacts.
   */
  @Override
  public List<ContactDTO> getContacts(int id) {
    String query =
        "SELECT comp.company_name, comp.company_designation, con.contact_contact_status, con.contact_meeting_place"
            + ", con.contact_refusal_reason, con.contact_contact_id "
            + "FROM pae.users AS usr "
            + "JOIN pae.contacts AS con ON usr.user_id = con.student_id "
            + "JOIN pae.companies AS comp ON con.company_id = comp.company_id "
            + "WHERE usr.user_id = ?";

    List<ContactDTO> contacts = new ArrayList<>();

    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setInt(1, id);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          contacts.add(rsToContact(rs));
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return contacts;
  }

  /**
   * Fetches all contact information for a specific user.
   *
   * @param id The ID of the user.
   * @return A list of contact information.
   * @throws RuntimeException If an SQL exception occurs.
   */
  public List<ContactDTO> getContactsAllInfo(int id) {

    String query = "SELECT con.contact_id, con.contact_status, con.contact_meeting_place, con.contact_refusal_reason, " +
        "com.company_id, com.company_name, com.company_designation, com.company_address, com.company_city, com.company_phone_number, com.company_email, com.company_is_blacklisted, com.company_blacklist_reason, " +
        "usr.user_id, usr.user_email, usr.user_lastname, usr.user_firstname, usr.user_phone_number, usr.user_registration_date, usr.user_role, " +
        "sch.school_year_id, sch.year " +
        "FROM pae.contacts con " +
        "JOIN pae.users usr ON con.student_id = usr.user_id " +
        "JOIN pae.companies com ON con.company_id = com.company_id " +
        "JOIN pae.school_years sch ON usr.user_school_year_id = sch.school_year_id " +
        "WHERE usr.user_id = ?";

    List<ContactDTO> contacts = new ArrayList<>();

    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setInt(1, id);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          contacts.add(rsToContact(rs));
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return contacts;
  }


  /**
   * Inserts a new contact into the database.
   *
   * @param contact The contact information to insert.
   * @throws RuntimeException If an SQL exception occurs.
   */
  public void insertContact(ContactDTO contact) {
    String query = "INSERT INTO pae.contacts "
            + "(school_year_id, company_id, student_id, contact_status) VALUES (1, ?, ?, ?)";
    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setObject(1, contact.getEntreprise());
      statement.setObject(2, contact.getUtilisateur());
      statement.setString(3, contact.getEtatContact());
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Updates a contact in the database.
   *
   * @param contact The contact information to update.
   * @throws RuntimeException If an SQL exception occurs.
   */
  public void updateContact(ContactDTO contact) {
    String query = "UPDATE pae.contacts SET contact_status = ?, contact_refusal_reason = ? "
            + "WHERE company_id = ? AND student_id = ?;";
    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setString(1, contact.getEtatContact());
      statement.setString(2, contact.getRaisonRefus());
      statement.setObject(3, contact.getEntreprise());
      statement.setObject(4, contact.getUtilisateur());
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Rs to detailed contact.
   *
   * @param rs the rs
   * @return the contact detailled DTO
   * @throws SQLException the SQL exception
   */
  private ContactDTO rsToContact(ResultSet rs) throws SQLException {

    EntrepriseDTO entreprise = factory.getEntrepriseDTO();
    UserDTO user = factory.getPublicUser();
    YearDTO year = factory.getYearDTO();
    ContactDTO contact = factory.getContactDTO();


    //ENTREPRISE
    entreprise.setId(rs.getInt("company_id"));
    entreprise.setNom(rs.getString("company_name"));
    entreprise.setAppellation(rs.getString("company_designation"));
    entreprise.setAdresse(rs.getString("company_address"));
    entreprise.setCity(rs.getString("company_city"));
    entreprise.setNumTel(rs.getString("company_phone_number"));
    entreprise.setEmail(rs.getString("company_email"));
    entreprise.setBlackListed(rs.getBoolean("company_is_blacklisted"));
    entreprise.setMotivation_blacklist(rs.getString("company_blacklist_reason"));

    //USER
    user.setId(rs.getInt("user_id"));
    user.setEmail(rs.getString("user_email"));
    user.setLastname(rs.getString("user_lastname"));
    user.setFirstname(rs.getString("user_firstname"));
    user.setPhone(rs.getString("user_phone_number"));
    user.setRegistrationDate(rs.getDate("user_registration_date"));
    user.setRole(rs.getString("user_role"));
    user.setPassword(rs.getString("user_password"));

    //YEAR
    year.setId(rs.getInt("school_year_id"));
    year.setAnnee(rs.getString("year"));

    //CONTACT
    contact.setId(rs.getInt("contact_id"));
    contact.setEtatContact(rs.getString("contact_status"));
    contact.setLieuxRencontre(rs.getString("contact_meeting_place"));
    contact.setRaisonRefus(rs.getString("contact_refusal_reason"));

    contact.setEntreprise(entreprise);
    contact.setUtilisateur(user);
    contact.setAnnee(year);

    return contact;
  }
}
