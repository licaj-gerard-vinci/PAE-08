package be.vinci.pae.business.internship;

import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.manager.ManagerDTO;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.year.YearDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.sql.Date;

/**
 * Represents the internshipDTO interface.
 */
@JsonDeserialize(as = InternshipImpl.class)
public interface InternshipDTO {

  /**
   * Gets the internship ID.
   *
   * @return the internship ID.
   */
  int getId();

  /**
   * Sets the internship ID.
   *
   * @param id the new internship ID.
   */
  void setId(int id);

  /**
   * Gets the internship manager.
   *
   * @return the internship manager.
   */
  ManagerDTO getManager();

  /**
   * Sets the internship manager.
   *
   * @param manager the new internship manager.
   */
  void setManager(ManagerDTO manager);

  /**
   * Gets the internship manager ID.
   *
   * @return the internship manager ID.
   */
  int getIdManager();

  /**
   * Sets the internship manager ID.
   *
   * @param idManager the new internship manager ID.
   */
  void setIdManager(int idManager);



  /**
   * Gets the internship student.
   *
   * @return the internship student.
   */
  UserDTO getStudent();

  /**
   * Sets the internship student.
   *
   * @param student the new internship student.
   */
  void setStudent(UserDTO student);

  /**
   * Gets the internship student ID.
   *
   * @return the internship student ID.
   */
  int getIdStudent();

  /**
   * Sets the internship student ID.
   *
   * @param idStudent the new internship student ID.
   */
  void setIdStudent(int idStudent);

  /**
   * Gets the internship Contact.
   *
   * @return the internship Contact.
   */
  ContactDTO getContact();

  /**
   * Sets the internship Contact.
   *
   * @param contact the new internship Contact.
   */
  void setContact(ContactDTO contact);

  /**
   * Gets the internship Contact ID.
   *
   * @return the internship Contact ID.
   */
  int getIdContact();

  /**
   * Sets the internship Contact ID.
   *
   * @param idContact the new internship Contact ID.
   */
  void setIdContact(int idContact);

  /**
   * Gets the internship company.
   *
   * @return the internship company.
   */
  CompanyDTO getCompany();

  /**
   * Sets the internship company.
   *
   * @param company the new internship company.
   */
  void setCompany(CompanyDTO company);

  /**
   * Gets the internship company ID.
   *
   * @return the internship company ID.
   */
  int getIdCompany();

  /**
   * Sets the internship company ID.
   *
   * @param idCompany the new internship company ID.
   */
  void setIdCompany(int idCompany);

  /**
   * Gets the internship topic.
   *
   * @return the internship topic.
   */
  String getTopic();

  /**
   * Sets the internship topic.
   *
   * @param topic the new internship topic.
   */
  void setTopic(String topic);

  /**
   * Gets the internship date.
   *
   * @return the internship date.
   */
  Date getSignatureDate();

  /**
   * Sets the internship date.
   *
   * @param dateSignature the new internship date.
   */
  void setSignatureDate(Date dateSignature);

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


}
