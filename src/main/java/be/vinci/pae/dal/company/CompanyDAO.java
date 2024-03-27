package be.vinci.pae.dal.company;

import be.vinci.pae.business.company.CompanyDTO;
import java.util.List;

/**
 * Represents an interface for accessing company data from the database.
 * This interface provides methods to retrieve company information.
 */
public interface CompanyDAO {

  /**
   * Retrieves an company from the database.
   *
   * @param id The id of the company to retrieve.
   * @return The company with the specified id.
   */
  CompanyDTO getCompany(int id);

  /**
   * Retrieves a list of all company from the database.
   *
   * @return A list containing all company.
   */
  List<CompanyDTO> getCompanies();

}
