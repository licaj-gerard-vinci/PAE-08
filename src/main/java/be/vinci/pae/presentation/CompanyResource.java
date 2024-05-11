package be.vinci.pae.presentation;

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
 * The {@code companyResource} class represents a resource for managing company-related
 * operations. It is annotated with {@code Singleton} and {@code Path("ent")}.
 */
@Singleton
@Path("companies")
@Log
public class CompanyResource {

  @Inject
  private CompanyUCC myCompanyUcc;

  private final ObjectMapper jsonMapper = new ObjectMapper();


  /**
   * Retrieves all companies.
   *
   * @return the list containing all companies.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"E", "P"})
  public List<CompanyDTO> getAllCompanies() {
    return myCompanyUcc.getAllCompanies();
  }

  /**
   * Retrieves a company with the specified id.
   *
   * @param id The id of the company to retrieve.
   * @return The company with the specified id.
   */
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"P"})
  public CompanyDTO getCompany(@PathParam("id") int id) {
    if (id <= 0) {
      throw new WebApplicationException("Invalid id", Response.Status.BAD_REQUEST);
    }
    return myCompanyUcc.getCompanyById(id);
  }

  /**
   * BlackList a company with the specified id.
   *
   * @param id The id of the company to update.
   * @param company The company to update.
   * @return The updated company.
   */
  @PUT
  @Path("/{id}/blacklist")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"P"})
  public ObjectNode blackListCompany(@PathParam("id") int id, CompanyDTO company) {
    if (id <= 0) {
      throw new WebApplicationException("Invalid id", Response.Status.BAD_REQUEST);
    }
    if (company.getId() != id) {
      throw new WebApplicationException("Invalid id", Response.Status.BAD_REQUEST);
    }
    if (company.getMotivation() == null
        || company.getMotivation().isEmpty()) {
      throw new WebApplicationException("Invalid motivation", Response.Status.BAD_REQUEST);
    }
    myCompanyUcc.blackListCompany(company);
    ObjectNode responseNode = jsonMapper.createObjectNode();
    responseNode.put("message", "Contact and company blacklisted successfully");
    return responseNode;
  }

  /**
   * Add a company.
   *
   * @param company The company to add.
   * @return The company added.
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"E"})
  public ObjectNode addCompany(CompanyDTO company) {
    if (company.getName() == null || company.getName().isEmpty()) {
      throw new WebApplicationException("Invalid company name", Response.Status.BAD_REQUEST);
    }
    if (company.getAddress() == null || company.getAddress().isEmpty()) {
      throw new WebApplicationException("Invalid company address", Response.Status.BAD_REQUEST);
    }
    if (company.getCity() == null || company.getCity().isEmpty()) {
      throw new WebApplicationException("Invalid company city", Response.Status.BAD_REQUEST);
    }

    myCompanyUcc.addCompany(company);

    ObjectNode responseNode = jsonMapper.createObjectNode();
    responseNode.put("message", "Company  created successfully");
    return responseNode;
  }


}