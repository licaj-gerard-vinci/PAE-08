package be.vinci.pae.presentation;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.business.user.UserDTO;
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
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class ContactResource.
 */
@Singleton
@Path("contacts")
public class ContactResource {

  private final ObjectMapper jsonMapper = new ObjectMapper();

  @Inject
  private ContactUCC myContactUcc;

  /**
   * Retrieves the contacts of the authenticated user from the request context.
   *
   * @return the contacts of the authenticated user.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<ContactDTO> getContatcs() {
    List<ContactDTO> contactDetailledDTOs = myContactUcc.getContacts();
    if (contactDetailledDTOs == null || contactDetailledDTOs.isEmpty()) {
      throw new WebApplicationException("Contacts not found for user", Response.Status.NOT_FOUND);
    }
    return contactDetailledDTOs;
  }

  /**
   * Retrieves all contact information for the authenticated user.
   *
   * @param requestContext The context of the container request.
   * @return A list of all contact information.
   * @throws WebApplicationException If the user is not authenticated.
   */
  @GET
  @Path("/detailed")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<ContactDTO> getContatcsAllInfo(@Context ContainerRequestContext requestContext) {
    UserDTO authenticatedUser = (UserDTO) requestContext.getProperty("user");
    if (authenticatedUser == null) {
      throw new WebApplicationException("User not found", Response.Status.UNAUTHORIZED);
    }

    List<ContactDTO> contactDTOs = myContactUcc.getContactsAllInfo(authenticatedUser.getId());
    if (contactDTOs == null || contactDTOs.isEmpty()) {
      contactDTOs = new ArrayList<>();
    }
    return contactDTOs;
  }

  /**
   * Inserts a new contact for the authenticated user.
   *
   * @param contact        The contact to insert.
   * @param requestContext The context of the container request.
   * @return A JSON object containing a success message.
   * @throws WebApplicationException If the user is not authenticated.
   */
  @POST
  @Path("/insert")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public ObjectNode insertContact(ContactDTO contact,
      @Context ContainerRequestContext requestContext) {
    System.out.println(contact);
    UserDTO authenticatedUser = (UserDTO) requestContext.getProperty("user");
    if (authenticatedUser == null) {
      throw new WebApplicationException("User not found", Response.Status.UNAUTHORIZED);
    }

    myContactUcc.insertContact(contact);

    ObjectNode responseNode = jsonMapper.createObjectNode();
    responseNode.put("message", "Contact created successfully");
    return responseNode;
  }

  /**
   * Updates a contact for the authenticated user.
   *
   * @param contact        The contact to update.
   * @param requestContext The context of the container request.
   * @return A JSON object containing a success message.
   * @throws WebApplicationException If the user is not authenticated.
   */
  @PUT
  @Path("/update")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  @Log
  public ObjectNode updateContact(ContactDTO contact,
      @Context ContainerRequestContext requestContext) {
    UserDTO authenticatedUser = (UserDTO) requestContext.getProperty("user");
    if (authenticatedUser == null) {
      throw new WebApplicationException("User not found", Response.Status.UNAUTHORIZED);
    }

    myContactUcc.updateContact(contact);

    ObjectNode responseNode = jsonMapper.createObjectNode();
    responseNode.put("message", "Contact updated successfully");
    return responseNode;
  }
}
