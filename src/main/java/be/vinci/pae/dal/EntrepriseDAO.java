package be.vinci.pae.dal;

import be.vinci.pae.business.EntrepriseDTO;

import java.util.List;

public interface EntrepriseDAO {
    List<EntrepriseDTO> getEntreprises();

}
