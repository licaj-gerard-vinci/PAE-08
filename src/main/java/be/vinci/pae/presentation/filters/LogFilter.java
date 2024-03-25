package be.vinci.pae.presentation.filters;

import jakarta.inject.Singleton;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Filter that logs the response status.
 */
@Singleton
@Provider
@Log
public class LogFilter implements ContainerResponseFilter {

  private static final Logger log = LogManager.getLogger(LogFilter.class);
  
  @Override
  public void filter(ContainerRequestContext requestContext,
      ContainerResponseContext responseContext)
      throws IOException {
    log.info("Response: " + responseContext.getStatus() + " " + requestContext.getMethod() + " "
        + requestContext.getUriInfo().getPath());
  }
}