package be.vinci.pae.presentation;

import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.company.CompanyUCC;
import be.vinci.pae.presentation.filters.Authorize;
import be.vinci.pae.presentation.filters.Log;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * The {@code CompanyResource} class represents a resource for managing company-related
 * operations. It is annotated with {@code Singleton} and {@code Path("ent")}.
 */
@Singleton
@Path("company")
@Log
public class CompanyResource {

  @Inject
  private CompanyUCC myCompanyUcc;

  /**
   * Retrieves all companies.
   *
   * @return the list containing all companies.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<CompanyDTO> getAllCompanies() {
    return myCompanyUcc.getCompanies();
  }

  /**
   * Retrieves an company with the specified id.
   *
   * @param id The id of the company to retrieve.
   * @return The company with the specified id.
   */
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public CompanyDTO getCompany(@PathParam("id") int id) {
    if (id <= 0) {
      throw new WebApplicationException("Invalid id", Response.Status.BAD_REQUEST);
    }
    return myCompanyUcc.getCompany(id);
  }
}