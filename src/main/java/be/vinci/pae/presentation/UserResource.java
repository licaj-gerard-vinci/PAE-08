package be.vinci.pae.presentation;

import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserUCC;
import be.vinci.pae.presentation.filters.Authorize;
import be.vinci.pae.presentation.filters.Log;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.List;

/**
 * UserResource.
 */
@Singleton
@Path("users")
@Log
public class UserResource {

  @Inject
  private UserUCC myUserUcc;

  /**
   * Retrieves the authenticated user from the request context.
   *
   * @param requestContext the request context.
   * @return the authenticated user.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<UserDTO> getUsers(@Context ContainerRequestContext requestContext) {
    UserDTO authenticatedUser = (UserDTO) requestContext.getProperty("user");
    if (authenticatedUser == null) {
      throw new WebApplicationException("User not found", Response.Status.UNAUTHORIZED);
    }
    return myUserUcc.getAll();
  }


  @PUT
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public UserDTO updateUser(@PathParam("id") int id, UserDTO user) {
    user.setId(id);
    boolean updateResult = myUserUcc.update(user);
    if (updateResult) {

      UserDTO userone = myUserUcc.getOne(id);

      return userone;
    } else {
      throw new WebApplicationException("User not found or update failed", Status.NOT_FOUND);
    }








  }




}
