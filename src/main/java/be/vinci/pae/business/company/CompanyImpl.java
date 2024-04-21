package be.vinci.pae.business.company;

/**
 * Represents the EntrepriseImpl class.
 */

public class CompanyImpl implements CompanyDTO {

  private int id;

  private String name;

  private String designation;

  private String adresse;

  private String phone;

  private String email;

  private boolean blackListed;

  private String motivation;

  private String city;

  private int version;


  /**
   * Gets the ID of the entreprise.
   *
   * @return the ID of the entreprise.
   */

  @Override
  public int getId() {
    return id;
  }

  /**
   * Sets the ID of the entreprise.
   *
   * @param id the new ID for the entreprise.
   */

  @Override
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the name of the entreprise.
   *
   * @return the name of the entreprise.
   */

  @Override
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the entreprise.
   *
   * @param name the new name for the entreprise.
   */

  @Override
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the designation of the entreprise.
   *
   * @return the designation of the entreprise.
   */

  @Override
  public String getDesignation() {
    return designation;
  }

  /**
   * Sets the designation of the entreprise.
   *
   * @param designation the new apellation for the entreprise.
   */

  @Override
  public void setDesignation(String designation) {
    this.designation = designation;
  }

  /**
   * Gets the adresse of the entreprise.
   *
   * @return the adresse of the entreprise.
   */

  @Override
  public String getAdresse() {
    return adresse;
  }

  /**
   * Sets the adresse of the entreprise.
   *
   * @param adresse the new adresse for the entreprise.
   */

  @Override
  public void setAdresse(String adresse) {
    this.adresse = adresse;
  }


  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  /**
   * Gets the numTel of the entreprise.
   *
   * @return the numTel of the entreprise.
   */

  @Override
  public String getPhone() {
    return phone;
  }

  /**
   * Sets the numTel of the entreprise.
   *
   * @param phone the new numTel for the entreprise.
   */

  @Override
  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * Gets the email of the entreprise.
   *
   * @return the email of the entreprise.
   */

  @Override
  public String getEmail() {
    return email;
  }

  /**
   * Sets the email of the entreprise.
   *
   * @param email the new email for the entreprise.
   */

  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets the blackListed of the entreprise.
   *
   * @return the blackListed of the entreprise.
   */

  @Override
  public boolean isBlackListed() {
    return blackListed;
  }

  /**
   * Sets the blackListed of the entreprise.
   *
   * @param blackListed the new blackListed for the entreprise.
   */

  @Override
  public void setBlackListed(boolean blackListed) {
    this.blackListed = blackListed;
  }

  /**
   * Gets the motivation_blacklist of the entreprise.
   *
   * @return the motivation_blacklist of the entreprise.
   */
  @Override
  public String getMotivation_blacklist() {
    return motivation;
  }


  /**
   * Sets the motivation_blacklist of the entreprise.
   *
   * @param motivation the new motivation_blacklist for the entreprise.
   */

  @Override
  public void setMotivation_blacklist(String motivation) {
    this.motivation = motivation;
  }

  @Override
  public int getVersion() {
    return version;
  }

  @Override
  public void setVersion(int version) {
    this.version = version;
  }
}
