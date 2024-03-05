package be.vinci.pae.business;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = ContactImpl.class)
public interface ContactDTO {

  int getId();

  void setId(int id);

  EntrepriseDTO getEntreprise();

  void setEntreprise(EntrepriseDTO entreprise);

  UserDTO getUtilisateur();

  void setUtilisateur(UserDTO utilisateur);

  String getEtatContact();

  void setEtatContact(String etatContact);

  String getLieuxRencontre();

  void setLieuxRencontre(String lieuxRencontre);

  String getRaisonRefus();

  void setRaisonRefus(String raisonRefus);

  AnneeDTO getAnnee();

  void setAnnee(AnneeDTO annee);


}
