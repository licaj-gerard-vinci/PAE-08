package be.vinci.pae.business.internship;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.manager.ManagerDTO;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.year.YearDTO;
import java.sql.Date;

/**
 * Represents the StageImpl class.
 */
public class InternshipImpl implements InternshipDTO {

  private int id;

  private ManagerDTO manager;

  private int idManager;

  private UserDTO student;

  private int idStudent;

  private ContactDTO contact;

  private int idContact;

  private CompanyDTO company;

  private int idCompany;

  private String topics;

  private Date signatureDate;

  private int version;

  private YearDTO annee;

  private int idAnnee;

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
  public int getIdManager() {
    return idManager;
  }

  @Override
  public void setIdManager(int idManager) {
    this.idManager = idManager;
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
  public int getIdStudent() {
    return idStudent;
  }

  @Override
  public void setIdStudent(int idStudent) {
    this.idStudent = idStudent;
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
  public int getIdContact() {
    return idContact;
  }

  @Override
  public void setIdContact(int idContact) {
    this.idContact = idContact;
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
  public int getIdCompany() {
    return idCompany;
  }

  @Override
  public void setIdCompany(int idCompany) {
    this.idCompany = idCompany;
  }

  @Override
  public String getTopics() {
    return topics;
  }

  @Override
  public void setTopics(String topics) {
    this.topics = topics;
  }

  /**
   * Gets the stage date.
   *
   * @return the stage date.
   */
  @Override
  public Date getdateSignature() {
    return signatureDate;
  }

  /**
   * Sets the stage date.
   *
   * @param dateSignature the new stage date.
   */
  @Override
  public void setdateSignature(Date dateSignature) {
    this.signatureDate = dateSignature;
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

  @Override
  public YearDTO getAnnee() {
    return annee;
  }

  @Override
  public void setAnnee(YearDTO annee) {
    this.annee = annee;
  }

  @Override
  public int getIdAnnee() {
    return idAnnee;
  }
  
  @Override
  public void setIdAnnee(int idAnnee) {
    this.idAnnee = idAnnee;
  }

}
