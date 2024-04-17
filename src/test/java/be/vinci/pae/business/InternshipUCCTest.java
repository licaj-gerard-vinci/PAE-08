package be.vinci.pae.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.stage.StageDTO;
import be.vinci.pae.business.stage.StageUCC;
import be.vinci.pae.dal.stage.StageDAO;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.exceptions.NotFoundException;
import java.util.Arrays;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import utils.ApplicationBinderTest;

/**
 * The {@code InternshipUCCTest} class tests the {@code InternshipUCC} class.
 */
public class InternshipUCCTest {

  private StageUCC stageUCC;
  private StageDAO stageDAO;
  private Factory factory;

  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    stageUCC = locator.getService(StageUCC.class);
    factory = locator.getService(Factory.class);
    stageDAO = locator.getService(StageDAO.class);
  }

  @Test
  @DisplayName("test get all stage succes")
  void testgetALL() {
    StageDTO stage1 = factory.getStageDTO();
    StageDTO stage2 = factory.getStageDTO();

    Mockito.when(stageDAO.getStages()).thenReturn(Arrays.asList(stage1, stage2));
    List<StageDTO> stage = stageUCC.getStages();
    assertEquals(2, stage.size());
  }


  @Test
  @DisplayName("Test getStages when no stages are found")
  void testStageNotFound() {
    Mockito.when(stageDAO.getStages()).thenThrow(new NotFoundException());
    assertThrows(NotFoundException.class, () -> stageUCC.getStages(),
        "NotFoundException was expected to be thrown when no stages are found");
  }

  @Test
  @DisplayName("fatal exception when trying to get all stage")
  void catchFatal() {
    Mockito.when(stageDAO.getStages()).thenThrow(new FatalException());
    assertThrows(FatalException.class, () -> stageUCC.getStages());
  }


  @Test
  @DisplayName("Succes get stage by user")
  void getStageByUser() {

    int userId = 7;
    StageDTO mockStage = factory.getStageDTO();
    Mockito.when(stageDAO.getStageById(userId)).thenReturn(mockStage);

    StageDTO resultStage = stageUCC.getStageUser(userId);

    assertNotNull(resultStage, "The result stage should not be null.");
    assertEquals(mockStage, resultStage,
        "The result stage should match the mock stage returned by DAO.");
  }


  @Test
  @DisplayName("Not Found Exception when stage by user is not found")
  void notFound() {
    // Arrange
    int userId = 1;
    Mockito.when(stageDAO.getStageById(userId)).thenReturn(null);
    assertNull(stageUCC.getStageUser(userId),
        "The result stage should be null when no stage is found for the user.");
  }


  @Test
  @DisplayName("Fatal exception when trying to get a stage")
  void catchFatale() {

    int userId = 1;
    Mockito.when(stageDAO.getStageById(userId)).thenThrow(new FatalException("Database error"));

    assertThrows(FatalException.class, () -> stageUCC.getStageUser(userId),
        "FatalException should be thrown when there is a database error.");
  }


}
