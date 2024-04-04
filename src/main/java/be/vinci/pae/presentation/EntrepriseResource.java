package be.vinci.pae.presentation;

import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.entreprise.EntrepriseUCC;
import be.vinci.pae.presentation.filters.Authorize;
import be.vinci.pae.presentation.filters.Log;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
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
public class EntrepriseResource {

  @Inject
  private EntrepriseUCC myEntrepriseUcc;

  /**
   * Retrieves all entreprises.
   *
   * @return the list containing all entreprises.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<EntrepriseDTO> getAllEntreprises() {
    return myEntrepriseUcc.getEntreprises();
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
  @Authorize
  public EntrepriseDTO getEntreprise(@PathParam("id") int id) {
    if (id <= 0) {
      throw new WebApplicationException("Invalid id", Response.Status.BAD_REQUEST);
    }
    return myEntrepriseUcc.getEntreprise(id);
  }

  /**
   * BlackList an company with the specified id.
   *
   * @param id The id of the company to update.
   * @return The updated company.
   */
  @PUT
  @Path("/{id}/blacklist")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public EntrepriseDTO blackListCompany(@PathParam("id") int id, EntrepriseDTO entreprise) {
    if (id <= 0) {
      throw new WebApplicationException("Invalid id", Response.Status.BAD_REQUEST);
    }
    if (entreprise.getMotivation_blacklist() == null) {
      throw new WebApplicationException("Invalid motivation", Response.Status.BAD_REQUEST);
    }
    System.out.println("toz " + entreprise.getMotivation_blacklist());
    myEntrepriseUcc.blackListCompany(entreprise);
    return entreprise;
  }
}