package be.vinci.pae.business.year;

import java.time.LocalDate;

/**
 * Represents the yearImpl class.
 */
public class YearImpl implements Year {

  private int id;
  private String year;
  private int version;

  /**
   * Gets the ID of the year.
   *
   * @return the ID of the year.
   */
  @Override
  public int getId() {
    return id;
  }

  /**
   * Sets the ID of the year.
   *
   * @param id the new ID for the year.
   */
  @Override
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the year.
   *
   * @return the year.
   */
  @Override
  public String getYear() {
    return year;
  }

  /**
   * Sets the year.
   *
   * @param year the new year.
   */
  @Override
  public void setYear(String year) {
    this.year = year;
  }

  /**
   * Gets the version.
   *
   * @return the version.
   */
  @Override
  public int getVersion() {
    return version;
  }

  /**
   * Sets the version.
   *
   * @param version the new version.
   */
  @Override
  public void setVersion(int version) {
    this.version = version;
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
