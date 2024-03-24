package be.vinci.pae.dal.stage;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.responsable.ResponsableDTO;
import be.vinci.pae.business.stage.StageDTO;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.dal.DALBackService;
import be.vinci.pae.dal.utils.DALBackServiceUtils;
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
   * @param userId the id
   * @return the stage by id
   */
  @Override
  public StageDTO getStageById(int userId) {
    String query =
        "SELECT int.*, man.*, use.*, con.*, com.*, sch.* "
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
          stage = rsToStage(rs);
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return stage;
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

    // Fill DTOs using methods from DALBackServiceUtils
    ResponsableDTO responsable = dalBackServiceUtils.fillResponsableDTO(rs);
    UserDTO etudiant = dalBackServiceUtils.fillUserDTO(rs);
    ContactDTO contact = dalBackServiceUtils.fillContactDTO(rs);
    EntrepriseDTO entreprise = dalBackServiceUtils.fillEntrepriseDTO(rs);

    // Add stage info
    stage.setId(rs.getInt("internship_id"));
    stage.setSujet(rs.getString("internship_topic"));
    stage.setdateSignature(rs.getString("internship_date_of_signature"));

    // Set filled DTOs to stage
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
