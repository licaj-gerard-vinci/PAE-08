package be.vinci.pae.business;

import java.util.Date;

public interface UserDTO {

  String getNom();

  void setNom(String nom);

  String getPrenom();

  void setPrenom(String prenom);

  String getNumTel();

  void setNumTel(String numTel);

  Date getDateInscription();

  void setDateInscription(Date dateInscription);

  Date getAnneeAcademique();

  void setAnneeAcademique(Date anneeAcademique);

  String getRole();

  void setRole(String role);

  String getEmail();

  void setEmail(String email);

  int getId();

  void setId(int id);

  String getPassword();

  void setPassword(String password);

}
