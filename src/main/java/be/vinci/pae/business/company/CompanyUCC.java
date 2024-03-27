package be.vinci.pae.business.company;

import java.util.List;

/**
 * The CompanyUCC interface provides methods to interact with the CompanyDAO to perform
 * operations on the companies.
 */
public interface CompanyUCC {

  /**
   * Gets the associated company.
   *
   * @param id the id of the company.
   * @return the associated company.
   */
  CompanyDTO getCompany(int id);

  /**
   * Retrieves all companies.
   *
   * @return the list containing all companies.
   */
  List<CompanyDTO> getCompanies();
}