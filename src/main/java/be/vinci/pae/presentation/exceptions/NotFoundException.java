package be.vinci.pae.presentation.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class NotFoundException extends WebApplicationException {
  public NotFoundException(String message) {
    super(Response.status(Response.Status.NOT_FOUND)
            .entity(message)
            .type("text/plain")
            .build());
  }

    public NotFoundException(Throwable cause) {
        super(Response.status(Response.Status.NOT_FOUND)
                .entity(cause.getMessage())
                .type("text/plain")
                .build());
    }

    public NotFoundException() {
        super(Response.status(Response.Status.NOT_FOUND)
                .build());
    }

}
