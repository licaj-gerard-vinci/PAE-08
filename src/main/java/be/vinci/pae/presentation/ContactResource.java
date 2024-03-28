package be.vinci.pae.presentation;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.presentation.filters.Authorize;
import be.vinci.pae.presentation.filters.Log;
import be.vinci.pae.presentation.filters.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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
@Log
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
   * @param id             The id of the contact.
   * @return A list of all contact information.
   * @throws WebApplicationException If the user is not authenticated.
   */
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<ContactDTO> getContatcsAllInfo(
      @PathParam("id") int id) {
    List<ContactDTO> contactDTOs = myContactUcc.getContactsAllInfo(id);
    if (contactDTOs == null || contactDTOs.isEmpty()) {
      contactDTOs = new ArrayList<>();
    }
    return contactDTOs;
  }

  /**
   * Inserts a new contact for the authenticated user.
   *
   * @param contact        The contact to insert.
   * @return A JSON object containing a success message.
   * @throws WebApplicationException If the user is not authenticated.
   */
  @POST
  @Path("/insert")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Student
  public ObjectNode insertContact(ContactDTO contact) {
    if (contact.getEntreprise() == null || contact.getUtilisateur() == null || contact.getEtatContact() == null) {
        throw new WebApplicationException("Missing information", Response.Status.BAD_REQUEST);
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
   * @return A JSON object containing a success message.
   * @throws WebApplicationException If the user is not authenticated.
   */
  @PUT
  @Path("/update")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Student
  public ObjectNode updateContact(ContactDTO contact) {
    if(contact.getEntreprise() == null || contact.getUtilisateur() == null || contact.getEtatContact() == null || contact.getVersion() == 0) {
      throw new WebApplicationException("Missing information", Response.Status.BAD_REQUEST);
    }
    myContactUcc.updateContact(contact);
    ObjectNode responseNode = jsonMapper.createObjectNode();
    responseNode.put("message", "Contact updated successfully");
    return responseNode;
  }
}
