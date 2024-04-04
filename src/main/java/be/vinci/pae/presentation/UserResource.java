package be.vinci.pae.presentation;

import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserUCC;
import be.vinci.pae.presentation.filters.Authorize;
import be.vinci.pae.presentation.filters.Log;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.BadRequestException;
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
import java.util.Map;
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
  @Authorize
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
  @Authorize
  public UserDTO updateUser(@PathParam("id") int id, UserDTO user) {


    boolean updateResult = myUserUcc.update(id, user);
    if (updateResult) {

      return myUserUcc.getOne(id);
    } else {
      throw new WebApplicationException("User not found or update failed",
          Status.NOT_FOUND.ordinal());
    }

  }

  /**
   * verify password of a user.
   *
   * @param id the user to register.
   * @return the registered user.
   */
  @POST
  @Path("/{id}/verify")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public Response verifyUser(@PathParam("id") int id, Map<String, String> passwordWrapper) {
    String oldPassword = passwordWrapper.get("Password");
    if (oldPassword == null || oldPassword.trim().isEmpty()) {
      throw new BadRequestException("Old password must be provided");
    }


    if (!myUserUcc.checkPassword(id, oldPassword)) {
      return Response.status(Response.Status.UNAUTHORIZED)
          .entity("Old password verification failed. Please ensure you've entered the correct "
              + "password.")
          .build();
    }




    return Response.ok().build();
  }
}



