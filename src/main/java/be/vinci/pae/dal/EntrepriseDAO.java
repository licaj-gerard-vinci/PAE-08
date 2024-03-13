package be.vinci.pae.dal;

import be.vinci.pae.business.EntrepriseDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface EntrepriseDAO {
    List<EntrepriseDTO> getEntreprises();

}
