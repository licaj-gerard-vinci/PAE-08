package be.vinci.pae.presentation.filters;

import jakarta.ws.rs.core.Response;

import static jakarta.ws.rs.core.Response.*;

public class UserAuthenticationException extends RuntimeException{
    public UserAuthenticationException() {
        super(status(Status.UNAUTHORIZED).build().toString());

    }
    public UserAuthenticationException(String message) {
        super(Response.status(Response.Status.UNAUTHORIZED)
                .entity(message)
                .type("text/plain")
                .build().toString());
    }

    public UserAuthenticationException(Throwable cause) {
        super(Response.status(Response.Status.UNAUTHORIZED)
                .entity(cause.getMessage())
                .type("text/plain")
                .build().toString());
    }
}
