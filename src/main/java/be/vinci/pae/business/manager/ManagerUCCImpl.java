package be.vinci.pae.business.manager;

import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.manager.ManagerDAO;
import be.vinci.pae.exceptions.ConflictException;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.exceptions.NotFoundException;
import jakarta.inject.Inject;
import java.util.List;

/**
 * The ManagerUCCImpl class implements the ManagerUCC interface. It provides the business logic for
 * handling managers.
 */
public class ManagerUCCImpl implements ManagerUCC {

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
   * @throws FatalException    if an error occurs during the operation
   */
  @Override
  public List<ManagerDTO> getManagersByCompanyId(int companyId) {
    try {
      dalServices.openConnection();
      List<ManagerDTO> manager = managerDAO.getManagers(companyId);
      if (manager == null) {
        throw new NotFoundException("Manager not found");
      }
      return manager;
    } finally {
      dalServices.close();
    }
  }

  /**
   * Adds a manager to the database, if it doesn't already exist or if he exists, but he doesn't
   * have an email.
   *
   * @param manager the manager to add
   */
  @Override
  public void addManager(ManagerDTO manager) {
    try {
      dalServices.startTransaction();
      List<ManagerDTO> existingManagers = managerDAO.getManager(manager);
      if (existingManagers != null) {
        for (ManagerDTO existingManager : existingManagers) {
          if (existingManager.getEmail().isEmpty()) {
            throw new ConflictException("A manager with empty email already exists");
          }
          if (existingManager.getEmail().equals(manager.getEmail())) {
            throw new ConflictException("A manager with the same email already exists");
          }
        }
      }
      if (!manager.getEmail().isEmpty()
              && managerDAO.getManagerByEmail(manager.getEmail()) != null) {
        throw new ConflictException("A manager with the same email already exists");
      }
      managerDAO.addManager(manager);
      dalServices.commitTransaction();
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }
}
