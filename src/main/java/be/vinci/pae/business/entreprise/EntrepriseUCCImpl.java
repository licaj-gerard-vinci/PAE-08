package be.vinci.pae.business.entreprise;

import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.entreprise.EntrepriseDAO;

import be.vinci.pae.exceptions.BusinessException;
import be.vinci.pae.exceptions.ConflictException;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.exceptions.NotFoundException;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.exceptions.NotFoundException;
import jakarta.inject.Inject;
import java.util.List;

/**
 * This class implements the EntrepriseUCC interface. It provides methods to interact with the
 * EntrepriseDAO to perform operations on the entreprises.
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
    try {
      dalServices.startTransaction();
      EntrepriseDTO entreprise = entrepriseDAO.getEntreprise(id);
      if (entreprise == null) {
        throw new NotFoundException("L'entreprise avec l'id " + id + " n'existe pas.");
      }
      dalServices.commitTransaction();
      return entreprise;
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    }
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
        throw new NotFoundException("Aucune entreprise n'a été trouvée.");
      }
      dalServices.commitTransaction();
      return entreprises;
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }


  /**
   * Adds an entreprise.
   *
   * @param entreprise the entreprise to add.
   * @return the added entreprise.
   */

  public void addEntreprise(EntrepriseDTO entreprise) {
    try {
      dalServices.startTransaction();
      List<EntrepriseDTO> entreprises = entrepriseDAO.getEntrepriseByNameDesignation(entreprise.getNom(), entreprise.getAppellation());
      if (!entreprises.isEmpty()) {
        throw new ConflictException("L'entreprise avec le nom " + entreprise.getNom() + " et l'appellation " + entreprise.getAppellation() + " existe déjà.");
      }
      entrepriseDAO.addEntreprise(entreprise);
      dalServices.commitTransaction();
    } catch (ConflictException e) {
      System.out.println(e.getMessage()); // Imprime le message d'erreur et continue
    } finally {
      dalServices.close();
    }
  }
}
