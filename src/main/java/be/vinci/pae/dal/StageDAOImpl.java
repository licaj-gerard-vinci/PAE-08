package be.vinci.pae.dal;

import be.vinci.pae.business.Factory;
import be.vinci.pae.business.StageDTO;
import be.vinci.pae.business.StageDetailedDTO;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * The Class StageDAOImpl.
 */
public class StageDAOImpl implements StageDAO {


  @Inject
  private DALService dalService;

  @Inject
  private Factory factory;

  /**
   * Gets the stage of user.
   *
   * @param id the id
   * @return the stage of user
   */

  public StageDTO getStageOfUser(int id) {
    String query = "SELECT * FROM pae.stages WHERE etudiant = ?";
    try (PreparedStatement statement = dalService.preparedStatement(query)) {
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
            s.id_stage,
            s.sujet,
            s.date_signature,
            r.nom AS responsable_nom,
            r.prenom AS responsable_prenom,
            e.nom AS entreprise_nom,
            e.appellation AS entreprise_appellation
          FROM
            pae.stages s
            INNER JOIN pae.responsables r ON s.responsable = r.id_responsable
            INNER JOIN pae.entreprises e ON s.entreprise = e.id_entreprise
          WHERE
            s.etudiant = ?;
        """;

    try (PreparedStatement statement = dalService.preparedStatement(query)) {
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
    stage.setId(rs.getInt("id_stage"));
    stage.setResponsable(rs.getInt("responsable"));
    stage.setEtudiant(rs.getInt("etudiant"));
    stage.setContact(rs.getInt("contact"));
    stage.setEntreprise(rs.getInt("entreprise"));
    stage.setSujet(rs.getString("sujet"));
    stage.setdateSignature(rs.getString("date_signature"));

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
    stage.setId(rs.getInt("id_stage"));
    stage.setSujet(rs.getString("sujet"));
    stage.setdateSignature(rs.getString("date_signature"));
    stage.setResponsableNom(rs.getString("responsable_nom"));
    stage.setResponsablePrenom(rs.getString("responsable_prenom"));
    stage.setEntrepriseNom(rs.getString("entreprise_nom"));
    stage.setEntrepriseAppellation(rs.getString("entreprise_appellation"));
    return stage;
  }
}
