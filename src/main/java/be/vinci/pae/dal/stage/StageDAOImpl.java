package be.vinci.pae.dal.stage;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.responsable.ResponsableDTO;
import be.vinci.pae.business.stage.StageDTO;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.dal.DALBackService;
import be.vinci.pae.dal.utils.DALBackServiceUtils;
import be.vinci.pae.exceptions.FatalException;
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
  private DALBackServiceUtils dalBackServiceUtils;

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
          stages.add(rsToStage(rs, "get"));
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return stages;
  }

  /**
   * Gets the stage by id.
   *
   * @param userId the id
   * @return the stage by id
   */
  @Override
  public StageDTO getStageById(int userId) {
    String query =
        "SELECT int.*, man.*, use.*, con.*, com.*, sch.*"
            + "FROM pae.internships int, pae.managers man, pae.users use, pae.contacts con, "
            + "pae.companies com, pae.school_years sch "
            + "WHERE int.internship_manager_id = man.manager_id "
            + "AND int.internship_student_id = use.user_id "
            + "AND int.internship_contact_id = con.contact_id "
            + "AND con.contact_company_id = com.company_id "
            + "AND use.user_school_year_id = sch.school_year_id "
            + "AND use.user_id = ?";

    StageDTO stage = null;
    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setInt(1, userId);
      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          stage = rsToStage(rs, "get");
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return stage;
  }

  /**
   * Inserts a new internship into the database.
   *
   * @param internship the contact to insert
   */
  @Override
  public void insertInternship(StageDTO internship) {
    String query = "INSERT INTO pae.internships "
            + "(internship_manager_id, internship_student_id, internship_contact_id, "
            + "internship_company_id, internship_school_year_id, internship_topic, "
            + "internship_date_of_signature, internship_version) "
            + "VALUES (?, ?, ?, ?, 1, ?, ?, 1)";
    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setInt(1, internship.getIdResponsable());
      statement.setInt(2, internship.getEtudiant().getId());
      statement.setInt(3, internship.getContact().getId());
      statement.setInt(4, internship.getEntreprise().getId());
      statement.setString(5, internship.getSujet());
      statement.setDate(6, internship.getdateSignature());
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException(e);
    }
  }

    /**
     * Update internship.
     *
     * @param internship the internship
     */

  public void updateInternshipTopic(StageDTO internship) {
    String query = "UPDATE pae.internships "
            + "SET internship_topic = ?, internship_version = internship_version + 1"
            + "WHERE internship_id = ? AND internship_version = ?";
    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setString(1, internship.getSujet());
      statement.setInt(2, internship.getId());
      statement.setInt(3, internship.getVersion());
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new FatalException(e);
    }
  }


  /**
   * Rs to stage.
   *
   * @param rs the rs
   * @param method the method
   * @return the stage DTO
   * @throws SQLException the SQL exception
   */

  public StageDTO rsToStage(ResultSet rs, String method) throws SQLException {
    StageDTO stage = factory.getStageDTO();

    // Fill DTOs using methods from DALBackServiceUtils
    ResponsableDTO responsable = dalBackServiceUtils.fillResponsableDTO(rs, method);
    UserDTO etudiant = dalBackServiceUtils.fillUserDTO(rs, method);
    ContactDTO contact = dalBackServiceUtils.fillContactDTO(rs, method);
    EntrepriseDTO entreprise = dalBackServiceUtils.fillEntrepriseDTO(rs, method);

    // Add stage info
    stage.setId(rs.getInt("internship_id"));
    stage.setSujet(rs.getString("internship_topic"));
    stage.setdateSignature(rs.getDate("internship_date_of_signature"));

    // Set filled DTOs to stage
    stage.setResponsable(responsable);
    stage.setIdResponsable(responsable.getId());
    stage.setEtudiant(etudiant);
    stage.setIdEtudiant(etudiant.getId());
    stage.setContact(contact);
    stage.setIdContact(contact.getId());
    stage.setEntreprise(entreprise);
    stage.setIdEntreprise(entreprise.getId());
    if (method.equals("update")) {
      stage.setVersion(rs.getInt("internship_version") + 1);
    } else {
      stage.setVersion(rs.getInt("internship_version"));
    }
    return stage;
  }
}
