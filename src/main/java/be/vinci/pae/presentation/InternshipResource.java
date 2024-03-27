package be.vinci.pae.presentation;

import be.vinci.pae.business.internship.InternshipDTO;
import be.vinci.pae.business.internship.InternshipUCC;
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
 * InternshipResource.
 */
@Singleton
@Path("internships")
@Log
public class InternshipResource {

  @Inject
  private InternshipUCC myInternshipUcc;

  /**
   * Retrieves the stage of the authenticated user from the request context.
   *
   * @param id             the id of the user.
   * @param requestContext the request context.
   * @return the stage of the authenticated user.
   */
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public InternshipDTO getUserInternship(@Context ContainerRequestContext requestContext,
                                         @PathParam("id") int id) {
    if (id <= 0) {
      throw new WebApplicationException("Invalid id", Response.Status.BAD_REQUEST);
    }
    UserDTO authenticatedUser = (UserDTO) requestContext.getProperty("user");
    if (authenticatedUser == null) {
      throw new WebApplicationException("User not found", Response.Status.UNAUTHORIZED);
    }
    InternshipDTO userInternship = myInternshipUcc.getInternshipUser(id);
    if (userInternship == null) {
      throw new WebApplicationException("Internship not found for user", Response.Status.NOT_FOUND);
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
  public List<InternshipDTO> getStages() {
    return myInternshipUcc.getInternships();
  }
}