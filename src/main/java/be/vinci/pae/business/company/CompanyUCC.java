package be.vinci.pae.business.company;

import java.util.List;

/**
 * The companyUCC interface provides methods to interact with the companyDAO to perform
 * operations on the companies.
 */
public interface CompanyUCC {

  /**
   * Gets the associated company.
   *
   * @param id the id of the company.
   * @return the associated company.
   */
  CompanyDTO getCompanyById(int id);

  /**
   * Retrieves all companies.
   *
   * @return the list containing all companies.
   */
  List<CompanyDTO> getAllCompanies();

  /**
   * Adds a Company.
   *
   * @param company the company to add.
   */
  void addCompany(CompanyDTO company);

  /**
   * Updates the company.
   *
   * @param company the company to update.
   */
  void blackListCompany(CompanyDTO company);
}