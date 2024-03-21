package be.vinci.pae.dal.contact;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.contact.ContactDetailledDTO;
import be.vinci.pae.business.factory.Factory;
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
  public List<ContactDetailledDTO> getContacts(int id) {
    String query =
        "SELECT comp.name, comp.designation, con.contact_status, con.meeting_place"
            + ", con.refusal_reason, con.contact_id "
            + "FROM pae.users AS usr "
            + "JOIN pae.contacts AS con ON usr.user_id = con.student_id "
            + "JOIN pae.companies AS comp ON con.company_id = comp.company_id "
            + "WHERE usr.user_id = ?";

    List<ContactDetailledDTO> contacts = new ArrayList<>();

    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setInt(1, id);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          contacts.add(rsToDetailedContact(rs));
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
    String query = "SELECT contact_id, company_id, student_id, contact_status, "
            + "meeting_place, refusal_reason "
            + "FROM pae.contacts "
            + "WHERE student_id = ?";

    List<ContactDTO> contacts = new ArrayList<>();

    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setInt(1, id);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          contacts.add(rsToContacts(rs));
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
      statement.setInt(1, contact.getEntreprise());
      statement.setInt(2, contact.getUtilisateur());
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
    String query = "UPDATE pae.contacts SET contact_status = ?, refusal_reason = ? "
            + "WHERE company_id = ? AND student_id = ?;";
    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setString(1, contact.getEtatContact());
      statement.setString(2, contact.getRaisonRefus());
      statement.setInt(3, contact.getEntreprise());
      statement.setInt(4, contact.getUtilisateur());
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
  private ContactDetailledDTO rsToDetailedContact(ResultSet rs) throws SQLException {
    ContactDetailledDTO contact = factory.getDetailledContactDTO();
    contact.setId(rs.getInt("contact_id"));
    contact.setEtatContact(rs.getString("contact_status"));
    contact.setLieuxRencontre(rs.getString("meeting_place"));
    contact.setRaisonRefus(rs.getString("refusal_reason"));
    contact.setNomEntreprise(rs.getString("name"));
    contact.setAppellation(rs.getString("designation"));
    return contact;
  }

  private ContactDTO rsToContacts(ResultSet rs) throws SQLException {
    ContactDTO contact = factory.getContactDTO();
    contact.setId(rs.getInt("contact_id"));
    contact.setEntreprise(rs.getInt("company_id"));
    contact.setUtilisateur(rs.getInt("student_id"));
    contact.setEtatContact(rs.getString("contact_status"));
    contact.setLieuxRencontre(rs.getString("meeting_place"));
    contact.setRaisonRefus(rs.getString("refusal_reason"));
    return contact;
  }
}
