package be.vinci.pae.dal;

import be.vinci.pae.business.EntrepriseDTO;
import be.vinci.pae.business.Factory;
import jakarta.inject.Inject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        String query = "SELECT ent.nom, ent.appellation," + " ent.adress, ent.numero_tel FROM pae.entreprises AS ent";

        List<EntrepriseDTO> entreprises = new ArrayList<>();

        try (PreparedStatement statement = dalService.preparedStatement(query); ResultSet rs = statement.executeQuery()) {
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
        entreprise.setNom(rs.getString("nom"));
        entreprise.setAppellation(rs.getString("appellation"));
        entreprise.setAdresse(rs.getString("adress"));
        entreprise.setNumTel(rs.getString("numero_tel"));
        return entreprise;
    }
}