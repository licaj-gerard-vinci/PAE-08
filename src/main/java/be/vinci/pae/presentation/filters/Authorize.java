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
  String value() default "";
}
