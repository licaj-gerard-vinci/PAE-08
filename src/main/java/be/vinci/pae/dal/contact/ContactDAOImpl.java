package be.vinci.pae.dal.contact;

import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.year.YearDTO;
import be.vinci.pae.dal.DALBackService;
import be.vinci.pae.dal.utils.DALBackServiceUtils;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.exceptions.NotFoundException;
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

  /**
   * Gets the contacts.
   *
   * @return the contacts.
   */
  @Override
  public List<ContactDTO> getContacts() {
    String query =
        "SELECT con.*, usr.*, comp.*, sy.* "
            + "FROM pae.users AS usr "
            + "JOIN pae.contacts AS con ON usr.user_id = con.contact_student_id "
            + "JOIN pae.companies AS comp ON con.contact_company_id = comp.company_id "
            + "JOIN pae.school_years AS sy ON con.contact_school_year_id = sy.school_year_id ";

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
            + "JOIN pae.school_years sy ON c.contact_school_year_id = sy.school_year_id "
            + "WHERE u.user_id = ? AND u.user_id = c.contact_student_id "
            + "AND c.contact_company_id = comp.company_id";


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
            + "JOIN pae.school_years sy ON c.contact_school_year_id = sy.school_year_id "
            + "WHERE c.contact_id = ? AND c.contact_student_id = u.user_id ";

    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setInt(1, idContact);
      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          return rsToContact(rs, "get");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException(e);
    }
    return null;
  }



  /**
   * Gets the all contacts with the idCompany.
   *
   * @param idCompany The ID of the contact to be deleted.
   * @return the list of contacts.
   */
  public List<ContactDTO> getContactsByCompanyId(int idCompany) {
    String query =
        "SELECT c.*, u.*, comp.*, sy.* "
            + "FROM pae.contacts c "
            + "JOIN pae.users u ON c.contact_student_id = u.user_id "
            + "JOIN pae.companies comp ON c.contact_company_id = comp.company_id "
            + "LEFT JOIN pae.school_years sy ON c.contact_school_year_id = sy.school_year_id "
            + "WHERE c.contact_company_id = ? AND c.contact_student_id = u.user_id ";

    List<ContactDTO> contacts = new ArrayList<>();

    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setInt(1, idCompany);
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
   * Inserts a new contact into the database.
   *
   * @param contact The contact information to insert.
   * @throws RuntimeException If an SQL exception occurs.
   */
  public void insertContact(ContactDTO contact) {
    String query = "INSERT INTO pae.contacts "
        + "(contact_school_year_id, contact_company_id, contact_student_id, "
        + "contact_status, contact_version) "
        + "VALUES (?, ?, ?, ?, 1)";
    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setInt(1, contact.getIdYear());
      statement.setInt(2, contact.getCompany().getId());
      statement.setInt(3, contact.getStudent().getId());
      statement.setString(4, contact.getContactStatus());
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
  public void updateContact(ContactDTO contact) {
    String query = "UPDATE pae.contacts SET contact_company_id = ?, "
        + "contact_student_id = ? , contact_school_year_id = ?,  "
        + "contact_status = ?, contact_meeting_place = ?, contact_refusal_reason = ?, "
        + "contact_version = contact_version + 1 WHERE contact_id = ? AND contact_version = ? "
        + "AND contact_school_year_id = ?";
    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setInt(1, contact.getCompany().getId());
      statement.setInt(2, contact.getStudent().getId());
      statement.setInt(3, contact.getYear().getId());
      statement.setString(4, contact.getContactStatus());
      statement.setString(5, contact.getMeetingPlace());
      statement.setString(6, contact.getRefusalReason());
      statement.setInt(7, contact.getId());
      statement.setInt(8, contact.getVersion());
      statement.setInt(9, contact.getYear().getId());
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
   * @return the contact DTO
   * @throws SQLException the SQL exception
   */
  private ContactDTO rsToContact(ResultSet rs, String method) throws SQLException {
    ContactDTO contact = dalBackServiceUtils.fillContactDTO(rs, method);

    CompanyDTO company = dalBackServiceUtils.fillCompanyDTO(rs, method);
    contact.setCompany(company);
    contact.setIdCompany(company.getId());
    UserDTO user = dalBackServiceUtils.fillUserDTO(rs, method);
    contact.setIdStudent(user.getId());
    contact.setStudent(user);
    YearDTO year = dalBackServiceUtils.fillYearDTO(rs);
    contact.setYear(year);
    return contact;
  }
}
