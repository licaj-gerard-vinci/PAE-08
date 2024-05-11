package be.vinci.pae.business.company;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.company.CompanyDAO;
import be.vinci.pae.dal.contact.ContactDAO;
import be.vinci.pae.exceptions.ConflictException;
import be.vinci.pae.exceptions.NotFoundException;
import jakarta.inject.Inject;
import java.util.List;

/**
 * This class implements the companyUCC interface. It provides methods to interact with the
 * companyDAO to perform operations on the companies.
 */
public class CompanyUCCImpl implements CompanyUCC {

  @Inject
  private CompanyDAO companyDAO;

  @Inject
  private DALServices dalServices;

  @Inject ContactDAO contactDAO;

  /**
   * Gets the associated company.
   *
   * @param id the id of the company.
   * @return the associated company.
   */
  @Override
  public CompanyDTO getCompanyById(int id) {
    try {
      dalServices.openConnection();
      CompanyDTO company = companyDAO.getCompany(id);
      if (company == null) {
        throw new NotFoundException("The company with the id " + id + " does not exist.");
      }
      return company;
    } finally {
      dalServices.close();
    }
  }

  /**
   * Retrieves all company.
   *
   * @return the list containing all company.
   */
  @Override
  public List<CompanyDTO> getAllCompanies() {
    try {
      dalServices.openConnection();
      List<CompanyDTO> company = companyDAO.getCompany();
      if (company == null) {
        throw new NotFoundException("No company was found.");
      }
      return company;
    } finally {
      dalServices.close();
    }
  }

  /**
   * Updates a company.
   *
   * @param company the company to update.
   */
  @Override
  public void blackListCompany(CompanyDTO company) {
    try {
      dalServices.startTransaction();
      CompanyDTO companyDTO = getCompanyById(company.getId());
      if (companyDTO.isBlackListed()) {
        throw new ConflictException("The company is already blacklisted.");
      }
      companyDTO.setBlackListed(true);
      companyDTO.setMotivation(company.getMotivation());
      companyDAO.updateCompany(companyDTO);
      List<ContactDTO> contacts = contactDAO.getContactsByCompanyId(company.getId());
      for (ContactDTO contact : contacts) {
        if (contact.getContactStatus().equals("pris")
            || contact.getContactStatus().equals("initié")) {
          contact.setContactStatus("blacklisté");
          contactDAO.updateContact(contact);
        }
      }
      dalServices.commitTransaction();
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }



  /**
   * Adds an company.
   *
   * @param company the company to add.
   */

  public void addCompany(CompanyDTO company) {
    try {
      dalServices.startTransaction();
      CompanyDTO companyFromDb = companyDAO
              .getCompanyByNameDesignation(company.getName(),
          company.getDesignation());
      if (companyFromDb != null) {
        throw new ConflictException("The company with the name " + company.getName()
            + " and the designation " + company.getDesignation() + " already exists.");
      }
      companyDAO.addCompany(company);
      dalServices.commitTransaction();
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }
}
