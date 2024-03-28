package be.vinci.pae.presentation;

import be.vinci.pae.business.stage.StageDTO;
import be.vinci.pae.business.stage.StageUCC;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.presentation.filters.Authorize;
import be.vinci.pae.presentation.filters.Log;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * StageResource.
 */
@Singleton
@Path("stages")
@Log
public class StageResource {

  @Inject
  private StageUCC myStageUcc;

  /**
   * Retrieves the stage of the authenticated user from the request context.
   *
   * @param id             the id of the user.
   * @return the stage of the authenticated user.
   */
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public StageDTO getUserStage(@PathParam("id") int id) {
    if (id <= 0) {
      throw new WebApplicationException("Invalid id", Response.Status.BAD_REQUEST);
    }
    StageDTO userStage = myStageUcc.getStageUser(id);
    if (userStage == null) {
      throw new WebApplicationException("Stage not found for user", Response.Status.NOT_FOUND);
    }
    return userStage;
  }

  /**
   * Retrieves all stages.
   *
   * @return all stages.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<StageDTO> getStages() {
    return myStageUcc.getStages();
  }
}