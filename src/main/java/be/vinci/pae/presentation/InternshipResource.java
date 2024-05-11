package be.vinci.pae.presentation;

import be.vinci.pae.business.internship.InternshipDTO;
import be.vinci.pae.business.internship.InternshipUCC;
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
 * internshipResource.
 */
@Singleton
@Path("internships")
@Log
public class InternshipResource {

  private final ObjectMapper jsonMapper = new ObjectMapper();
  @Inject
  private InternshipUCC myInternshipUcc;

  /**
   * Retrieves the internship of the authenticated user from the request context.
   *
   * @param id             the id of the user.
   * @return the internship of the authenticated user.
   */
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"E", "P"})
  public InternshipDTO getUserInternship(@PathParam("id") int id) {
    if (id <= 0) {
      throw new WebApplicationException("Invalid id", Response.Status.BAD_REQUEST);
    }
    InternshipDTO userInternship = myInternshipUcc.getInternshipByUserId(id);
    if (userInternship == null) {
      throw new WebApplicationException("internship not found for user", Response.Status.NOT_FOUND);
    }
    return userInternship;
  }

  /**
   * Retrieves all internships.
   *
   * @return all internships.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"E", "P"})
  public List<InternshipDTO> getInternships() {
    return myInternshipUcc.getInternship();
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
  public ObjectNode insertInternship(InternshipDTO internship) {
    if (internship.getCompany() == null || internship.getStudent() == null
            || internship.getIdManager() <= 0 || internship.getContact() == null) {
      throw new WebApplicationException("Missing information", Response.Status.BAD_REQUEST);
    }

    myInternshipUcc.insertInternship(internship);
    ObjectNode responseNode = jsonMapper.createObjectNode();
    responseNode.put("message", "Internship created successfully");
    return responseNode;
  }

  /**
   * Updates the topic of an internship.
   *
   * @param internship the InternshipDTO object representing the internship to be updated
   * @param id         the id of the internship to be updated
   * @return a JSON object containing a success message
   * @throws WebApplicationException if any required information is missing from the InternshipDTO
   */
  @PUT
  @Path("/update/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"E"})
  public ObjectNode updateInternshipTopic(@PathParam("id") int id, InternshipDTO internship) {

    if (internship.getTopic() == null) {
      throw new WebApplicationException("Missing information", Response.Status.BAD_REQUEST);
    }

    myInternshipUcc.updateInternshipTopic(internship, id);
    ObjectNode responseNode = jsonMapper.createObjectNode();
    responseNode.put("message", "Internship updated successfully");
    return responseNode;

  }
}