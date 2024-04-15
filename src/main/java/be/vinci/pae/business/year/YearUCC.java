package be.vinci.pae.business.year;

/**
 * The Interface YearUCC.
 */
public interface YearUCC {

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
}
