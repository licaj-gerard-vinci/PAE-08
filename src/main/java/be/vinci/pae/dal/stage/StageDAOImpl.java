package be.vinci.pae.dal.stage;

import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.stage.StageDTO;
import be.vinci.pae.business.stage.StageDetailedDTO;
import be.vinci.pae.dal.DALBackService;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * The Class StageDAOImpl.
 */
public class StageDAOImpl implements StageDAO {


  @Inject
  private DALBackService dalBackService;

  @Inject
  private Factory factory;

  /**
   * Gets the stage of user.
   *
   * @param id the id
   * @return the stage of user
   */

  public StageDTO getStageOfUser(int id) {
    String query = "SELECT * FROM pae.internships WHERE student_id = ?";
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
   * Gets the detail of stage.
   *
   * @param id the id
   * @return the detail of stage
   */

  public StageDetailedDTO getDetailOfStage(int id) {
    String query = """
          SELECT
            int.internship_id,
            int.topic,
            int.date_of_signature,
            man.lastname ,
            man.firstname ,
            com.name ,
            com.designation
          FROM
            pae.internships int
            INNER JOIN pae.managers man ON int.manager_id = man.manager_id
            INNER JOIN pae.companies com ON int.company_id = com.company_id
          WHERE
            int.student_id = ?;
        """;

    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setInt(1, id);
      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          return rsToDetailedStage(rs);
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
    stage.setId(rs.getInt("internship_id"));
    stage.setResponsable(rs.getInt("manager_id"));
    stage.setEtudiant(rs.getInt("student_id"));
    stage.setContact(rs.getInt("contact_id"));
    stage.setEntreprise(rs.getInt("company_id"));
    stage.setSujet(rs.getString("topic"));
    stage.setdateSignature(rs.getString("date_of_signature"));

    return stage;

  }


  /**
   * Rs to detailed stage.
   *
   * @param rs the rs
   * @return the stage detailed DTO
   * @throws SQLException the SQL exception
   */
  private StageDetailedDTO rsToDetailedStage(ResultSet rs) throws SQLException {
    StageDetailedDTO stage = factory.getDetailedStageDTO();
    stage.setId(rs.getInt("internship_id"));
    stage.setSujet(rs.getString("topic"));
    stage.setdateSignature(rs.getString("date_of_signature"));
    stage.setResponsableNom(rs.getString("lastname"));
    stage.setResponsablePrenom(rs.getString("firstname"));
    stage.setEntrepriseNom(rs.getString("name"));
    stage.setEntrepriseAppellation(rs.getString("designation"));
    return stage;
  }
}
