package be.vinci.pae.dal.manager;

import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.responsable.ResponsableDTO;
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
 * The ManagerDAOImpl class implements the ManagerDAO interface.
 * It provides the data access logic for handling managers.
 */
public class ManagerDAOImpl implements ManagerDAO {

  @Inject
  private DALBackService dalService;

  @Inject
  private DALBackServiceUtils dalBackServiceUtils;

  @Inject
  private Factory factory;

  /**
   * Retrieves a list of managers for a specific company.
   *
   * @param companyId the ID of the company for which to retrieve managers
   * @return a list of ManagerDTO objects representing the managers of the company
   * @throws FatalException if an error occurs during the operation
   */
  @Override
  public List<ResponsableDTO> getManagers(int companyId) {
    String query =
        "SELECT m.*,c.*"
        + " FROM pae.managers m, pae.companies c "
        + "WHERE c.company_id = ? AND m.manager_company_id = c.company_id";


    List<ResponsableDTO> managers = new ArrayList<>();

    try (PreparedStatement statement = dalService.preparedStatement(query)) {
      statement.setInt(1, companyId);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          managers.add(rsToManager(rs, "get"));
        }
      }
      System.out.println(managers);
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return managers;
  }

  /**
   * Converts a ResultSet to a ManagerDTO object.
   *
   * @param rs the ResultSet to convert
   * @param method the method that called this function
   * @return a ManagerDTO object representing the manager
   * @throws SQLException if an error occurs during the operation
   */
  private ResponsableDTO rsToManager(ResultSet rs, String method) throws SQLException {
    EntrepriseDTO entreprise = dalBackServiceUtils.fillEntrepriseDTO(rs, method);
    ResponsableDTO manager = dalBackServiceUtils.fillManagerDTO(rs, method);

    manager.setEntreprise(entreprise);
    manager.setIdEntreprise(entreprise.getId());
    return manager;
  }
}
