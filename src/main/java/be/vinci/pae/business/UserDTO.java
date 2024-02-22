package be.vinci.pae.business;

import java.util.Date;

public interface UserDTO {

  String getNom();

  void setNom(String nom);

  String getPrenom();

  void setPrenom(String prenom);

  String getNum_tel();

  void setNum_tel(String num_tel);

  Date getDate_inscription();

  void setDate_inscription(Date date_inscription);

  Date getAnnee_academique();

  void setAnnee_academique(Date annee_academique);

  String getRole();

  void setRole(String role);

  String getEmail();

  void setEmail(String email);

  int getId();

  void setId(int id);

  String getPassword();

  void setPassword(String password);

}
