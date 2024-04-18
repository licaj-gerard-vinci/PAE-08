package be.vinci.pae.presentation;

import be.vinci.pae.business.stage.StageDTO;
import be.vinci.pae.business.stage.StageUCC;
import be.vinci.pae.presentation.filters.Authorize;
import be.vinci.pae.presentation.filters.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
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

  private final ObjectMapper jsonMapper = new ObjectMapper();
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
  @Authorize(roles = {"E", "P"})
  public StageDTO getUserStage(@PathParam("id") int id) {
    if (id <= 0) {
      throw new WebApplicationException("Invalid id", Response.Status.BAD_REQUEST);
    }
    StageDTO userStage = myStageUcc.getInternshipByUserId(id);
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
  @Authorize(roles = {"E", "P"})
  public List<StageDTO> getStages() {
    return myStageUcc.getStages();
  }

  /**
   * Inserts a new internship into the system.
   *
   * @param internship the InternshipDTO object representing the internship to be inserted
   * @return a JSON object containing a success message
   * @throws WebApplicationException if any required information is missing from the InternshipDTO
   */
  @POST
  @Path("/insert")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"E"})
  public ObjectNode insertInternship(StageDTO internship) {
    if (internship.getEntreprise() == null || internship.getEtudiant() == null
            || internship.getIdResponsable() <= 0 || internship.getContact() == null) {
      throw new WebApplicationException("Missing information", Response.Status.BAD_REQUEST);
    }

    myStageUcc.insertInternship(internship);
    ObjectNode responseNode = jsonMapper.createObjectNode();
    responseNode.put("message", "Internship created successfully");
    return responseNode;
  }

  /**
   * Updates the topic of an internship.
   *
   * @param internship the InternshipDTO object representing the internship to be updated
   * @return a JSON object containing a success message
   * @throws WebApplicationException if any required information is missing from the InternshipDTO
   */
  @PUT
  @Path("/update/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"E"})
  public ObjectNode updateInternshipTopic(@PathParam("id") int id ,StageDTO internship) {

    if ( internship.getSujet() == null) {
      throw new WebApplicationException("Missing information", Response.Status.BAD_REQUEST);
    }

    myStageUcc.updateInternshipTopic(internship,id);
    ObjectNode responseNode = jsonMapper.createObjectNode();
    responseNode.put("message", "Internship updated successfully");
    return responseNode;

  }
}