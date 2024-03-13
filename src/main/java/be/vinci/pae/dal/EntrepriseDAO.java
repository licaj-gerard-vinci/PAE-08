package be.vinci.pae.dal;

import be.vinci.pae.business.EntrepriseDTO;
import java.util.List;

/**
 * Represents an interface for accessing entreprise data from the database.
 * This interface provides methods to retrieve entreprise information.
 */
public interface EntrepriseDAO {

  /**
   * Retrieves a list of all entreprises from the database.
   *
   * @return A list containing all entreprises.
   */
  List<EntrepriseDTO> getEntreprises();

}
