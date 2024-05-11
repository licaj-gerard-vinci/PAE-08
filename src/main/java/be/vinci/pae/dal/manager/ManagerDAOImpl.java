package be.vinci.pae.dal.manager;

import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.manager.ManagerDTO;
import be.vinci.pae.dal.DALBackService;
import be.vinci.pae.dal.utils.DALBackServiceUtils;
import be.vinci.pae.exceptions.FatalException;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The ManagerDAOImpl class implements the ManagerDAO interface. It provides the data access logic
 * for handling managers.
 */
public class ManagerDAOImpl implements ManagerDAO {

  @Inject
  private DALBackService dalService;

  @Inject
  private DALBackServiceUtils dalBackServiceUtils;

  /**
   * Retrieves a list of managers for a specific company.
   *
   * @param companyId the ID of the company for which to retrieve managers
   * @return a list of ManagerDTO objects representing the managers of the company
   * @throws FatalException if an error occurs during the operation
   */
  @Override
  public List<ManagerDTO> getManagers(int companyId) {
    String query =
        "SELECT m.*,c.* "
            + "FROM pae.managers m, pae.companies c "
            + "WHERE c.company_id = ? AND m.manager_company_id = c.company_id";

    List<ManagerDTO> managers = new ArrayList<>();

    try (PreparedStatement statement = dalService.preparedStatement(query)) {
      statement.setInt(1, companyId);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          managers.add(rsToManager(rs, "get"));
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return managers;
  }

  /**
   * Retrieves the manager by its email.
   *
   * @param email the email of the manager to retrieve
   * @return a ManagerDTO object representing the manager
   * @throws FatalException if an error occurs during the operation
   */
  @Override
  public ManagerDTO getManagerByEmail(String email) {
    String query = "SELECT m.*,c.*"
        + " FROM pae.managers m, pae.companies c "
        + "WHERE m.manager_email = ? AND m.manager_company_id = c.company_id";

    ManagerDTO manager = null;

    try (PreparedStatement statement = dalService.preparedStatement(query)) {
      statement.setString(1, email);
      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          manager = rsToManager(rs, "get");
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return manager;
  }

  /**
   * Retrieves a manager by its first name, last name and email.
   *
   * @param manager the manager to retrieve if exists
   * @return a ManagerDTO object representing the manager
   * @throws FatalException if an error occurs during the operation
   */
  @Override
  public List<ManagerDTO> getManager(ManagerDTO manager) {
    String query = "SELECT m.*,c.*"
        + " FROM pae.managers m, pae.companies c "
        + "WHERE LOWER(m.manager_lastname) LIKE LOWER(?) "
        + "AND LOWER(m.manager_firstname) LIKE LOWER(?)"
        + "AND m.manager_company_id = c.company_id";

    List<ManagerDTO> managers = new ArrayList<>();

    try (PreparedStatement statement = dalService.preparedStatement(query)) {
      statement.setString(1, manager.getName());
      statement.setString(2, manager.getFirstName());
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          managers.add(rsToManager(rs, "get"));
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return managers;
  }

  /**
   * Adds a manager to the database.
   *
   * @param manager the manager to add
   * @throws FatalException if an error occurs during the operation
   */
  @Override
  public void addManager(ManagerDTO manager) {
    String query = "INSERT INTO pae.managers (manager_lastname, "
        + "manager_firstname, manager_phone_number, manager_email, manager_company_id, "
        + "manager_version) "
        + "VALUES (?,?,?,?,?,1)";

    try (PreparedStatement statement = dalService.preparedStatement(query)) {
      statement.setString(1, manager.getName());
      statement.setString(2, manager.getFirstName());
      statement.setString(3, manager.getPhone());
      statement.setString(4, manager.getEmail());
      statement.setInt(5, manager.getIdCompany());

      int affectedRows = statement.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("Creating manager failed, no rows affected.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException(e);
    }
  }

  /**
   * Converts a ResultSet to a ManagerDTO object.
   *
   * @param rs     the ResultSet to convert
   * @param method the method that called this function
   * @return a ManagerDTO object representing the manager
   * @throws SQLException if an error occurs during the operation
   */
  private ManagerDTO rsToManager(ResultSet rs, String method) throws SQLException {
    CompanyDTO company = dalBackServiceUtils.fillCompanyDTO(rs, method);
    ManagerDTO manager = dalBackServiceUtils.fillManagerDTO(rs, method);

    manager.setCompany(company);
    manager.setIdCompany(company.getId());
    return manager;
  }
}
