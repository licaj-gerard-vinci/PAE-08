package be.vinci.pae.presentation;

import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserUCC;
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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.apache.logging.log4j.core.util.internal.Status;


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
   * @return the authenticated user.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<UserDTO> getUsers() {
    return myUserUcc.getAll();
  }



  /**
   * Retrieves a single user by their ID.
   *
   * @param id the user's ID.
   * @return the user with the specified ID or null if not found.
   */
  @PUT
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public UserDTO updateUser(@PathParam("id") int id, UserDTO user) {
    user.setId(id);
    if (user.getPassword() == null || user.getPassword().isBlank()) {
      throw new WebApplicationException("Password is required", Response.Status.BAD_REQUEST);
    }

    boolean updateResult = myUserUcc.update(user);
    if (updateResult) {

      UserDTO userone = myUserUcc.getOne(id);

      return userone;
    } else {
      throw new WebApplicationException("User not found or "
          + "update failed", Status.NOT_FOUND.ordinal());
    }

  }
}



