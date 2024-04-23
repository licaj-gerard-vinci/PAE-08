package be.vinci.pae.business.year;

import java.util.List;

/**
 * The Interface YearUCC.
 */
public interface YearUCC {

  /**
   * Retrieves all years.
   *
   * @return A list of all years.
   */
  List<YearDTO> getAllAcademicYears();

  /**
   * Retrieves a single year by its unique identifier.
   *
   * @param id The unique identifier of the year.
   * @return The {@link YearDTO} instance, or {@code null} if not found.
   */
  YearDTO getYearById(int id);

  /**
   * Retrieves a single year by its year.
   *
   * @param year The year of the year.
   * @return The {@link YearDTO} instance, or {@code null} if not found.
   */
  YearDTO getYearByYear(String year);

  /**
   * Retrieves the current year.
   *
   * @return The {@link YearDTO} instance representing the current year.
   */
  String renderCurrentYear();
}
