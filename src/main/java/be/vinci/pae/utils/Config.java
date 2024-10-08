package be.vinci.pae.utils;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Provides a utility class for loading and accessing configuration properties from a specified
 * file. This class uses a {@code Properties} object to load and store configuration settings.
 */
public class Config {

  private static Properties props;

  /**
   * Loads configuration properties from the specified file. Initializes the {@code props} object
   * with properties loaded from the file. If an error occurs during file reading, it throws a
   * {@code WebApplicationException} with the appropriate error message.
   *
   * @param file the path to the configuration file to be loaded.
   */
  public static void load(String file) {
    props = new Properties();
    try (InputStream input = new FileInputStream(file)) {
      props.load(input);
    } catch (IOException e) {
      throw new WebApplicationException(
          Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type("text/plain")
              .build());
    }
  }

  /**
   * Retrieves the value of a property as a string.
   *
   * @param key The key of the property to retrieve.
   * @return The value of the property as a string, or {@code null} if the property is not found.
   */
  public static String getProperty(String key) {
    return props.getProperty(key);
  }

  /**
   * Retrieves the value of a property as an integer.
   *
   * @param key The key of the property to retrieve.
   * @return The integer value of the property.
   * @throws NumberFormatException If the property value cannot be parsed as an integer.
   */
  public static Integer getIntProperty(String key) {
    return Integer.parseInt(props.getProperty(key));
  }

  /**
   * Retrieves the value of a property as a boolean.
   *
   * @param key The key of the property to retrieve.
   * @return The boolean value of the property, false if the property is not found or not a valid
   */
  public static boolean getBoolProperty(String key) {
    return Boolean.parseBoolean(props.getProperty(key));
  }

}