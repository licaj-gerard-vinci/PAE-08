package be.vinci.pae.presentation.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class UnhautorizedException extends WebApplicationException {
    /**
     * Constructs a new exception with the specified detail message. The cause is not initialized, and
     * may subsequently be initialized by a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the
     *                {@link #getMessage()} method.
     */
    public UnhautorizedException(String message) {
        super(Response.status(Response.Status.NOT_FOUND)
                .entity(message)
                .type("text/plain")
                .build());
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public UnhautorizedException(Throwable cause) {
        super(Response.status(Response.Status.NOT_FOUND)
                .entity(cause.getMessage())
                .type("text/plain")
                .build());
    }

    /**
     * Constructs a new exception with {@code null} as its detail message. The cause is not
     * initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public UnhautorizedException() {
        super(Response.status(Response.Status.NOT_FOUND)
                .build());
    }
}
