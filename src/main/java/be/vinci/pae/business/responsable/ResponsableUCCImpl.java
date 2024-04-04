package be.vinci.pae.business.responsable;

import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.manager.ManagerDAO;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.exceptions.NotFoundException;
import jakarta.inject.Inject;
import java.util.List;

/**
 * The ManagerUCCImpl class implements the ManagerUCC interface.
 * It provides the business logic for handling managers.
 */
public class ResponsableUCCImpl implements ResponsableUCC {

  @Inject
  private DALServices dalServices;
  @Inject
  private ManagerDAO managerDAO;

  /**
   * Retrieves a list of managers for a specific company.
   *
   * @param companyId the ID of the company for which to retrieve managers
   * @return a list of ManagerDTO objects representing the managers of the company
   * @throws NotFoundException if no managers are found for the company
   * @throws FatalException if an error occurs during the operation
   */
  @Override
  public List<ResponsableDTO> getManagers(int companyId) {
    try {
      dalServices.startTransaction();
      List<ResponsableDTO> manager = managerDAO.getManagers(companyId);
      if (manager == null) {
        throw new NotFoundException("Manager not found");
      }
      dalServices.commitTransaction();
      return manager;
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }
}
