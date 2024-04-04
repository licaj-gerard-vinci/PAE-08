package be.vinci.pae.dal.manager;

import be.vinci.pae.business.responsable.ResponsableDTO;
import java.util.List;

/**
 * Interface for the ManagerDAO.
 * This interface provides methods to handle data access operations related to Manager.
 */
public interface ManagerDAO {

  /**
   * Retrieves a list of managers for a specific company.
   *
   * @param companyId the ID of the company for which to retrieve managers
   * @return a list of ManagerDTO objects representing the managers of the company
   */
  List<ResponsableDTO> getManagers(int companyId);
}
