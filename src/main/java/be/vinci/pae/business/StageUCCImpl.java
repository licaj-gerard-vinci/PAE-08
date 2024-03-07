package be.vinci.pae.business;

import be.vinci.pae.dal.StageDAO;
import jakarta.inject.Inject;

public class StageUCCImpl implements StageUCC {

  @Inject
  private StageDAO stageDAO;


  public StageDTO GetStageUser(int idUser) {
    return stageDAO.getStageOfUser(idUser);
  }

}
