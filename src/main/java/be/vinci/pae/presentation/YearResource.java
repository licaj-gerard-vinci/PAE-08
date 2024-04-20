package be.vinci.pae.presentation;

import be.vinci.pae.business.year.YearDTO;
import be.vinci.pae.business.year.YearUCC;
import be.vinci.pae.presentation.filters.Authorize;
import be.vinci.pae.presentation.filters.Log;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

/**
 * YearResource.
 */
@Singleton
@Path("years")
@Log
public class YearResource {

  @Inject
  private YearUCC myYearUcc;

  /**
   * Get all years.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"A", "P"})
  public List<YearDTO> getYears() {
    return myYearUcc.getAllAcademicYears();
  }
}
