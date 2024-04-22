package be.vinci.pae.dal.manager;

import be.vinci.pae.business.manager.ManagerDTO;
import java.util.List;

/**
 * Interface for the ManagerDAO. This interface provides methods to handle data access operations
 * related to Manager.
 */
public interface ManagerDAO {

  /**
   * Retrieves a list of managers for a specific company.
   *
   * @param companyId the ID of the company for which to retrieve managers
   * @return a list of ManagerDTO objects representing the managers of the company
   */
  List<ManagerDTO> getManagers(int companyId);

  /**
   * Retrieves the manager by its email.
   *
   * @param email the email of the manager to retrieve
   * @return a ManagerDTO object representing the manager
   */
  ManagerDTO getManagerByEmail(String email);

  /**
   * Adds a manager to the database.
   *
   * @param manager the manager to add
   */
  void addManager(ManagerDTO manager);


  /**
   * Retrieves the manager by its first name, last name and email.
   *
   * @param manager the manager to retrieve if exists
   * @return a list of ManagerDTO objects representing the managers
   */
  List<ManagerDTO> getManager(ManagerDTO manager);
}
