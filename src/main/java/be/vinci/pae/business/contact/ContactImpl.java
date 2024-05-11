package be.vinci.pae.business.contact;

import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.year.YearDTO;


/**
 * Represents the ContactImpl class.
 */
public class ContactImpl implements Contact {

  private int id;

  private CompanyDTO company;

  private int idCompany;

  private UserDTO student;

  private int idStudent;

  private String contactStatus;

  private String meetingPlace;

  private String refusalReason;

  private YearDTO year;

  private int idYear;

  private int version;




  @Override
  public int getIdCompany() {
    return idCompany;
  }

  @Override
  public void setIdCompany(int idCompany) {
    this.idCompany = idCompany;
  }

  @Override
  public int getIdStudent() {
    return idStudent;
  }

  @Override
  public void setIdStudent(int idStudent) {
    this.idStudent = idStudent;
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
   * Gets the associated company.
   *
   * @return the company.
   */
  @Override
  public CompanyDTO getCompany() {
    return company;
  }

  /**
   * Sets the associated company ID.
   *
   * @param company the new company ID.
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
  public UserDTO getStudent() {
    return student;
  }

  /**
   * Sets the associated user ID.
   *
   * @param student the new user ID.
   */
  @Override
  public void setStudent(UserDTO student) {
    this.student = student;
  }

  /**
   * Gets the state of the contact.
   *
   * @return the contact state.
   */
  @Override
  public String getContactStatus() {
    return contactStatus;
  }

  /**
   * Sets the state of the contact.
   *
   * @param contactStatus the new contact state.
   */
  @Override
  public void setContactStatus(String contactStatus) {
    this.contactStatus = contactStatus;
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
   * Gets the associated year ID.
   *
   * @return the associated year ID.
   */
  @Override
  public int getIdYear() {
    return idYear;
  }

  /**
   * Sets the associated year ID.
   *
   * @param idYear the new associated year ID.
   */
  @Override
  public void setIdYear(int idYear) {
    this.idYear = idYear;
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
   * Check if a contact can be updated to the 'taken' state.
   *
   * @param actualState The actuel.
   * @param expectedState The expected state of the contact.
   * @return true if the contact can be updated, false otherwise.
   */
  @Override
  public boolean checkState(String actualState, String expectedState) {

    if (actualState.equals("pris") && !expectedState.equals("accepté")
        && !expectedState.equals("refusé") && !expectedState.equals("non suivi")
        && !expectedState.equals("blacklisté") && !expectedState.equals("suspendu")) {
      return false;
    }

    if (actualState.equals("initié") && !expectedState.equals("pris")
        && !expectedState.equals("non suivi") && !expectedState.equals("blacklisté")
        && !expectedState.equals("suspendu")) {
      return false;
    }

    if (actualState.equals("non suivi") || actualState.equals("refusé")
        || actualState.equals("accepté") || actualState.equals("suspendu")) {
      return false;
    }

    return !expectedState.equals("initié");
  }

}