package be.vinci.pae.presentation;

import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.entreprise.EntrepriseUCC;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.presentation.filters.Authorize;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

/**
 * The {@code EntrepriseResource} class
 * represents a resource for managing entreprise-related operations.
 * It is annotated with {@code Singleton} and {@code Path("ent")}.
 */
@Singleton
@Path("entreprise")
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
      if(id <= 0) {
        return null; //Il faut retourner une exception ici, pas null
      }
      return myEntrepriseUcc.getEntreprise(id);
    }
}
