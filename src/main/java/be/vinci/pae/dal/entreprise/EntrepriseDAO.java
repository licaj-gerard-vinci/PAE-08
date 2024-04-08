package be.vinci.pae.dal.entreprise;

import be.vinci.pae.business.entreprise.EntrepriseDTO;
import java.util.List;

/**
 * Represents an interface for accessing entreprise data from the database.
 * This interface provides methods to retrieve entreprise information.
 */
public interface EntrepriseDAO {

  /**
   * Retrieves an entreprise from the database.
   *
   * @param id The id of the entreprise to retrieve.
   * @return The entreprise with the specified id.
   */
  EntrepriseDTO getEntreprise(int id);

  /**
   * Retrieves a list of all entreprises from the database.
   *
   * @return A list containing all entreprises.
   */
  List<EntrepriseDTO> getEntreprises();

    /**
     * Retrieves an entreprise from the database.
     * @param name the name of the company to retrieve.
     * @param designation the designation of the company to retrieve.
     * @return Entreprise with the specified name and designation.
     */
  List<EntrepriseDTO> getEntrepriseByNameDesignation(String name, String designation);

/**
   * Inserts a new entreprise into the database.
   *
   * @param entreprise An EntrepriseDTO object containing the information of the entreprise to be
   *        inserted.
   */
    void addEntreprise(EntrepriseDTO entreprise);




}
