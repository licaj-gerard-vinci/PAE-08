package be.vinci.pae.business;

import be.vinci.pae.dal.EntrepriseDAO;
import jakarta.inject.Inject;

import java.util.List;

public class EntrepriseUCCImpl implements EntrepriseUCC {

    @Inject
    private EntrepriseDAO entrepriseDAO;

    /**
     * Retrieves all entreprises.
     *
     * @return the list containing all entreprises.
     */
    @Override
    public List<EntrepriseDTO> getEntreprises() {
        return entrepriseDAO.getEntreprises();
    }

}
