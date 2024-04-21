package be.vinci.pae.dal.company;

import be.vinci.pae.business.company.CompanyDTO;
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
 * The Entreprise interface represents a business entity. It extends the EntrepriseDTO interface and
 * provides methods to get and set the properties of an entreprise.
 */
public class CompanyDAOImpl implements CompanyDAO {

  @Inject
  private DALBackService dalBackService;

  @Inject
  private DALBackServiceUtils dalBackServiceUtils;

  /**
  * Retrieves an entreprise from the database.
  *
  * @param id the id of the entreprise to retrieve.
  * @return the entreprise with the specified id.
  */
  @Override
  public CompanyDTO getCompany(int id) {
    String query = "SELECT * FROM pae.companies "
        + "WHERE company_id = ?";

    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setInt(1, id);
      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          return rsToCompany(rs, "get");
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return null;
  }

  /**
  * Retrieves an entreprise from the database.
  *
  * @param name and designation of the company to retrieve.
  * @return the entreprise with the specified name and designation.
  */
  @Override
  public CompanyDTO getCompanyByNameDesignation(String name, String designation) {
    String query = "SELECT * FROM pae.companies "
            + "WHERE LOWER(company_name) LIKE LOWER(?) AND "
            + "LOWER(company_designation) LIKE LOWER(?)";
    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setString(1, name);
      statement.setString(2, designation);
      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          return rsToCompany(rs, "get");
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return null;
  }

  /**
   * Retrieves all entreprises from the database.
   *
   * @return a list of all entreprises.
   */
  @Override
  public List<CompanyDTO> getCompany() {

    String query = "SELECT * FROM pae.companies";

    List<CompanyDTO> Company = new ArrayList<>();

    try (PreparedStatement statement = dalBackService.preparedStatement(query);
        ResultSet rs = statement.executeQuery()) {
      while (rs.next()) {
        Company.add(rsToCompany(rs, "get"));
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return Company;
  }
  /**
   * Adds a Company to the database.
   *
   * @param Company the entreprise to add.
   */

  public void addCompany(CompanyDTO Company) {
    String query = "INSERT INTO pae.companies "
        + "(company_name, company_designation, company_address,company_city, "
        + "company_phone_number, company_email, "
        + "company_is_blacklisted,company_blacklist_reason,company_version) "
        + "VALUES (?, ?, ?, ?, ?, ?,False,?,1) RETURNING company_id";

    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setString(1, Company.getName());
      statement.setString(2, Company.getDesignation());
      statement.setString(3, Company.getAdresse());
      statement.setString(4, Company.getCity());
      statement.setString(5, Company.getPhone());
      statement.setString(6, Company.getEmail());
      statement.setString(7, Company.getMotivation());

      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          Company.setId(rs.getInt(1));
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }

  }

  /**
   * Updates the Company in the database.
   *
   * @param Company The entreprise to update.
   */
  @Override
    public void updateCompany(CompanyDTO Company) {
    String query = "UPDATE pae.companies "
                + "SET company_name = ?, company_address = ?, company_designation = ?, "
                + "company_city = ?, company_phone_number = ?, company_is_blacklisted = ?, "
                + "company_email = ?, company_blacklist_reason = ?, "
                + "company_version = company_version + 1 WHERE company_id = ? "
                + "AND company_version = ?";
    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setString(1, Company.getName());
      statement.setString(2, Company.getAdresse());
      statement.setString(3, Company.getDesignation());
      statement.setString(4, Company.getCity());
      statement.setString(5, Company.getPhone());
      statement.setBoolean(6, Company.isBlackListed());
      statement.setString(7, Company.getEmail());
      statement.setString(8, Company.getMotivation());
      statement.setInt(9, Company.getId());
      statement.setInt(10, Company.getVersion());
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new FatalException(e);
    }
  }

  private CompanyDTO rsToCompany(ResultSet rs, String method) throws SQLException {
    return dalBackServiceUtils.fillCompanyDTO(rs, method);
  }
}