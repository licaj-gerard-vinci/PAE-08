package be.vinci.pae.business.contact;

import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.year.YearDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents the ContactDTO interface.
 */
@JsonDeserialize(as = ContactImpl.class)
public interface ContactDTO {


  /**
   * Gets the contact ID.
   *
   * @return the contact ID.
   */
  int getId();

  /**
   * Sets the contact ID.
   *
   * @param id the new contact ID.
   */
  void setId(int id);

  /**
   * Gets the associated company ID.
   *
   * @return the company ID.
   */
  CompanyDTO getCompany();

  /**
   * Sets the associated company ID.
   *
   * @param company the new company ID.
   */
  void setCompany(CompanyDTO company);

  /**
   * Gets the associated company ID.
   *
   * @return the company ID.
   */
  int getCompanyId();

  /**
   * Sets the associated company ID.
   *
   * @param companyId the new company ID.
   */
  void setCompanyId(int companyId);

  /**
   * Gets the associated user ID.
   *
   * @return the user ID.
   */
  UserDTO getUser();

  /**
   * Sets the associated user ID.
   *
   * @param user the new user ID.
   */
  void setUser(UserDTO user);

  /**
   * Gets the associated user ID.
   *
   * @return the user ID.
   */
  int getUserId();

  /**
   * Sets the associated user ID.
   *
   * @param userId the new user ID.
   */
  void setUserId(int userId);

  /**
   * Gets the contact state.
   *
   * @return the contact state.
   */
  String getContactState();

  /**
   * Sets the contact state.
   *
   * @param contactState the new contact state.
   */
  void setContactState(String contactState);

  /**
   * Gets the meeting place.
   *
   * @return the meeting place.
   */
  String getMeetingPlace();

  /**
   * Sets the meeting place.
   *
   * @param meetingPlace the new meeting place.
   */
  void setMeetingPlace(String meetingPlace);

  /**
   * Gets the refusal reason.
   *
   * @return the refusal reason.
   */
  String getRefusalReason();

  /**
   * Sets the refusal reason.
   *
   * @param refusalReason the new refusal reason.
   */
  void setRefusalReason(String refusalReason);

  /**
   * Gets the meeting date.
   *
   * @return the meeting date.
   */
  YearDTO getYear();

  /**
   * Sets the meeting date.
   *
   * @param year the new meeting date.
   */
  void setYear(YearDTO year);

  /**
   * Gets the version.
   *
   * @return the version.
   */
  int getVersion();

  /**
   * Sets the version.
   *
   * @param version the new version.
   */
  void setVersion(int version);





}