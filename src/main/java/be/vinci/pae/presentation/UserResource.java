package be.vinci.pae.presentation;

import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserUCC;
import be.vinci.pae.presentation.filters.Authorize;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Singleton
@Path("users")
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


}
