package be.vinci.pae.business.year;

/**
 * The {@code Year} interface represents a year within the application. It extends the
 * {@code YearDTO} interface, providing additional methods related to year functionality.
 */
public interface Year extends YearDTO {

  /**
   * Retrieves the current year.
   *
   * @return The {@link YearDTO} instance representing the current year.
   */
  String renderCurrentYear();
}
