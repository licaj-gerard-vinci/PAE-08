package be.vinci.pae.business.year;

import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.year.YearDAO;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.List;

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
   * Retrieves all years.
   *
   * @return A list of all years.
   */
  @Override
  public List<YearDTO> getAllAcademicYears() {
    try {
      dalServices.openConnection();
      return yearDAO.getAll();
    } finally {
      dalServices.close();
    }
  }

  /**
   * Retrieves a single year by its unique identifier.
   *
   * @param id The unique identifier of the year.
   * @return The {@link YearDTO} instance, or {@code null} if not found.
   */
  @Override
  public YearDTO getYearById(int id) {
    try {
      dalServices.openConnection();
      return yearDAO.getOneById(id);
    } finally {
      dalServices.close();
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
      dalServices.openConnection();
      return yearDAO.getOneByYear(year);
    } finally {
      dalServices.close();
    }
  }

  /**
   * Retrieves the current year.
   *
   * @return The {@link YearDTO} instance representing the current year.
   */
  @Override
  public String renderCurrentYear() {
    LocalDate currentDate = LocalDate.now();
    int currentMonth = currentDate.getMonthValue();

    // Determine the academic year
    String academicYear;
    if (currentMonth < 9) {
      academicYear = (currentDate.getYear() - 1) + "-" + currentDate.getYear();
    } else {
      academicYear = currentDate.getYear() + "-" + (currentDate.getYear() + 1);
    }
    return academicYear;
  }
}
