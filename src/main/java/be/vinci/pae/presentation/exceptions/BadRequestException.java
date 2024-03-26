package be.vinci.pae.presentation.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class BadRequestException extends WebApplicationException {
  public BadRequestException(String message) {
    super(Response.status(Response.Status.BAD_REQUEST)
            .entity(message)
            .type("text/plain")
            .build());
  }
}

