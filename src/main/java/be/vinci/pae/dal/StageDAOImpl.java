package be.vinci.pae.dal;

import be.vinci.pae.business.Factory;
import be.vinci.pae.business.StageDTO;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StageDAOImpl implements StageDAO {


  @Inject
  private DALService dalService;

  @Inject
  private Factory factory;


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

}
