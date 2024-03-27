package be.vinci.pae.dal.company;

import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.dal.DALBackService;
import be.vinci.pae.dal.utils.DALBackServiceUtils;
import be.vinci.pae.presentation.exceptions.FatalException;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Company interface represents a business entity. It extends the CompanyDTO interface and
 * provides methods to get and set the properties of an company.
 */
public class CompanyDAOImpl implements CompanyDAO {

  @Inject
  private DALBackService dalBackService;

  @Inject
  private DALBackServiceUtils dalBackServiceUtils;

  /**
   * Retrieves an company from the database.
   *
   * @param id the id of the company to retrieve.
   * @return the company with the specified id.
   */
  @Override
  public CompanyDTO getCompany(int id) {
    String query = "SELECT * FROM pae.companies "
        + "WHERE company_id = ? AND company_is_blacklisted = false";

    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setInt(1, id);
      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          return rsToCompanies(rs, "get");
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return null;
  }

  /**
   * Retrieves all companies from the database.
   *
   * @return a list of all companies.
   */
  @Override
  public List<CompanyDTO> getCompanies() {

    String query = "SELECT * FROM pae.companies "
        + "WHERE company_is_blacklisted = false";

    List<CompanyDTO> companies = new ArrayList<>();

    try (PreparedStatement statement = dalBackService.preparedStatement(query);
        ResultSet rs = statement.executeQuery()) {
      while (rs.next()) {
        companies.add(rsToCompanies(rs, "get"));
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return companies;
  }

  private CompanyDTO rsToCompanies(ResultSet rs, String method) throws SQLException {
    return dalBackServiceUtils.fillCompanyDTO(rs, method);
  }
}