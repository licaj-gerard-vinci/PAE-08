package be.vinci.pae.dal.contact;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.year.YearDTO;
import be.vinci.pae.dal.DALBackService;
import be.vinci.pae.dal.utils.DALBackServiceUtils;
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
          contacts.add(rsToContact(rs));
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
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
    System.out.println("enter DAO getContactById");
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
          return rsToContact(rs);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }

    return null;
  }

  /**
   * Check if a contact exists.
   *
   * @param idUser The ID of the user.
   * @param idEntreprise The ID of the company.
   * @return The contact if it exists, null otherwise.
   */
  public ContactDTO checkContactExists(int idUser, int idEntreprise) {
    System.out.println("enter check");
    String query = "SELECT * FROM pae.contacts, pae.school_years, pae.companies, "
            + "pae.users WHERE contact_student_id = ? AND contact_company_id = ?";
    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setInt(1, idUser);
      statement.setInt(2, idEntreprise);
      ResultSet rs = statement.executeQuery();
      if (rs.next()) {
        return rsToContact(rs);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
    System.out.println("nothing found");
    return null;
  }

  /**
   * Check if a contact can be updated to the 'taken' state.
   *
   * @param idUser The ID of the user.
   * @param idEntreprise The ID of the company.
   * @return true if the contact can be updated, false otherwise.
   */
  public boolean checkContactAndState(int idUser, int idEntreprise, String expectedState) {
    String query = "SELECT contact_status FROM pae.contacts WHERE contact_student_id = ? "
            + "AND contact_company_id = ?";

    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setInt(1, idUser);
      statement.setInt(2, idEntreprise);
      ResultSet rs = statement.executeQuery();
      System.out.println("expectedState: " + expectedState);
      if (rs.next()) {
        String currentState = rs.getString("contact_status");
        System.out.println("receivedState: " + currentState);
        return currentState.equals(expectedState);
      } else {
        return false;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
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
    String query = "UPDATE pae.contacts SET contact_status = ?, contact_meeting_place = ?, "
            + "contact_refusal_reason = ? , contact_version = ?  "
            + "WHERE contact_company_id = ? AND contact_student_id = ?;";
    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setString(1, contact.getEtatContact());
      statement.setString(2, contact.getLieuxRencontre());
      statement.setString(3, contact.getRaisonRefus());
      statement.setInt(4, contact.getVersion());
      statement.setInt(5,
          contact.getEntreprise().getId()); // Utilisez getId() pour obtenir l'ID de l'entreprise
      statement.setInt(6,
          contact.getUtilisateur().getId()); // Utilisez getId() pour obtenir l'ID de l'utilisateur
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
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
    YearDTO year = factory.getYearDTO();
    year.setId(rs.getInt("school_year_id"));
    year.setAnnee(rs.getString("year"));

    ContactDTO contact = factory.getContactDTO();

    contact.setId(rs.getInt("contact_id"));
    contact.setEtatContact(rs.getString("contact_status"));
    contact.setLieuxRencontre(rs.getString("contact_meeting_place"));
    contact.setRaisonRefus(rs.getString("contact_refusal_reason"));
    contact.setVersion(rs.getInt("contact_version"));

    EntrepriseDTO entreprise = dalBackServiceUtils.fillEntrepriseDTO(rs);
    contact.setEntreprise(entreprise);
    contact.setIdEntreprise(entreprise.getId());
    UserDTO user = dalBackServiceUtils.fillUserDTO(rs);
    contact.setIdUtilisateur(user.getId());
    contact.setUtilisateur(user);
    contact.setAnnee(year);

    return contact;
  }
}
