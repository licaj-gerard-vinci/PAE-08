package be.vinci.pae.business.entreprise;

import java.util.List;

/**
 * The EntrepriseUCC interface provides methods to interact with the EntrepriseDAO to perform
 * operations on the entreprises.
 */
public interface EntrepriseUCC {

  /**
   * Gets the associated entreprise.
   *
   * @param id the id of the entreprise.
   * @return the associated entreprise.
   */
  EntrepriseDTO getEntreprise(int id);

  /**
   * Retrieves all entreprises.
   *
   * @return the list containing all entreprises.
   */
  List<EntrepriseDTO> getEntreprises();

  /**
   * Updates the entreprise.
   *
   * @param entreprise the entreprise to update.
   */
  void blackListCompany(EntrepriseDTO entreprise);
}