package be.vinci.pae.presentation.filters;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class CORSFilter implements ContainerResponseFilter {

  @Override
  public void filter(ContainerRequestContext requestContext,
      ContainerResponseContext responseContext) throws IOException {
    responseContext.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:3000");
    responseContext.getHeaders()
        .add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
    responseContext.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
    responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
  }
}