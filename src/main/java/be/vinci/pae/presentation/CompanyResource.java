package be.vinci.pae.presentation;

import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.company.CompanyUCC;
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
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * The {@code EntrepriseResource} class represents a resource for managing entreprise-related
 * operations. It is annotated with {@code Singleton} and {@code Path("ent")}.
 */
@Singleton
@Path("entreprise")
@Log
public class CompanyResource {

  @Inject
  private CompanyUCC myCompanyUcc;

  private final ObjectMapper jsonMapper = new ObjectMapper();

  @Inject
  private ContactUCC myContactUcc;


  /**
   * Retrieves all entreprises.
   *
   * @return the list containing all entreprises.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"E", "P"})
  public List<CompanyDTO> getAllEntreprises() {
    return myCompanyUcc.getAllCompanies();
  }

  /**
   * Retrieves an entreprise with the specified id.
   *
   * @param id The id of the entreprise to retrieve.
   * @return The entreprise with the specified id.
   */
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"P"})
  public CompanyDTO getEntreprise(@PathParam("id") int id) {
    if (id <= 0) {
      throw new WebApplicationException("Invalid id", Response.Status.BAD_REQUEST);
    }
    return myCompanyUcc.getCompanyById(id);
  }

  /**
   * BlackList an company with the specified id.
   *
   * @param id The id of the company to update.
   * @param Company The company to update.
   * @return The updated company.
   */
  @PUT
  @Path("/{id}/blacklist")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"P"})
  public ObjectNode blackListCompany(@PathParam("id") int id, CompanyDTO Company) {
    if (id <= 0) {
      throw new WebApplicationException("Invalid id", Response.Status.BAD_REQUEST);
    }
    if (Company.getId() != id) {
      throw new WebApplicationException("Invalid id", Response.Status.BAD_REQUEST);
    }
    if (Company.getMotivation() == null
        || Company.getMotivation().isEmpty()) {
      throw new WebApplicationException("Invalid motivation", Response.Status.BAD_REQUEST);
    }
    myCompanyUcc.blackListCompany(Company);
    myContactUcc.blackListContact(id);
    ObjectNode responseNode = jsonMapper.createObjectNode();
    responseNode.put("message", "Contact and company blacklisted successfully");
    return responseNode;
  }

  /**
   * Add a company.
   *
   * @param Company The company to add.
   * @return The company added.
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"E"})
  public ObjectNode addEntreprise(CompanyDTO Company) {
    if (Company.getName() == null || Company.getName().isEmpty()) {
      throw new WebApplicationException("Invalid entreprise name", Response.Status.BAD_REQUEST);
    }
    if (Company.getAdresse() == null || Company.getAdresse().isEmpty()) {
      throw new WebApplicationException("Invalid entreprise address", Response.Status.BAD_REQUEST);
    }
    if (Company.getCity() == null || Company.getCity().isEmpty()) {
      throw new WebApplicationException("Invalid entreprise city", Response.Status.BAD_REQUEST);
    }




    myCompanyUcc.addCompany(Company);

    ObjectNode responseNode = jsonMapper.createObjectNode();
    responseNode.put("message", "Company  created successfully");
    return responseNode;
  }


}