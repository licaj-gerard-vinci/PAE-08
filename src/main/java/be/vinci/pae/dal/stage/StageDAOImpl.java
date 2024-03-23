package be.vinci.pae.dal.stage;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.responsable.ResponsableDTO;
import be.vinci.pae.business.stage.StageDTO;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.year.YearDTO;
import be.vinci.pae.dal.DALBackService;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class StageDAOImpl.
 */
public class StageDAOImpl implements StageDAO {


  @Inject
  private DALBackService dalBackService;

  @Inject
  private Factory factory;

  /**
   * Gets all stage.
   *
   * @return all stages
   */
  @Override
  public List<StageDTO> getStages() {
    List<StageDTO> stages = new ArrayList<>();
    String query = "SELECT int.*, man.*, use.*, con.*, com.*, sch.* "
        + "FROM pae.internships int "
        + "INNER JOIN pae.managers man ON int.internship_manager_id = man.manager_id "
        + "INNER JOIN pae.users use ON int.internship_student_id = use.user_id "
        + "INNER JOIN pae.contacts con ON int.internship_contact_id = con.contact_id "
        + "INNER JOIN pae.companies com ON int.internship_company_id = com.company_id "
        + "INNER JOIN pae.school_years sch ON int.internship_school_year_id = sch.school_year_id";

    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          stages.add(rsToStage(rs));
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return stages;
  }

  /**
   * Gets the stage by id.
   *
   * @param id the id
   * @return the stage by id
   */
  @Override
  public StageDTO getStageById(int id) {
    String query = "SELECT int.*, man.*, use.*, con.*, com.*, sch.* "
        + "FROM pae.internships int "
        + "INNER JOIN pae.managers man ON int.internship_manager_id = man.manager_id "
        + "INNER JOIN pae.users use ON int.internship_student_id = use.user_id "
        + "INNER JOIN pae.contacts con ON int.internship_contact_id = con.contact_id "
        + "INNER JOIN pae.companies com ON int.internship_company_id = com.company_id "
        + "INNER JOIN pae.school_years sch ON int.internship_school_year_id = sch.school_year_id "
        + "WHERE int.internship_id = ?";

    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setInt(1, id);
      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          return rsToStage(rs);
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }


  /**
   * Rs to stage.
   *
   * @param rs the rs
   * @return the stage DTO
   * @throws SQLException the SQL exception
   */

  public StageDTO rsToStage(ResultSet rs) throws SQLException {

    StageDTO stage = factory.getStageDTO();
    ResponsableDTO responsable = factory.getResponsableDTO();
    UserDTO etudiant = factory.getPublicUser();
    ContactDTO contact = factory.getContactDTO();
    EntrepriseDTO entreprise = factory.getEntrepriseDTO();
    YearDTO year = factory.getYearDTO();

    //Add year info
    year.setId(rs.getInt("school_year_id"));
    year.setAnnee(rs.getString("year"));

    //Add manager info
    responsable.setId(rs.getInt("manager_id"));
    responsable.setNom(rs.getString("manager_lastname"));
    responsable.setPrenom(rs.getString("manager_firstname"));
    responsable.setNumTel(rs.getString("manager_phone_number"));
    responsable.setEmail(rs.getString("manager_email"));
    responsable.setIdEntreprise(rs.getInt("manager_company_id"));

    //Add student info
    etudiant.setId(rs.getInt("user_id"));
    etudiant.setEmail(rs.getString("user_email"));
    etudiant.setLastname(rs.getString("user_lastname"));
    etudiant.setFirstname(rs.getString("user_firstname"));
    etudiant.setPhone(rs.getString("user_phone_number"));
    etudiant.setPassword(rs.getString("user_password"));
    etudiant.setRegistrationDate(rs.getDate("user_registration_date"));
    etudiant.setRole(rs.getString("user_role"));
    etudiant.setYear(year);
    etudiant.setHasInternship(rs.getBoolean("user_has_internship"));

    //Add contact info
    contact.setId(rs.getInt("contact_id"));
    contact.setEntreprise(entreprise);
    contact.setIdEntreprise(rs.getInt("contact_company_id"));
    contact.setUtilisateur(etudiant);
    contact.setIdUtilisateur(rs.getInt("contact_student_id"));
    contact.setEtatContact(rs.getString("contact_status"));
    contact.setLieuxRencontre(rs.getString("contact_meeting_place"));
    contact.setRaisonRefus(rs.getString("contact_refusal_reason"));
    contact.setAnnee(year);

    //Add entreprise info
    entreprise.setId(rs.getInt("company_id"));
    entreprise.setNom(rs.getString("company_name"));
    entreprise.setAppellation(rs.getString("company_designation"));
    entreprise.setAdresse(rs.getString("company_address"));
    entreprise.setCity(rs.getString("company_city"));
    entreprise.setNumTel(rs.getString("company_phone_number"));
    entreprise.setEmail(rs.getString("company_email"));
    entreprise.setBlackListed(rs.getBoolean("company_is_blacklisted"));
    entreprise.setMotivation_blacklist(rs.getString("company_blacklist_reason"));

    //Add stage info
    stage.setId(rs.getInt("internship_id"));
    stage.setSujet(rs.getString("internship_topic"));
    stage.setdateSignature(rs.getString("internship_date_of_signature"));

    stage.setResponsable(responsable);
    stage.setIdResponsable(responsable.getId());
    stage.setEtudiant(etudiant);
    stage.setIdEtudiant(etudiant.getId());
    stage.setContact(contact);
    stage.setIdContact(contact.getId());
    stage.setEntreprise(entreprise);
    stage.setIdEntreprise(entreprise.getId());

    return stage;



  }
}
