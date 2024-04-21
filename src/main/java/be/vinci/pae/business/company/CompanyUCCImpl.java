package be.vinci.pae.business.company;

import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.entreprise.CompanyDAO;
import be.vinci.pae.exceptions.ConflictException;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.exceptions.NotFoundException;
import jakarta.inject.Inject;
import java.util.List;

/**
 * This class implements the EntrepriseUCC interface. It provides methods to interact with the
 * EntrepriseDAO to perform operations on the entreprises.
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
  public CompanyDTO getCompanyById(int id) {
    try {
      dalServices.openConnection();
      CompanyDTO company = companyDAO.getCompany(id);
      if (company == null) {
        throw new NotFoundException("L'entreprise avec l'id " + id + " n'existe pas.");
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
        throw new NotFoundException("Aucune entreprise n'a été trouvée.");
      }
      return company;
    } finally {
      dalServices.close();
    }
  }

  /**
   * Updates a company.
   *
   * @param entreprise the company to update.
   */
  @Override
  public void blackListCompany(CompanyDTO entreprise) {
    try {
      dalServices.startTransaction();
      CompanyDTO company = getCompanyById(entreprise.getId());
      if (company == null) {
        throw new NotFoundException("L'entreprise n'a pas pu être trouvée.");
      }
      if (company.isBlackListed()) {
        throw new ConflictException("L'entreprise est déjà blacklistée.");
      }
      company.setBlackListed(true);
      company.setMotivation_blacklist(entreprise.getMotivation_blacklist());
      companyDAO.updateCompany(company);
      dalServices.commitTransaction();
    } catch (FatalException e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }



  /**
   * Adds an entreprise.
   *
   * @param entreprise the entreprise to add.
   */

  public void addCompany(CompanyDTO entreprise) {
    try {
      dalServices.startTransaction();
      CompanyDTO entrepriseFromDb = companyDAO
              .getCompanyByNameDesignation(entreprise.getName(),
          entreprise.getDesignation());
      if (entrepriseFromDb != null) {
        throw new ConflictException("L'entreprise avec le nom " + entreprise.getName()
                  + " et l'appellation " + entreprise.getDesignation() + " existe déjà.");
      }
      companyDAO.addCompany(entreprise);
      dalServices.commitTransaction();
    } catch (ConflictException e) {
      System.out.println(e.getMessage());
    }
  }
}
