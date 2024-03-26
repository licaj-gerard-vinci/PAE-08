package be.vinci.pae.presentation.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class SQLException extends WebApplicationException {
  public SQLException(String message) {
    super(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(message)
            .type("text/plain")
            .build());
  }
}
