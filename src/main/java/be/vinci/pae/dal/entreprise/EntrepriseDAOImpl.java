package be.vinci.pae.dal.entreprise;

import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.dal.DALBackService;
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
  private Factory factory;

    /**
     * Retrieves an entreprise from the database.
     *
     * @param id the id of the entreprise to retrieve.
     * @return the entreprise with the specified id.
     */
    @Override
    public EntrepriseDTO getEntreprise(int id) {
      String query = "SELECT company_id, company_name, company_designation, "
          + "company_address, company_phone_number FROM pae.companies "
          + "WHERE company_id = ? AND company_is_blacklisted = false";

      try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
        statement.setInt(1, id);
        try (ResultSet rs = statement.executeQuery()) {
          if (rs.next()) {
            return rsToEntreprises(rs);
          }
        }
      } catch (SQLException e) {
        throw new RuntimeException(e);
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

    String query = "SELECT company_id, company_name, company_designation, "
        + "company_address, company_hone_number FROM pae.companies "
        + "WHERE company_is_blacklisted = false";

    List<EntrepriseDTO> entreprises = new ArrayList<>();

    try (PreparedStatement statement = dalBackService.preparedStatement(query);
        ResultSet rs = statement.executeQuery()) {
      while (rs.next()) {
        entreprises.add(rsToEntreprises(rs));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return entreprises;
  }

  private EntrepriseDTO rsToEntreprises(ResultSet rs) throws SQLException {
    EntrepriseDTO entreprise = factory.getEntrepriseDTO();
    entreprise.setId(rs.getInt("company_id"));
    entreprise.setNom(rs.getString("company_name"));
    entreprise.setAppellation(rs.getString("company_designation"));
    entreprise.setAdresse(rs.getString("company_address"));
    entreprise.setNumTel(rs.getString("company_phone_number"));

    return entreprise;
  }
}