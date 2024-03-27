package be.vinci.pae.business.contact;

import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.year.YearDTO;


/**
 * Represents the ContactImpl class.
 */
public class ContactImpl implements ContactDTO {

  private int id;

  private CompanyDTO company;

  private int companyId;

  private UserDTO user;
  private int userId;
  private String contactState;

  private String meetingPlace;

  private String refusalReason;

  private YearDTO year;

  private int version;



  @Override
  public int getCompanyId() {
    return companyId;
  }

  @Override
  public void setCompanyId(int companyId) {
    this.companyId = companyId;
  }

  @Override
  public int getUserId() {
    return userId;
  }

  @Override
  public void setUserId(int userId) {
    this.userId = userId;
  }

  @Override
  public int getId() {
    return id;
  }

  /**
   * Sets the contact ID.
   *
   * @param id the new contact ID.
   */
  @Override
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the associated enterprise.
   *
   * @return the enterprise.
   */
  @Override
  public CompanyDTO getCompany() {
    return company;
  }

  /**
   * Sets the associated enterprise ID.
   *
   * @param company the new enterprise ID.
   */
  @Override
  public void setCompany(CompanyDTO company) {
    this.company = company;
  }

  /**
   * Gets the associated user ID.
   *
   * @return the user ID.
   */
  @Override
  public UserDTO getUser() {
    return user;
  }

  /**
   * Sets the associated user ID.
   *
   * @param user the new user ID.
   */
  @Override
  public void setUser(UserDTO user) {
    this.user = user;
  }

  /**
   * Gets the state of the contact.
   *
   * @return the contact state.
   */
  @Override
  public String getContactState() {
    return contactState;
  }

  /**
   * Sets the state of the contact.
   *
   * @param contactState the new contact state.
   */
  @Override
  public void setContactState(String contactState) {
    this.contactState = contactState;
  }

  /**
   * Gets the meeting location.
   *
   * @return the meeting location.
   */
  @Override
  public String getMeetingPlace() {
    return meetingPlace;
  }

  /**
   * Sets the meeting location.
   *
   * @param meetingPlace the new meeting location.
   */
  @Override
  public void setMeetingPlace(String meetingPlace) {
    this.meetingPlace = meetingPlace;
  }

  /**
   * Gets the reason for refusal.
   *
   * @return the reason for refusal.
   */
  @Override
  public String getRefusalReason() {
    return refusalReason;
  }

  /**
   * Sets the reason for refusal.
   *
   * @param refusalReason the new reason for refusal.
   */
  @Override
  public void setRefusalReason(String refusalReason) {
    this.refusalReason = refusalReason;
  }


  /**
   * Gets the associated year.
   *
   * @return the associated year.
   */
  @Override
  public YearDTO getYear() {
    return year;
  }

  /**
   * Sets the associated year.
   *
   * @param year the new associated year.
   */
  @Override
  public void setYear(YearDTO year) {
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
}