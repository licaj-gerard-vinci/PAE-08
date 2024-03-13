package be.vinci.pae.presentation;

import be.vinci.pae.business.EntrepriseDTO;
import be.vinci.pae.business.EntrepriseUCC;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

/**
 * The {@code EntrepriseResource} class
 * represents a resource for managing entreprise-related operations.
 * It is annotated with {@code Singleton} and {@code Path("ent")}.
 */
@Singleton
@Path("ent")
public class EntrepriseResource {

  @Inject
  private EntrepriseUCC myEntrepriseUcc;

/**
 * Retrieves all entreprises.
 *
 * @return the list containing all entreprises.
 */
  @GET
  @Path("all")
  @Produces(MediaType.APPLICATION_JSON)
  public List<EntrepriseDTO> getAllEntreprises() {
    return myEntrepriseUcc.getEntreprises();
  }
}
