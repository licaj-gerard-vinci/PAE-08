package be.vinci.pae.dal.company;

import be.vinci.pae.business.company.CompanyDTO;
import java.util.List;

/**
 * Represents an interface for accessing company data from the database.
 * This interface provides methods to retrieve company information.
 */
public interface CompanyDAO {

  /**
   * Retrieves a company from the database.
   *
   * @param id The id of the company to retrieve.
   * @return The company with the specified id.
   */
  CompanyDTO getCompany(int id);

  /**
   * Retrieves a list of all companies from the database.
   *
   * @return A list containing all companies.
   */
  List<CompanyDTO> getCompany();

  /**
   * Updates the company in the database.
   *
   * @param company The company to update.
   */
  void updateCompany(CompanyDTO company);

  /**
   * Retrieves a company from the database.
   *
   * @param name the name of the company to retrieve.
   * @param designation the designation of the company to retrieve.
   * @return Company with the specified name and designation.
   */
  CompanyDTO getCompanyByNameDesignation(String name, String designation);

  /**
     * Inserts a new Company into the database.
     *
     * @param company A company object containing the information
     *                 of the company to be inserted.
     */
  void addCompany(CompanyDTO company);



}
