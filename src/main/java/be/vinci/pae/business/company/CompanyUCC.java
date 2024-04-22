package be.vinci.pae.business.company;

import java.util.List;

/**
 * The EntrepriseUCC interface provides methods to interact with the EntrepriseDAO to perform
 * operations on the entreprises.
 */
public interface CompanyUCC {

  /**
   * Gets the associated entreprise.
   *
   * @param id the id of the entreprise.
   * @return the associated entreprise.
   */
  CompanyDTO getCompanyById(int id);

  /**
   * Retrieves all entreprises.
   *
   * @return the list containing all entreprises.
   */
  List<CompanyDTO> getAllCompanies();

  /**
   * Adds a Company.
   *
   * @param company the entreprise to add.
   */
  void addCompany(CompanyDTO company);

  /**
   * Updates the entreprise.
   *
   * @param company the entreprise to update.
   */
  void blackListCompany(CompanyDTO company);
}