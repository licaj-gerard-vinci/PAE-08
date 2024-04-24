package be.vinci.pae.utils;

import be.vinci.pae.exceptions.FatalException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


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
  private final Logger log = LogManager.getLogger("WebExceptionMapper");


  @Override
  public Response toResponse(Throwable exception) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    exception.printStackTrace(pw);
    if (exception instanceof FatalException) {
      log.fatal(sw.toString());
      return Response.status(((WebApplicationException) exception).getResponse().getStatus())
          .entity(exception.getMessage()).build();
    }
    if (exception instanceof WebApplicationException) {
      log.warn(sw.toString());
      return Response.status(((WebApplicationException) exception).getResponse().getStatus())
          .entity(exception.getMessage()).build();
    }
    log.error(sw.toString());
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity(exception.getMessage())
        .build();
  }
}
