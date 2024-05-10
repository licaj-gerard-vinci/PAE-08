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
   * Gets the associated enterprise ID.
   *
   * @return the enterprise ID.
   */
  CompanyDTO getCompany();

  /**
   * Sets the associated enterprise ID.
   *
   * @param company the new enterprise ID.
   */
  void setCompany(CompanyDTO company);

  /**
   * Gets the associated enterprise ID.
   *
   * @return the enterprise ID.
   */
  int getIdCompany();

  /**
   * Sets the associated enterprise ID.
   *
   * @param idCompany the new enterprise ID.
   */
  void setIdCompany(int idCompany);

  /**
   * Gets the associated user ID.
   *
   * @return the user ID.
   */
  UserDTO getStudent();

  /**
   * Sets the associated user ID.
   *
   * @param utilisateur the new user ID.
   */
  void setStudent(UserDTO utilisateur);

  /**
   * Gets the associated user ID.
   *
   * @return the user ID.
   */
  int getIdStudent();

  /**
   * Sets the associated user ID.
   *
   * @param idStudent the new user ID.
   */
  void setIdStudent(int idStudent);

  /**
   * Gets the contact state.
   *
   * @return the contact state.
   */
  String getContactStatus();

  /**
   * Sets the contact state.
   *
   * @param contactStatus the new contact state.
   */
  void setContactStatus(String contactStatus);

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
   * Gets the raison of refuse.
   *
   * @return the raison of refuse.
   */
  String getRefusalReason();

  /**
   * Sets the raison of refuse.
   *
   * @param refusalReason the new raison of refuse.
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
   * Gets the associated year ID.
   *
   * @return the year ID.
   */
  int getIdYear();

  /**
   * Sets the associated year ID.
   *
   * @param idYear the new year ID.
   */
  void setIdYear(int idYear);

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