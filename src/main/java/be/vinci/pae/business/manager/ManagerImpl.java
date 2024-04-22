package be.vinci.pae.business.manager;

import be.vinci.pae.business.company.CompanyDTO;

/**
 * Represents the Manager class.
 */
public class ManagerImpl implements ManagerDTO {

  private int id;
  private String name;
  private String firstName;
  private String phone;
  private String email;
  private CompanyDTO company;
  private int idCompany;
  private int version;

  /**
   * Gets the Manager ID.
   *
   * @return the Manager ID.
   */
  @Override
  public int getId() {
    return id;
  }

  /**
   * Sets the Manager ID.
   *
   * @param id the new Manager ID.
   */
  @Override
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the Manager name.
   *
   * @return the Manager name.
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Sets the Manager name.
   *
   * @param name the new Manager name.
   */
  @Override
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the Manager first name.
   *
   * @return the Manager first name.
   */
  @Override
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets the Manager first name.
   *
   * @param firstName the new Manager first name.
   */
  @Override
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Gets the Manager email.
   *
   * @return the Manager email.
   */
  @Override
  public String getPhone() {
    return phone;
  }

  /**
   * Sets the Manager email.
   *
   * @param phone the new Manager email.
   */
  @Override
  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * Gets the Manager email.
   *
   * @return the Manager email.
   */
  @Override
  public String getEmail() {
    return email;
  }

  /**
   * Sets the Manager email.
   *
   * @param email the new Manager email.
   */
  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets the Manager of the responsable.
   *
   * @return the Manager of the responsable.
   */
  @Override
  public CompanyDTO getCompany() {
    return company;
  }

  /**
   * Sets the company of the Manager.
   *
   * @param company the new company of the Manager.
   */
  @Override
  public void setCompany(CompanyDTO company) {
    this.company = company;
  }

  /**
   * Gets the company ID of the Manager.
   *
   * @return the company ID of the Manager.
   */
  @Override
  public int getIdCompany() {
    return idCompany;
  }

  /**
   * Sets the company ID of the Manager.
   *
   * @param idCompany the new company ID of the Manager.
   */
  @Override
  public void setIdCompany(int idCompany) {
    this.idCompany = idCompany;
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