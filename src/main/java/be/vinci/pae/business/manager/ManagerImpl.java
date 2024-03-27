package be.vinci.pae.business.manager;

import be.vinci.pae.business.company.CompanyDTO;

/**
 * Represents the ResponsableImpl class.
 */
public class ManagerImpl implements ManagerDTO {

  private int id;
  private String lastname;
  private String firstname;
  private String phoneNumber;
  private String email;
  private CompanyDTO company;
  private int companyId;
  private int version;

  /**
   * Gets the manager ID.
   *
   * @return the manager ID.
   */
  @Override
  public int getId() {
    return id;
  }

  /**
   * Sets the manager ID.
   *
   * @param id the new manager ID.
   */
  @Override
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the manager name.
   *
   * @return the manager name.
   */
  @Override
  public String getLastname() {
    return lastname;
  }

  /**
   * Sets the manager name.
   *
   * @param lastname the new manager name.
   */
  @Override
  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  /**
   * Gets the manager first name.
   *
   * @return the manager first name.
   */
  @Override
  public String getFirstname() {
    return firstname;
  }

  /**
   * Sets the manager first name.
   *
   * @param firstname the new manager first name.
   */
  @Override
  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  /**
   * Gets the manager email.
   *
   * @return the manager email.
   */
  @Override
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * Sets the manager email.
   *
   * @param phoneNumber the new manager email.
   */
  @Override
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * Gets the manager email.
   *
   * @return the manager email.
   */
  @Override
  public String getEmail() {
    return email;
  }

  /**
   * Sets the manager email.
   *
   * @param email the new manager email.
   */
  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets the company of the manager.
   *
   * @return the company of the manager.
   */
  @Override
  public CompanyDTO getCompany() {
    return company;
  }

  /**
   * Sets the company of the manager.
   *
   * @param company the new company of the manager.
   */
  @Override
  public void setCompany(CompanyDTO company) {
    this.company = company;
  }

  /**
   * Gets the company ID of the manager.
   *
   * @return the company ID of the manager.
   */
  @Override
  public int getCompanyId() {
    return companyId;
  }

  /**
   * Sets the company ID of the manager.
   *
   * @param companyId the new company ID of the manager.
   */
  @Override
  public void setCompanyId(int companyId) {
    this.companyId = companyId;
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