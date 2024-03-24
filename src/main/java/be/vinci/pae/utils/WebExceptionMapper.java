package be.vinci.pae.utils;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;


/**
 * Provides a utility class for mapping exceptions to HTTP responses. This class uses the
 * {@code ExceptionMapper} interface to map exceptions to HTTP responses.
 */
@Provider
public class WebExceptionMapper implements ExceptionMapper<Throwable> {

  /**
   * Maps an exception to an HTTP response. If the exception is a {@code WebApplicationException},
   * it returns the response from the exception. Otherwise, it returns an internal server error
   * response with the exception message.
   *
   * @param exception the exception to be mapped.
   * @return the HTTP response mapped from the exception.
   */
  private final Logger log = Logger.getLogger("WebExceptionMapper");


  @Override
  public Response toResponse(Throwable exception) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    log.severe(sw.toString());
    exception.printStackTrace(pw);
    if (exception instanceof WebApplicationException) {
      return Response.status(((WebApplicationException) exception).getResponse().getStatus())
          .entity(exception.getMessage())
          .build();
    }
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity(exception.getMessage())
        .build();
  }
}
