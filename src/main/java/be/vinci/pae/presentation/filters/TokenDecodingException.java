package be.vinci.pae.presentation.filters;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

/**
 * Represents an exception that is thrown when a token cannot be decoded. This exception extends the
 * WebApplicationException class.
 */
public class TokenDecodingException extends WebApplicationException {

  /**
   * Constructs a new exception with a default message and a status code of 401 (Unauthorized).
   */
  public TokenDecodingException() {
    super(Response.status(Response.Status.UNAUTHORIZED)
        .build());
  }

  /**
   * Constructs a new exception with the specified detail message and a status code of 401
   * (Unauthorized).
   *
   * @param message the detail message. The detail message is saved for later retrieval by the
   *                {@link #getMessage()} method.
   */
  public TokenDecodingException(String message) {
    super(Response.status(Response.Status.UNAUTHORIZED)
        .entity(message)
        .type("text/plain")
        .build());
  }

  /**
   * Constructs a new exception with the specified cause and a status code of 401 (Unauthorized).
   *
   * @param cause the Throwable that caused this exception to be thrown.
   */
  public TokenDecodingException(Throwable cause) {
    super(Response.status(Response.Status.UNAUTHORIZED)
        .entity(cause.getMessage())
        .type("text/plain")
        .build());
  }

}
