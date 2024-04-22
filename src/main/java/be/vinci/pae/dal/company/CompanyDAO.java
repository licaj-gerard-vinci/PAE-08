package be.vinci.pae.dal.company;

import be.vinci.pae.business.company.CompanyDTO;
import java.util.List;

/**
 * Represents an interface for accessing entreprise data from the database.
 * This interface provides methods to retrieve entreprise information.
 */
public interface CompanyDAO {

  /**
   * Retrieves an entreprise from the database.
   *
   * @param id The id of the entreprise to retrieve.
   * @return The entreprise with the specified id.
   */
  CompanyDTO getCompany(int id);

  /**
   * Retrieves a list of all entreprises from the database.
   *
   * @return A list containing all entreprises.
   */
  List<CompanyDTO> getCompany();

  /**
   * Updates the entreprise in the database.
   *
   * @param company The entreprise to update.
   */
  void updateCompany(CompanyDTO company);

  /**
   * Retrieves an entreprise from the database.
   *
   * @param name the name of the company to retrieve.
   * @param designation the designation of the company to retrieve.
   * @return Company with the specified name and designation.
   */
  CompanyDTO getCompanyByNameDesignation(String name, String designation);

  /**
     * Inserts a new Company into the database.
     *
     * @param company An EntrepriseDTO object containing the information
     *                 of the entreprise to be inserted.
     */
  void addCompany(CompanyDTO company);



}
