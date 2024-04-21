package be.vinci.pae.business.manager;


import java.util.List;

/**
 * Interface for the ManagerUCC. This interface provides methods to handle operations related to
 * Manager.
 */
public interface ManagerUCC {

  /**
   * Retrieves a list of managers for a specific company.
   *
   * @param companyId the ID of the company for which to retrieve managers
   * @return a list of ManagerDTO objects representing the managers of the company
   */
  List<ManagerDTO> getManagersByCompanyId(int companyId);

  /**
   * Adds a manager to the database.
   *
   * @param manager the manager to add
   */
  void addManager(ManagerDTO manager);
}
