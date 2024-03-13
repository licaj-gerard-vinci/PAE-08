package be.vinci.pae.dal;

import be.vinci.pae.business.EntrepriseDTO;
import java.util.List;

/**
 * Retrieves all entreprises from the database.
 *
 * @return a list of all entreprises.
 */
public interface EntrepriseDAO {
  List<EntrepriseDTO> getEntreprises();

}
