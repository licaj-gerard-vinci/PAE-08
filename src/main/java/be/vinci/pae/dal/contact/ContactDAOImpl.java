package be.vinci.pae.dal.contact;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.year.YearDTO;
import be.vinci.pae.dal.DALBackService;
import be.vinci.pae.dal.utils.DALBackServiceUtils;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.exceptions.NotFoundException;
import be.vinci.pae.presentation.filters.Log;
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
  private DALBackServiceUtils dalBackServiceUtils;

  @Inject
  private Factory factory;

  /**
   * Gets the contacts.
   *
   * @return the contacts.
   */
  @Override
  public List<ContactDTO> getContacts() {
    String query =
        "SELECT comp.company_name, comp.company_designation, con.contact_contact_status,"
            + " con.contact_meeting_place"
            + ", con.contact_refusal_reason, con.contact_contact_id "
            + "FROM pae.users AS usr "
            + "JOIN pae.contacts AS con ON usr.user_id = con.student_id "
            + "JOIN pae.companies AS comp ON con.company_id = comp.company_id ";

    List<ContactDTO> contacts = new ArrayList<>();

    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          contacts.add(rsToContact(rs, "get"));
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return contacts;
  }

  /**
   * Fetches all contact information for a specific user.
   *
   * @param id The ID of the user.
   * @return A list of contact information.
   * @throws FatalException If an SQL exception occurs.
   */
  public List<ContactDTO> getContactsAllInfo(int id) {

    String query =
        "SELECT c.*, u.*, comp.*, sy.* "
            + "FROM pae.contacts c "
            + "JOIN pae.users u ON c.contact_student_id = u.user_id "
            + "JOIN pae.companies comp ON c.contact_company_id = comp.company_id "
            + "LEFT JOIN pae.school_years sy ON u.user_school_year_id = sy.school_year_id "
            + "WHERE u.user_id = ?";

    List<ContactDTO> contacts = new ArrayList<>();

    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setInt(1, id);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          contacts.add(rsToContact(rs, "get"));
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }

    return contacts;
  }

  /**
   * Get a contact by its ID.
   *
   * @param idContact the ID of the contact.
   * @return the contact.
   */
  public ContactDTO getContactById(int idContact) {
    String query =
        "SELECT c.*, u.*, comp.*, sy.* "
            + "FROM pae.contacts c "
            + "JOIN pae.users u ON c.contact_student_id = u.user_id "
            + "JOIN pae.companies comp ON c.contact_company_id = comp.company_id "
            + "LEFT JOIN pae.school_years sy ON u.user_school_year_id = sy.school_year_id "
            + "WHERE c.contact_id = ?";

    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setInt(1, idContact);
      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          return rsToContact(rs, "checkGet");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException(e);
    }

    return null;
  }

  /**
   * Inserts a new contact into the database.
   *
   * @param contact The contact information to insert.
   * @throws RuntimeException If an SQL exception occurs.
   */
  public void insertContact(ContactDTO contact) {
    String query = "INSERT INTO pae.contacts "
        + "(contact_school_year_id, contact_company_id, contact_student_id, "
        + "contact_status, contact_version) "
        + "VALUES (1, ?, ?, ?, 1)";
    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setInt(1, contact.getEntreprise().getId());
      statement.setInt(2, contact.getUtilisateur().getId());
      statement.setString(3, contact.getEtatContact());
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException(e);
    }
  }

  /**
   * Updates a contact in the database.
   *
   * @param contact The contact information to update.
   * @throws RuntimeException If an SQL exception occurs.
   */
  public void updateOneContact(ContactDTO contact) {
    String query = "UPDATE pae.contacts SET contact_company_id = ?, "
        + "contact_student_id = ? , contact_school_year_id = ?,  "
        + "contact_status = ?, contact_meeting_place = ?, contact_refusal_reason = ?, "
        + "contact_version = contact_version + 1 WHERE contact_id = ? AND contact_version = ? "
        + "AND contact_school_year_id = ?";
    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setInt(1, contact.getEntreprise().getId());
      statement.setInt(2, contact.getUtilisateur().getId());
      statement.setInt(3, contact.getAnnee().getId());
      statement.setString(4, contact.getEtatContact());
      statement.setString(5, contact.getLieuxRencontre());
      statement.setString(6, contact.getRaisonRefus());
      statement.setInt(7, contact.getId());
      statement.setInt(8, contact.getVersion());
      statement.setInt(9, contact.getAnnee().getId());
      int rows = statement.executeUpdate();
      if (rows == 0) {
        throw new NotFoundException("Contact not found");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException(e);
    }
  }

  /**
   * Rs to detailed contact.
   *
   * @param rs the rs
   * @return the contact detailled DTO
   * @throws SQLException the SQL exception
   */
  private ContactDTO rsToContact(ResultSet rs, String method) throws SQLException {
    ContactDTO contact = dalBackServiceUtils.fillContactDTO(rs, method);

    EntrepriseDTO entreprise = dalBackServiceUtils.fillEntrepriseDTO(rs, method);
    contact.setEntreprise(entreprise);
    contact.setIdEntreprise(entreprise.getId());
    UserDTO user = dalBackServiceUtils.fillUserDTO(rs, method);
    contact.setIdUtilisateur(user.getId());
    contact.setUtilisateur(user);
    if (method.equals("checkGet")) {
      YearDTO year = factory.getYearDTO();
      year.setId(rs.getInt("school_year_id"));
      year.setAnnee(rs.getString("year"));
      year.setVersion(rs.getInt("school_year_version"));
      contact.setAnnee(year);
    }
    return contact;
  }
}
