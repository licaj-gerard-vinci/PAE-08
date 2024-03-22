package be.vinci.pae.presentation;

import be.vinci.pae.business.stage.StageDTO;
import be.vinci.pae.business.stage.StageDetailedDTO;
import be.vinci.pae.business.stage.StageUCC;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.presentation.filters.Authorize;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Singleton
@Path("stages")
public class StageResource {

  @Inject
  private StageUCC myStageUcc;

  /**
   * Retrieves the stage of the authenticated user from the request context.
   *
   * @param requestContext the request context.
   * @return the stage of the authenticated user.
   */
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public StageDTO getUserStage(@Context ContainerRequestContext requestContext, @PathParam("id") int id) {
    if(id <= 0) {
      throw new WebApplicationException("Invalid id", Response.Status.BAD_REQUEST);
    }
    UserDTO authenticatedUser = (UserDTO) requestContext.getProperty("user");
    if (authenticatedUser == null) {
      throw new WebApplicationException("User not found", Response.Status.UNAUTHORIZED);
    }
    StageDTO userStage = myStageUcc.getStageUser(id);
    if (userStage == null) {
      throw new WebApplicationException("Stage not found for user", Response.Status.NOT_FOUND);
    }

    return userStage;
  }
}
