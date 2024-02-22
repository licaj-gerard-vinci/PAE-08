package be.vinci.pae.donnees;

import be.vinci.pae.bd.Configuration;

import be.vinci.pae.business.UserDTO;
import be.vinci.pae.business.UserImpl;

import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Provides services related to user data management, including retrieval, creation, login, and
 * registration of users. Utilizes a JSON-based storage mechanism and JWT for authentication
 * tokens.
 */
public class UserDataServiceImpl implements UserDataService {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();


  /**
   * Attempts to log in a user with the provided login and password.
   *
   * @param password the user's password.
   * @return an ObjectNode containing the user's token, ID, and login if successful; null otherwise.
   */
  @Override
  public ObjectNode login(String email) {
    Configuration configuration = new Configuration();
    try (Connection connection = configuration.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
            "SELECT id_utilisateur,nom, prenom, numero_tel, date_inscription, role_utilisateur FROM utilisateur WHERE email = ?")) {

      stmt.setString(1, email);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          UserDTO user = new UserImpl();

          // Remplir l'objet user avec toutes les données nécessaires de la base de données.
          user.setId(rs.getInt("id_utilisateur"));
          user.setEmail(email); // Supposant que login est l'email.
          user.setNom(rs.getString("nom"));
          user.setPrenom(rs.getString("prenom"));
          user.setNumTel(rs.getString("numero_tel"));
          user.setDateInscription(rs.getDate("date_inscription"));
          user.setRole(rs.getString("role_utilisateur").charAt(0));
          System.out.println(user);

          // Générez le token JWT pour l'utilisateur ici
          return generateTokenForUser(user);
        }

      }
    } catch (SQLException e) {
      e.printStackTrace();
      // Gérer l'exception correctement, par exemple en renvoyant une réponse d'erreur.
    }
    return null; // Ou lancez une exception pour indiquer une authentification échouée
  }


  /**
   * Generates a JWT token for the given user.
   *
   * @param user the user for whom to generate the token.
   * @return an ObjectNode containing the token, user ID, and login.
   */
  @Override
  public ObjectNode generateTokenForUser(UserDTO user) {
    String token = JWT.create()
        .withIssuer("auth0")
        .withClaim("user", user.getId())
        .sign(jwtAlgorithm);
    return jsonMapper.createObjectNode()
        .put("token", token)
        .put("id", user.getId())
        .put("login", user.getEmail());
  }
}