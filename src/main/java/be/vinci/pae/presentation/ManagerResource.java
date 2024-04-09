package be.vinci.pae.presentation;

import be.vinci.pae.business.responsable.ResponsableDTO;
import be.vinci.pae.business.responsable.ResponsableUCC;
import be.vinci.pae.exceptions.NotFoundException;
import be.vinci.pae.presentation.filters.Authorize;
import be.vinci.pae.presentation.filters.Log;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * The ManagerResource class provides RESTful web resources using JAX-RS annotations. It handles
 * HTTP requests related to managers.
 */
@Singleton
@Path("managers")
@Log
public class ManagerResource {

  @Inject
  private ResponsableUCC myManagerUCC;

  /**
   * Retrieves a list of managers for a specific company.
   *
   * @param companyId the ID of the company for which to retrieve managers.
   * @return a list of ManagerDTO objects representing the managers of the company.
   */
  @GET
  @Path("/{companyId}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<ResponsableDTO> getManagers(
      @PathParam("companyId") int companyId) {
    List<ResponsableDTO> managerDTOs = myManagerUCC.getManagers(companyId);

    if (managerDTOs == null || managerDTOs.isEmpty()) {
      managerDTOs = new ArrayList<>();
    }
    return managerDTOs;
  }

  /**
   * Adds a manager to the database.
   *
   * @param manager the manager to add.
   */
  @POST
  @Path("/insert")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public void addManager(ResponsableDTO manager) {
    if (manager == null) {
      throw new NotFoundException("Manager cannot be null");
    }
    if (manager.getNom() == null || manager.getPrenom() == null
        || manager.getIdEntreprise() == 0) {
      throw new NotFoundException("Manager information is incomplete");
    }
    myManagerUCC.addManager(manager);
  }
}
