package be.vinci.pae.business.internship;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.manager.ManagerDTO;
import be.vinci.pae.business.user.UserDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents the InternshipDTO interface.
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
   * Gets the stage Manager.
   *
   * @return the stage Manager.
   */
  ManagerDTO getManager();

  /**
   * Sets the stage Manager.
   *
   * @param manager the new stage Manager.
   */
  void setManager(ManagerDTO manager);

  /**
   * Gets the stage Manager ID.
   *
   * @return the stage Manager ID.
   */
  int getManagerId();

  /**
   * Sets the stage Manager ID.
   *
   * @param managerId the new stage Manager ID.
   */
  void setManagerId(int managerId);



  /**
   * Gets the stage Student.
   *
   * @return the stage student.
   */
  UserDTO getStudent();

  /**
   * Sets the stage Student.
   *
   * @param student the new stage Student.
   */
  void setStudent(UserDTO student);

  /**
   * Gets the stage Student ID.
   *
   * @return the stage Student ID.
   */
  int getStudentId();

  /**
   * Sets the stage Student ID.
   *
   * @param studentId the new stage Student ID.
   */
  void setStudentId(int studentId);

  /**
   * Gets the stage Contact.
   *
   * @return the stage Contact.
   */
  ContactDTO getContact();

  /**
   * Sets the stage Contact.
   *
   * @param contact the new stage Contact.
   */
  void setContact(ContactDTO contact);

  /**
   * Gets the stage Contact ID.
   *
   * @return the stage Contact ID.
   */
  int getContactId();

  /**
   * Sets the stage Contact ID.
   *
   * @param contactId the new stage Contact ID.
   */
  void setContactId(int contactId);

  /**
   * Gets the stage Company.
   *
   * @return the stage Company.
   */
  CompanyDTO getCompany();

  /**
   * Sets the stage Company.
   *
   * @param company the new stage Company.
   */
  void setCompany(CompanyDTO company);

  /**
   * Gets the stage Company ID.
   *
   * @return the stage Company ID.
   */
  int getCompanyId();

  /**
   * Sets the stage Company ID.
   *
   * @param companyId the new stage Company ID.
   */
  void setCompanyId(int companyId);

  /**
   * Gets the stage Topic.
   *
   * @return the stage Topic.
   */
  String getTopic();

  /**
   * Sets the stage Topic.
   *
   * @param topic the new stage Topic.
   */
  void setTopic(String topic);

  /**
   * Gets the stage date of signature.
   *
   * @return the stage date of signature.
   */
  String getSignatureDate();

  /**
   * Sets the stage date of signature.
   *
   * @param signatureDate the new stage date of signature.
   */
  void setSignatureDate(String signatureDate);

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
