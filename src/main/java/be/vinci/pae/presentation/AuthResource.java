package be.vinci.pae.presentation;

import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserUCC;
import be.vinci.pae.presentation.filters.Authorize;
import be.vinci.pae.presentation.filters.Log;
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
import java.text.Normalizer;

/**
 * The {@code AuthResource} class provides RESTful web resources using JAX-RS annotations to handle
 * authentication operations, such as logging in users. It makes use of dependency injection to
 * obtain a reference to the {@code UserDataService} for accessing user-related data. The class is
 * marked as {@code Singleton} to ensure a single instance is used during the application's
 * lifecycle.
 */
@Singleton
@Path("auth")
@Log
public class AuthResource {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();

  @Inject
  private UserUCC myUserUcc;


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
    if (!json.hasNonNull("email") || !json.hasNonNull("password")
        || json.get("email").asText().isBlank() || json.get("password").asText().isBlank()
        || !json.get("email").asText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
      throw new WebApplicationException("email or password required", Status.BAD_REQUEST);
    }
    String email = json.get("email").asText();
    String password = json.get("password").asText();

    // Get and check credentials
    if (email.isEmpty() || password.isEmpty()) {
      throw new WebApplicationException("email or password required", Status.BAD_REQUEST);
    }
    if (!email.endsWith("@student.vinci.be") && !email.endsWith("@vinci.be")) {
      throw new WebApplicationException("wrong email", Status.BAD_REQUEST);
    }

    // Try to log in
    UserDTO publicUser = myUserUcc.login(email, password);
    if (publicUser == null) {
      throw new WebApplicationException("not found", Status.NOT_FOUND);
    }

    return generateTokenForUser(publicUser);
  }

  /**
   * Registers a new user by creating a new account with the given details. The method expects a
   * JSON object containing the necessary user details and returns a JSON object with the
   * authentication result.
   *
   * @param user A {@code UserDTO} containing the user's details.
   * @return An {@code ObjectNode} representing the authentication result.
   */
  @POST
  @Path("register")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode register(UserDTO user) {
    if (user.getLastname() == null || user.getFirstname() == null || user.getEmail() == null
        || user.getPassword() == null || user.getPhone() == null || user.getRole() == null) {
      throw new WebApplicationException("no info", Status.BAD_REQUEST);
    }

    if (user.getEmail().isEmpty() || user.getPassword().isEmpty() || user.getLastname().isEmpty()
        || user.getFirstname().isEmpty() || user.getPhone().isEmpty() || user.getRole().isEmpty()
    ) {
      throw new WebApplicationException("email or password required", Status.BAD_REQUEST);
    }

    String lastname = Normalizer.normalize(user.getLastname().toLowerCase(), Normalizer.Form.NFD)
        .replaceAll("[^\\p{ASCII}]", "");
    String firstname = Normalizer.normalize(user.getFirstname().toLowerCase(), Normalizer.Form.NFD)
        .replaceAll("[^\\p{ASCII}]", "");
    String emailPattern = firstname + "." + lastname;
    String email = user.getEmail().toLowerCase();

    if (!(email.startsWith(emailPattern) && (email.endsWith("@student.vinci.be") || email.endsWith(
        "@vinci.be")))) {
      throw new WebApplicationException("wrong email", Status.BAD_REQUEST);
    }

    // Try to log in
    UserDTO publicUser = myUserUcc.register(user);
    if (publicUser == null) {
      throw new WebApplicationException("not found", Status.NOT_FOUND);
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
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"A", "E", "P"})
  public UserDTO getUser(@Context ContainerRequestContext requestContext) {
    UserDTO authenticatedUser = (UserDTO) requestContext.getProperty("user");
    return myUserUcc.getOne(authenticatedUser.getId());
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
        .putPOJO("user", user);
  }


}
