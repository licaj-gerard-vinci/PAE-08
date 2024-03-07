package be.vinci.pae.presentation;

import be.vinci.pae.business.StageDTO;
import be.vinci.pae.business.StageDetailedDTO;
import be.vinci.pae.business.StageUCC;
import be.vinci.pae.business.UserDTO;
import be.vinci.pae.business.UserUCC;
import be.vinci.pae.dal.utils.Json;
import be.vinci.pae.presentation.filters.Authorize;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;

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

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();
  private final Json json = new Json<>(UserDTO.class);
  @Inject
  private UserUCC myUserUcc;
  @Inject
  private StageUCC myStageUcc;


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
    if (!json.hasNonNull("email") || !json.hasNonNull("password")) {
      throw new WebApplicationException("email or password required", Status.NOT_FOUND);
    }
    String email = json.get("email").asText();
    String password = json.get("password").asText();

    // Get and check credentials
    if (email.isEmpty() || password.isEmpty()) {
      throw new WebApplicationException("email or password required", Status.BAD_REQUEST);
    }
    if (!email.endsWith("@student.vinci.be") && !email.endsWith("@vinci.be")) {
      throw new WebApplicationException("email incorrect", Status.UNAUTHORIZED);
    }

    // Try to log in
    UserDTO publicUser = myUserUcc.login(email, password);
    if (publicUser == null) {
      throw new WebApplicationException("not found", Status.UNAUTHORIZED);
    }

    return generateTokenForUser(publicUser);
  }


  /**
   * Retrieves the authenticated user from the request context.
   *
   * @param requestContext the request context.
   * @return the authenticated user.
   */
  @GET
  @Path("user")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public UserDTO getUser(@Context ContainerRequestContext requestContext) {
    UserDTO authenticated = (UserDTO) requestContext.getProperty("user");
    if (authenticated == null) {
      throw new WebApplicationException("not found", Status.UNAUTHORIZED);
    }
    return (UserDTO) json.filterPublicJsonView(authenticated);
  }

  /**
   * Retrieves the stage of the authenticated user from the request context.
   *
   * @param requestContext the request context.
   * @return the stage of the authenticated user.
   */

  @GET
  @Path("stage")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public StageDTO getUserStage(@Context ContainerRequestContext requestContext) {
    UserDTO authenticatedUser = (UserDTO) requestContext.getProperty("user");
    if (authenticatedUser == null) {
      throw new WebApplicationException("User not found", Status.UNAUTHORIZED);
    }
    StageDTO userStage = myStageUcc.GetStageUser(authenticatedUser.getId());
    if (userStage == null) {
      throw new WebApplicationException("Stage not found for user", Status.NOT_FOUND);
    }
    StageDetailedDTO userStageDetail = myStageUcc.getDetailedStageForUser(
        authenticatedUser.getId());
    if (userStageDetail == null) {
      throw new WebApplicationException("Stage not found for user", Status.NOT_FOUND);
    }
    return userStageDetail;


  }


  /**
   * Generates a JWT token for the given user.
   *
   * @param user the user for whom to generate the token.
   * @return an ObjectNode containing the token, user ID, and login.
   */
  public ObjectNode generateTokenForUser(UserDTO user) {
    String token = JWT.create()
        .withIssuer("auth0")
        .withClaim("user", user.getId())
        .withExpiresAt(new java.util.Date(System.currentTimeMillis() + 300000000))
        .sign(jwtAlgorithm);
    return jsonMapper.createObjectNode()
        .put("token", token)
        .put("id", user.getId())
        .put("name", user.getNom())
        .put("firstName", user.getPrenom())
        .put("email", user.getEmail())
        .put("role", user.getRole())
        .put("numTel", user.getNumTel());
  }


}
