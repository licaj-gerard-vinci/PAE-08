package be.vinci.pae.business.manager;

import be.vinci.pae.business.company.CompanyDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents the ResponsableDTO interface.
 */
@JsonDeserialize(as = ManagerImpl.class)
public interface ManagerDTO {

  /**
   * Gets the responsable ID.
   *
   * @return the responsable ID.
   */
  int getId();

  /**
   * Sets the responsable ID.
   *
   * @param id the new responsable ID.
   */
  void setId(int id);

  /**
   * Gets the responsable name.
   *
   * @return the responsable name.
   */
  String getName();

  /**
   * Sets the responsable name.
   *
   * @param name the new responsable name.
   */

  void setName(String name);

  /**
   * Gets the responsable first name.
   *
   * @return the responsable first name.
   */
  String getFirstName();

  /**
   * Sets the responsable first name.
   *
   * @param firstName the new responsable first name.
   */
  void setFirstName(String firstName);

  /**
   * Gets the responsable email.
   *
   * @return the responsable email.
   */
  String getPhone();

  /**
   * Sets the responsable email.
   *
   * @param phone the new responsable email.
   */
  void setPhone(String phone);

  /**
   * Gets the responsable email.
   *
   * @return the responsable email.
   */
  String getEmail();

  /**
   * Sets the responsable email.
   *
   * @param email the new responsable email.
   */
  void setEmail(String email);


  /**
   * Gets the entreprise.
   *
   * @return the entreprise.
   */
  CompanyDTO getCompany();

  /**
   * Sets the entreprise.
   *
   * @param company the new entreprise.
   */
  void setCompany(CompanyDTO company);

  /**
   * Gets the entreprise ID.
   *
   * @return the entreprise ID.
   */
  int getIdCompany();

  /**
   * Sets the entreprise ID.
   *
   * @param idCompany the new entreprise ID.
   */
  void setIdCompany(int idCompany);

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