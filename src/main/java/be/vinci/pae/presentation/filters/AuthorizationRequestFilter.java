package be.vinci.pae.presentation.filters;

import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserUCC;
import be.vinci.pae.exceptions.UnauthorizedException;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;


/**
 * Implements a request filter to authenticate users via JWT. Validates the token in the
 * Authorization header, allowing access for valid tokens and blocking access or throwing errors for
 * invalid or missing tokens.
 */
@Singleton
@Provider
@Authorize
public class AuthorizationRequestFilter implements ContainerRequestFilter {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final JWTVerifier jwtVerifier = JWT.require(this.jwtAlgorithm).withIssuer("auth0")
          .build();
  @Inject
  private UserUCC myUserUCC;

  @Context
  private ResourceInfo resourceInfo;

  @Override
  public void filter(@Context ContainerRequestContext requestContext) throws IOException {
    String token = requestContext.getHeaderString("Authorization");
    if (token == null) {
      throw new TokenDecodingException("Missing token");
    } else {
      DecodedJWT decodedToken = null;
      try {
        decodedToken = this.jwtVerifier.verify(token);
        if (decodedToken.getExpiresAt().before(new Date())) {
          throw new TokenDecodingException("Token expired");
        }
      } catch (Exception e) {
        throw new TokenDecodingException(e.getMessage());
      }
      UserDTO authenticatedUser = myUserUCC.getOne(decodedToken.getClaim("user").asInt());
      if (authenticatedUser == null) {
        throw new NotFoundException("User not found");
      }

      Method m = resourceInfo.getResourceMethod();
      Authorize authorize = m.getAnnotation(Authorize.class);
      String[] roles = authorize.roles();
      if (!Arrays.asList(roles).contains(authenticatedUser.getRole())) {
        throw new UnauthorizedException("Unauthorized");
      }

      requestContext.setProperty("user", authenticatedUser);
    }
  }

}

