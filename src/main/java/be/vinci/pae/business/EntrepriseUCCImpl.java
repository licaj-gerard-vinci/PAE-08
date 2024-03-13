package be.vinci.pae.business;

import be.vinci.pae.dal.EntrepriseDAO;
import jakarta.inject.Inject;

import java.util.List;

/**
 * This class implements the EntrepriseUCC interface.
 * It provides methods to interact with the EntrepriseDAO to perform operations on the entreprises.
 */
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
