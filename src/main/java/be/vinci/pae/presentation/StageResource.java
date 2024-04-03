package be.vinci.pae.presentation;

import be.vinci.pae.business.internship.InternshipDTO;
import be.vinci.pae.business.internship.InternshipUCC;
import be.vinci.pae.presentation.filters.Authorize;
import be.vinci.pae.presentation.filters.Log;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
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
  private InternshipUCC myStageUcc;

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
  public InternshipDTO getUserStage(@PathParam("id") int id) {
    if (id <= 0) {
      throw new WebApplicationException("Invalid id", Response.Status.BAD_REQUEST);
    }
    InternshipDTO userStage = myStageUcc.getStageUser(id);
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
  public List<InternshipDTO> getStages() {
    return myStageUcc.getStages();
  }

  @POST
  @Path("/insert")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public ObjectNode insertInternship(InternshipDTO internship) {
    if (internship.getEntreprise() == null || internship.getEtudiant() == null
            || internship.getResponsable() == null) {
      throw new WebApplicationException("Missing information", Response.Status.BAD_REQUEST);
    }

    return null;
  }
}