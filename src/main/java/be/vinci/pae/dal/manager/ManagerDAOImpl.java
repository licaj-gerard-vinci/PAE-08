package be.vinci.pae.dal.manager;

import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.factory.Factory;
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

public class ManagerDAOImpl implements ManagerDAO {

    @Inject
    private DALBackService dalService;

    @Inject
    private DALBackServiceUtils dalBackServiceUtils;

    @Inject
    private Factory factory;

    @Override
    public List<ManagerDTO> getManagers(int companyId) {
        String query =
                "SELECT m.*,c.*"
                        + " FROM pae.managers m, pae.companies c "
                        + "WHERE c.company_id = ? AND m.manager_company_id = c.company_id";


        List<ManagerDTO> managers = new ArrayList<>();

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

    private ManagerDTO rsToManager(ResultSet rs, String method) throws SQLException {
        EntrepriseDTO entreprise = dalBackServiceUtils.fillEntrepriseDTO(rs, method);
        ManagerDTO manager = dalBackServiceUtils.fillManagerDTO(rs, method);

        manager.setEntreprise(entreprise);
        manager.setIdEntreprise(entreprise.getId());
        return manager;
    }
}
