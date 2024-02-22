package be.vinci.pae.presentation;

import be.vinci.pae.donnees.UserDataService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * The {@code AuthResource} class provides RESTful web resources using JAX-RS annotations to handle
 * authentication operations, such as logging in users. It makes use of dependency injection to
 * obtain a reference to the {@code UserDataService} for accessing user-related data. The class is
 * marked as {@code Singleton} to ensure a single instance is used during the application's
 * lifecycle.
 */
@Singleton
@Path("auth")
public class AuthRessource {

  @Inject
  private UserDataService myUserDataService;


  /**
   * Logs in a user by verifying their credentials. The method expects a JSON object containing the
   * necessary authentication details and returns a JSON object with the authentication result.
   *
   * @param json A {@code JsonNode} containing the user's login credentials.
   * @return An {@code ObjectNode} representing the authentication result.
   */
  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode login(JsonNode json) {

    //TODO faire le login

    return null;
  }


}
