package be.vinci.pae.presentation.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class AuthorisationException extends WebApplicationException {
  public AuthorisationException(String message) {
    super(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(message)
            .type("text/plain")
            .build());
  }

     public AuthorisationException(Throwable cause) {
          super(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                 .entity(cause.getMessage())
                 .type("text/plain")
                 .build());
     }
     public AuthorisationException() {
         super(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                 .build());
     }
}
