package be.vinci.pae.dal.contact;

import be.vinci.pae.business.contact.ContactDetailledDTO;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.dal.DALService;
import be.vinci.pae.dal.contact.ContactDAO;
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
  private DALService dalService;

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
            +
            ", con.refusal_reason, con.contact_id "
            +
            "FROM pae.users AS usr "
            +
            "JOIN pae.contacts AS con ON usr.user_id = con.student_id "
            +
            "JOIN pae.companies AS comp ON con.company_id = comp.company_id "
            +
            "WHERE usr.user_id = ?";

    List<ContactDetailledDTO> contacts = new ArrayList<>();

    try (PreparedStatement statement = dalService.preparedStatement(query)) {
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
}
