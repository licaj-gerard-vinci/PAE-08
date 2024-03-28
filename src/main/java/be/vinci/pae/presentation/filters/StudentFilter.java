package be.vinci.pae.presentation.filters;

import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserUCC;
import be.vinci.pae.exceptions.UnhautorizedException;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;
import java.util.Date;

/**
 * The type Student filter.
 */
public class StudentFilter implements ContainerResponseFilter {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final JWTVerifier jwtVerifier = JWT.require(this.jwtAlgorithm).withIssuer("auth0")
      .build();
  @Inject
  private UserUCC myUserUCC;

  /**
   * Filter.
   *
   * @param requestContext          the request context
   * @param containerResponseContext the container response context
   * @throws IOException the io exception
   */
  @Override
  public void filter(ContainerRequestContext requestContext, ContainerResponseContext containerResponseContext) throws IOException {
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
        if(!authenticatedUser.getRole().equals("E")){
            throw new UnhautorizedException("Vous n'êtes pas étudiant FAUT METTRE LA BONNE EXCEPTION");
        }
        requestContext.setProperty("user", authenticatedUser);
    }
  }
}
