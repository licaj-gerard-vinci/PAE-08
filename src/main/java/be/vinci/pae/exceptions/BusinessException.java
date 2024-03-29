package be.vinci.pae.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

/**
 * Represents an exception that is thrown when a resource is not found. This exception extends the
 * WebApplicationException class.
 */
public class BusinessException extends WebApplicationException {

  /**
   * Constructs a new exception with the specified detail message. The cause is not initialized, and
   * may subsequently be initialized by a call to {@link #initCause}.
   *
   * @param message the detail message. The detail message is saved for later retrieval by the
   */
  public BusinessException(String message) {
    super(Response.status(Response.Status.BAD_REQUEST)
        .entity(message)
        .type("text/plain")
        .build());
  }

  /**
   * Constructs a new exception with the specified detail message and cause.
   *
   * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
   */
  public BusinessException(Throwable cause) {
    super(Response.status(Response.Status.BAD_REQUEST)
        .entity(cause.getMessage())
        .type("text/plain")
        .build());
  }

  /**
   * Constructs a new exception with {@code null} as its detail message. The cause is not
   * initialized, and may subsequently be initialized by a call to {@link #initCause}.
   */
  public BusinessException() {
    super(Response.status(Response.Status.BAD_REQUEST)
        .build());
  }
}

