package be.vinci.pae.business.internship;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.manager.ManagerDTO;
import be.vinci.pae.business.user.UserDTO;

/**
 * Represents the StageImpl class.
 */
public class InternshipImpl implements InternshipDTO {

  private int id;

  private ManagerDTO manager;

  private int managerId;

  private UserDTO student;

  private int studentId;

  private ContactDTO contact;

  private int contactId;

  private CompanyDTO company;

  private int companyId;

  private String topic;

  private String signatureDate;

  private int version;

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public ManagerDTO getManager() {
    return manager;
  }

  @Override
  public void setManager(ManagerDTO manager) {
    this.manager = manager;
  }

  @Override
  public int getManagerId() {
    return managerId;
  }

  @Override
  public void setManagerId(int managerId) {
    this.managerId = managerId;
  }

  @Override
  public UserDTO getStudent() {
    return student;
  }

  @Override
  public void setStudent(UserDTO student) {
    this.student = student;
  }

  @Override
  public int getStudentId() {
    return studentId;
  }

  @Override
  public void setStudentId(int studentId) {
    this.studentId = studentId;
  }

  @Override
  public ContactDTO getContact() {
    return contact;
  }

  @Override
  public void setContact(ContactDTO contact) {
    this.contact = contact;
  }

  @Override
  public int getContactId() {
    return contactId;
  }

  @Override
  public void setContactId(int contactId) {
    this.contactId = contactId;
  }

  @Override
  public CompanyDTO getCompany() {
    return company;
  }

  @Override
  public void setCompany(CompanyDTO company) {
    this.company = company;
  }

  @Override
  public int getCompanyId() {
    return companyId;
  }

  @Override
  public void setCompanyId(int companyId) {
    this.companyId = companyId;
  }

  @Override
  public String getTopic() {
    return topic;
  }

  @Override
  public void setTopic(String topic) {
    this.topic = topic;
  }

  /**
   * Gets the stage date of signature.
   *
   * @return the stage date of signature.
   */
  @Override
  public String getSignatureDate() {
    return signatureDate;
  }

  /**
   * Sets the stage date of signature.
   *
   * @param signatureDate the new stage date of signature.
   */
  @Override
  public void setSignatureDate(String signatureDate) {
    this.signatureDate = signatureDate;
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
