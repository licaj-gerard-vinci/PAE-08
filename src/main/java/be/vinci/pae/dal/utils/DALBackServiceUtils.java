package be.vinci.pae.dal.utils;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.responsable.ResponsableDTO;
import be.vinci.pae.business.user.UserDTO;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DALBackServiceUtils {



  UserDTO fillUserDTO(ResultSet rs) throws SQLException;

  ContactDTO fillContactDTO(ResultSet rs) throws SQLException;

  ResponsableDTO fillResponsableDTO(ResultSet rs) throws SQLException;

  EntrepriseDTO fillEntrepriseDTO(ResultSet rs) throws SQLException;
}
