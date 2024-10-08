package be.vinci.pae.presentation.filters;

import jakarta.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Annotation to bind {@link AuthorizationRequestFilter} for resource methods/classes needing
 * authentication.
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {

  /**
   * Roles allowed to access the resource.
   *
   * @return the roles allowed to access the resource.
   */
  String[] roles() default {};
}
