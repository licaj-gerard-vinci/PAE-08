package be.vinci.pae.business;

import java.util.List;

public interface EntrepriseUCC {
    /**
     * Retrieves all entreprises.
     *
     * @return the list containing all entreprises.
     */
    List<EntrepriseDTO> getEntreprises();
}
