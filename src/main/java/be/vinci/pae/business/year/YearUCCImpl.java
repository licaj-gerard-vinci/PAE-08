package be.vinci.pae.business.year;

import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.year.YearDAO;
import jakarta.inject.Inject;

/**
 * The {@code YearUCCImpl} class provides methods for managing year-related operations, such as
 * retrieving year data. It makes use of dependency injection to obtain a reference to the
 * {@code YearDAO} for accessing year-related data.
 */
public class YearUCCImpl implements YearUCC {

  @Inject
  private DALServices dalServices;
  @Inject
  private YearDAO yearDAO;

  /**
   * Retrieves a single year by its unique identifier.
   *
   * @param id The unique identifier of the year.
   * @return The {@link YearDTO} instance, or {@code null} if not found.
   */
  @Override
  public YearDTO getYearById(int id) {
    try {
      dalServices.startTransaction();
      YearDTO year = yearDAO.getOneById(id);
      dalServices.commitTransaction();
      return year;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  /**
   * Retrieves a single year by its year.
   *
   * @param year The year of the year.
   * @return The {@link YearDTO} instance, or {@code null} if not found.
   */
  @Override
  public YearDTO getYearByYear(String year) {
    try {
      dalServices.startTransaction();
      YearDTO yearToReturn = yearDAO.getOneByYear(year);
      dalServices.commitTransaction();
      return yearToReturn;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }
}
