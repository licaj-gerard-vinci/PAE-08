package be.vinci.pae.presentation;

import be.vinci.pae.business.manager.ManagerUCC;
import be.vinci.pae.business.manager.ManagerDTO;
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

@Singleton
@Path("managers")
@Log
public class ManagerResource {

  @Inject
  private ManagerUCC myManagerUCC;

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
