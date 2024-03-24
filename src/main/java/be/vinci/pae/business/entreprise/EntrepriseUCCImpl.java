package be.vinci.pae.business.entreprise;

import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.entreprise.EntrepriseDAO;
import jakarta.inject.Inject;
import java.util.List;

/**
 * This class implements the EntrepriseUCC interface.
 * It provides methods to interact with the EntrepriseDAO to perform operations on the entreprises.
 */
public class EntrepriseUCCImpl implements EntrepriseUCC {

  @Inject
  private EntrepriseDAO entrepriseDAO;

  @Inject
  private DALServices dalServices;

    /**
     * Gets the associated entreprise.
     *
     * @param id the id of the entreprise.
     * @return the associated entreprise.
     */
    @Override
    public EntrepriseDTO getEntreprise(int id) {
      if(id <= 0) {
        return null; //Il faut retourner une exception ici, pas null
      }
      EntrepriseDTO entreprise = entrepriseDAO.getEntreprise(id);
      if (entreprise == null) {
        return null; //Il faut retourner une exception ici, pas null
      }
      return entreprise;
    }

  /**
   * Retrieves all entreprises.
   *
   * @return the list containing all entreprises.
   */
  @Override
  public List<EntrepriseDTO> getEntreprises() {
    try {
      dalServices.startTransaction();
      List<EntrepriseDTO> entreprises = entrepriseDAO.getEntreprises();
      if (entreprises == null) {
        return null; //Il faut retourner une exception ici, pas null
      }
      dalServices.commitTransaction();
      return entreprises;
    } catch (Exception e) {
      System.out.println("Erreur lors de la récupération des entreprises : " + e.getMessage());
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.close();
    }
  }

}
