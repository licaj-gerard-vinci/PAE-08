package be.vinci.pae.presentation;

import be.vinci.pae.business.manager.ManagerDTO;
import be.vinci.pae.business.manager.ManagerUCC;
import be.vinci.pae.presentation.filters.Authorize;
import be.vinci.pae.presentation.filters.Log;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * The ManagerResource class provides RESTful web resources using JAX-RS annotations.
 * It handles HTTP requests related to managers.
 */
@Singleton
@Path("managers")
@Log
public class ManagerResource {

  @Inject
  private ManagerUCC myManagerUCC;

  /**
   * Retrieves a list of managers for a specific company.
   *
   * @param companyId the ID of the company for which to retrieve managers
   * @return a list of ManagerDTO objects representing the managers of the company
   * If no managers are found, an empty list is returned.
   *
   * This method is a GET request and can be accessed at the "/{companyId}" path.
   * It produces JSON.
   * The method is protected with the @Authorize annotation, meaning the client must be authenticated to access this endpoint.
   */
  @GET
  @Path("/{companyId}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<ManagerDTO> getManagers(
      @PathParam("companyId") int companyId) {
    List<ManagerDTO> managerDTOs = myManagerUCC.getManagers(companyId);

    if (managerDTOs == null || managerDTOs.isEmpty()) {
      managerDTOs = new ArrayList<>();
    }
    return managerDTOs;
  }
}
