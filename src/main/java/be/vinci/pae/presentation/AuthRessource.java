package be.vinci.pae.presentation;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.contact.ContactDetailledDTO;
import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.business.stage.StageDTO;
import be.vinci.pae.business.stage.StageDetailedDTO;
import be.vinci.pae.business.stage.StageUCC;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserUCC;
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
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.List;

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

  @Inject
  private UserUCC myUserUcc;
  @Inject
  private StageUCC myStageUcc;

  @Inject
  private ContactUCC myContactUcc;


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
    return authenticated;
  }

  /**
   * Retrieves all the users from the database.
   *
   * @param requestContext the request context of the HTTP request.
   * @return a list of UserDTO representing all users.
   */
  @GET
  @Path("users")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<UserDTO> getAllUsers(@Context ContainerRequestContext requestContext) {
    UserDTO authenticated = (UserDTO) requestContext.getProperty("user");
    if (authenticated == null) {
      throw new WebApplicationException("User not found", Status.UNAUTHORIZED);
    }
    // Vérifie si l'utilisateur authentifié est un professeur ('P') ou un administrateur ('A')
    if (!authenticated.getRole().equals("P") && !authenticated.getRole().equals("A")) {
      throw new WebApplicationException("Not authorized to access this resource",
          Status.UNAUTHORIZED);
    }
    return myUserUcc.getAll();
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
    StageDTO userStage = myStageUcc.getStageUser(authenticatedUser.getId());
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
   * Retrieves the contacts of the authenticated user from the request context.
   *
   * @param requestContext the request context.
   * @return the contacts of the authenticated user.
   */
  @GET
  @Path("contact")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<ContactDetailledDTO> getContatcs(@Context ContainerRequestContext requestContext) {
    UserDTO authenticatedUser = (UserDTO) requestContext.getProperty("user");
    if (authenticatedUser == null) {
      throw new WebApplicationException("User not found", Status.UNAUTHORIZED);
    }

    List<ContactDetailledDTO> contactDetailledDTOs = myContactUcc.getContacts(
        authenticatedUser.getId());
    if (contactDetailledDTOs == null || contactDetailledDTOs.isEmpty()) {
      throw new WebApplicationException("Contacts not found for user", Status.NOT_FOUND);
    }
    return contactDetailledDTOs; // Retourne la liste des contacts détaillés
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
      throw new WebApplicationException("no info", Status.NOT_FOUND);
    }

    if (user.getEmail().isEmpty() || user.getPassword().isEmpty() || user.getLastname().isEmpty()
        || user.getFirstname().isEmpty() || user.getPhone().isEmpty() || user.getRole().isEmpty()
    ) {
      throw new WebApplicationException("email or password required", Status.BAD_REQUEST);
    }
    if (!user.getEmail().endsWith("@student.vinci.be") && !user.getEmail().endsWith("@vinci.be")) {
      throw new WebApplicationException("email incorrect", Status.BAD_REQUEST);
    }

    // Try to log in
    UserDTO publicUser = myUserUcc.register(user);
    if (publicUser == null) {
      throw new WebApplicationException("not found", Status.NOT_FOUND);
    }

    return generateTokenForUser(publicUser);
  }

  /**
   * Retrieves all contact information for the authenticated user.
   *
   * @param requestContext The context of the container request.
   * @return A list of all contact information.
   * @throws WebApplicationException If the user is not authenticated.
   */
  @GET
  @Path("contactAllInfo")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<ContactDTO> getContatcsAllInfo(@Context ContainerRequestContext requestContext) {
    UserDTO authenticatedUser = (UserDTO) requestContext.getProperty("user");
    if (authenticatedUser == null) {
      throw new WebApplicationException("User not found", Status.UNAUTHORIZED);
    }

    List<ContactDTO> contactDTOs = myContactUcc.getContactsAllInfo(
        authenticatedUser.getId());
    if (contactDTOs == null || contactDTOs.isEmpty()) {
      contactDTOs = new ArrayList<>();
    }
    return contactDTOs;
  }

  /**
   * Inserts a new contact for the authenticated user.
   *
   * @param contact The contact to insert.
   * @param requestContext The context of the container request.
   * @return A JSON object containing a success message.
   * @throws WebApplicationException If the user is not authenticated.
   */
  @POST
  @Path("insertContact")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public ObjectNode insertContact(ContactDTO contact,
                                  @Context ContainerRequestContext requestContext) {
    UserDTO authenticatedUser = (UserDTO) requestContext.getProperty("user");
    if (authenticatedUser == null) {
      throw new WebApplicationException("User not found", Status.UNAUTHORIZED);
    }
    myContactUcc.insertContact(contact);

    ObjectNode responseNode = jsonMapper.createObjectNode();
    responseNode.put("message", "Contact created successfully");
    return responseNode;
  }

  /**
   * Updates a contact for the authenticated user.
   *
   * @param contact The contact to update.
   * @param requestContext The context of the container request.
   * @return A JSON object containing a success message.
   * @throws WebApplicationException If the user is not authenticated.
   */
  @PUT
  @Path("updateContact")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public ObjectNode updateContact(ContactDTO contact,
                                  @Context ContainerRequestContext requestContext) {
    UserDTO authenticatedUser = (UserDTO) requestContext.getProperty("user");
    if (authenticatedUser == null) {
      throw new WebApplicationException("User not found", Status.UNAUTHORIZED);
    }
    myContactUcc.updateContact(contact);

    ObjectNode responseNode = jsonMapper.createObjectNode();
    responseNode.put("message", "Contact updated successfully");
    return responseNode;
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
        .put("name", user.getLastname())
        .put("firstName", user.getFirstname())
        .put("email", user.getEmail())
        .put("role", user.getRole())
        .put("numTel", user.getPhone())
        .put("schoolYear", user.getYear())
        .put("hasInternship", user.getHasInternship());
  }


}
