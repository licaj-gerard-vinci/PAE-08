package be.vinci.pae.dal;

import be.vinci.pae.business.EntrepriseDTO;
import be.vinci.pae.business.Factory;
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
  private DALService dalService;
  @Inject
  private Factory factory;

  /**
   * Retrieves all entreprises from the database.
   *
   * @return a list of all entreprises.
   */
  @Override
  public List<EntrepriseDTO> getEntreprises() {

    String query = "SELECT company_id,name, designation, "
        + "address, phone_number FROM pae.companies " +
            "WHERE is_blacklisted = false";

    List<EntrepriseDTO> entreprises = new ArrayList<>();

    try (PreparedStatement statement = dalService.preparedStatement(query);
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
    entreprise.setNom(rs.getString("name"));
    entreprise.setAppellation(rs.getString("designation"));
    entreprise.setAdresse(rs.getString("address"));
    entreprise.setNumTel(rs.getString("phone_number"));

    return entreprise;
  }
}