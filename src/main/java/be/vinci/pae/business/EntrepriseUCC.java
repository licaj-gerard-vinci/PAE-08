package be.vinci.pae.business;

import java.util.List;

/**
 * The EntrepriseUCC interface provides methods to interact with the EntrepriseDAO to perform operations on the entreprises.
 */
public interface EntrepriseUCC {
    /**
     * Retrieves all entreprises.
     *
     * @return the list containing all entreprises.
     */
    List<EntrepriseDTO> getEntreprises();
}
