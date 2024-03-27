package be.vinci.pae.business.company;

/**
 * Represents the EntrepriseImpl class.
 */

public class CompanyImpl implements CompanyDTO {

  private int id;

  private String name;

  private String designation;

  private String address;

  private String phoneNumber;

  private String email;

  private boolean blackListed;

  private String blacklistReason;

  private String city;

  private int version;


  /**
   * Gets the ID of the company.
   *
   * @return the ID of the company.
   */

  @Override
  public int getId() {
    return id;
  }

  /**
   * Sets the ID of the company.
   *
   * @param id the new ID for the company.
   */

  @Override
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the name of the company.
   *
   * @return the name of the company.
   */

  @Override
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the company.
   *
   * @param nom the new name for the company.
   */

  @Override
  public void setName(String nom) {
    this.name = name;
  }

  /**
   * Gets the apellation of the company.
   *
   * @return the apellation of the company.
   */

  @Override
  public String getDesignation() {
    return designation;
  }

  /**
   * Sets the designation of the company.
   *
   * @param designation the new designation for the company.
   */

  @Override
  public void setDesignation(String designation) {
    this.designation = designation;
  }

  /**
   * Gets the address of the company.
   *
   * @return the address of the company.
   */

  @Override
  public String getAddress() {
    return address;
  }

  /**
   * Sets the address of the company.
   *
   * @param address the new address for the company.
   */

  @Override
  public void setAddress(String address) {
    this.address = address;
  }


  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  /**
   * Gets the phoneNumber of the company.
   *
   * @return the phoneNumber of the company.
   */

  @Override
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * Sets the phoneNumber of the company.
   *
   * @param phoneNumber the new phoneNumber for the company.
   */

  @Override
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * Gets the email of the company.
   *
   * @return the email of the company.
   */

  @Override
  public String getEmail() {
    return email;
  }

  /**
   * Sets the email of the company.
   *
   * @param email the new email for the company.
   */

  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets the blackListed of the company.
   *
   * @return the blackListed of the company.
   */

  @Override
  public boolean isBlackListed() {
    return blackListed;
  }

  /**
   * Sets the blackListed of the company.
   *
   * @param blackListed the new blackListed for the company.
   */

  @Override
  public void setBlackListed(boolean blackListed) {
    this.blackListed = blackListed;
  }

  /**
   * Gets the blacklistReason of the company.
   *
   * @return the blacklistReason of the company.
   */
  @Override
  public String getBlacklistReason() {
    return blacklistReason;
  }


  /**
   * Sets the blacklistReason of the company.
   *
   * @param blacklistReason the new blacklistReason for the company.
   */

  @Override
  public void setBlacklistReason(String blacklistReason) {
    this.blacklistReason = blacklistReason;
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
