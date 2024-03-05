package be.vinci.pae.business;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents the EntrepriseDTO interface.
 */
@JsonDeserialize(as = EntrepriseImpl.class)
public interface EntrepriseDTO {

  int getId();

  void setId(int id);

  String getNom();

  void setNom(String nom);

  String getApellation();

  void setApellation(String apellation);

  String getAdresse();

  void setAdresse(String adresse);

  String getNumTel();

  void setNumTel(String numTel);

  String getEmail();

  void setEmail(String email);

  boolean isBlackListed();

  void setBlackListed(boolean blackListed);

  String getMotivation_blacklist();

  void setMotivation_blacklist(String motivation_blacklist);

  
}
