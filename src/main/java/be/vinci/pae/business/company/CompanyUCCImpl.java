package be.vinci.pae.business.company;

import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.company.CompanyDAO;
import be.vinci.pae.presentation.exceptions.FatalException;
import be.vinci.pae.presentation.exceptions.NotFoundException;
import jakarta.inject.Inject;
import java.util.List;

/**
 * This class implements the companyUCC interface. It provides methods to interact with the
 * CompanyDAO to perform operations on the companies.
 */
public class CompanyUCCImpl implements CompanyUCC {

  @Inject
  private CompanyDAO companyDAO;

  @Inject
  private DALServices dalServices;

  /**
   * Gets the associated company.
   *
   * @param id the id of the company.
   * @return the associated company.
   */
  @Override
  public CompanyDTO getCompany(int id) {
    try {
      dalServices.startTransaction();
      CompanyDTO company = companyDAO.getCompany(id);
      if (company == null) {
        throw new NotFoundException("Company with id " + id + " not found.");
      }
      dalServices.commitTransaction();
      return company;
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.close();
    }
  }

  /**
   * Retrieves all companies.
   *
   * @return the list containing all companies.
   */
  @Override
  public List<CompanyDTO> getCompanies() {
    try {
      dalServices.startTransaction();
      List<CompanyDTO> companies = companyDAO.getCompanies();
      if (companies == null) {
        throw new NotFoundException("No companies found.");
      }
      dalServices.commitTransaction();
      return companies;
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.close();
    }
  }

}
