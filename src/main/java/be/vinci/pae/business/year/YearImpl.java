package be.vinci.pae.business.year;

/**
 * Represents the AnneeImpl class.
 */
public class YearImpl implements YearDTO {

  private int id;
  private String annee;
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
  public String getAnnee() {
    return annee;
  }

  /**
   * Sets the year.
   *
   * @param annee the new year.
   */
  @Override
  public void setAnnee(String annee) {
    this.annee = annee;
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
}
