package be.vinci.pae.dal.entreprise;

import be.vinci.pae.business.entreprise.EntrepriseDTO;
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
public class EntrepriseDAOImpl implements EntrepriseDAO {

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
  public EntrepriseDTO getEntreprise(int id) {
    String query = "SELECT * FROM pae.companies "
        + "WHERE company_id = ?";

    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setInt(1, id);
      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          return rsToEntreprises(rs, "get");
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
  public List<EntrepriseDTO> getEntreprises() {

    String query = "SELECT * FROM pae.companies "
        + "WHERE company_is_blacklisted = false";

    List<EntrepriseDTO> entreprises = new ArrayList<>();

    try (PreparedStatement statement = dalBackService.preparedStatement(query);
        ResultSet rs = statement.executeQuery()) {
      while (rs.next()) {
        entreprises.add(rsToEntreprises(rs, "get"));
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return entreprises;
  }

  /**
   * Updates the entreprise in the database.
   *
   * @param entreprise The entreprise to update.
   * @return The updated entreprise.
   */
  @Override
  public void updateEntreprise(EntrepriseDTO entreprise) {
    String query = "UPDATE pae.companies "
        + "SET company_name = ?, company_address = ?, company_designation = ?, "
        + "company_city = ?, company_phone_number = ?, company_is_blacklisted = ?, "
        + "company_email = ?, company_blacklist_reason = ?, "
        + "company_version = company_version + 1 WHERE company_id = ? AND company_version = ?";
    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setString(1, entreprise.getNom());
      statement.setString(2, entreprise.getAdresse());
      statement.setString(3, entreprise.getAppellation());
      statement.setString(4, entreprise.getCity());
      statement.setString(5, entreprise.getNumTel());
      statement.setBoolean(6, entreprise.isBlackListed());
      statement.setString(7, entreprise.getEmail());
      statement.setString(8, entreprise.getMotivation_blacklist());
      statement.setInt(9, entreprise.getId());
      statement.setInt(10, entreprise.getVersion());
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new FatalException(e);
    }
  }



  private EntrepriseDTO rsToEntreprises(ResultSet rs, String method) throws SQLException {
    return dalBackServiceUtils.fillEntrepriseDTO(rs, method);
  }
}