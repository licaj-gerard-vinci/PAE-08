package be.vinci.pae.dal;

import be.vinci.pae.bd.Configuration;
import be.vinci.pae.business.UserDTO;
import be.vinci.pae.business.UserImpl;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Provides services related to user data management, including retrieval, creation, login, and
 * registration of users. Utilizes a JSON-based storage mechanism and JWT for authentication
 * tokens.
 */
public class UserDAOImpl implements UserDAO {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();
  @Inject
  private DALServiceImpl ps;

  /**
   * Retrieves a single user by their ID.
   *
   * @param id the user's ID.
   * @return the user with the specified ID or null if not found.
   */
  @Override
  public UserDTO getOne(int id) {
    return null;
  }

  /**
   * Retrieves a single user by their login.
   *
   * @param email the user's login.
   * @return the user with the specified login or null if not found.
   */
  @Override
  public UserDTO getOneByEmail(String email) {
    return null;
  }

  /**
   * Attempts to log in a user with the provided login and password.
   *
   * @param password the user's password.
   * @return an ObjectNode containing the user's token, ID, and login if successful; null otherwise.
   */
  @Override
  public UserDTO login(String email, String password) {
    Configuration configuration = new Configuration();
    try (Connection connection = configuration.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
            "SELECT id_utilisateur, mot_de_passe, nom, prenom, numero_tel, date_inscription, anne_academique, role_utilisateur FROM bdpae.utilisateur WHERE email = ?")) {

      stmt.setString(1, email);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          String dbPassword = rs.getString("mot_de_passe");
          if (BCrypt.checkpw(password, dbPassword)) {
            UserDTO user = new UserImpl();

            // Remplir l'objet user avec toutes les données nécessaires de la base de données.
            user.setId(rs.getInt("id_utilisateur"));
            user.setEmail(email); // Supposant que login est l'email.
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setNumTel(rs.getString("numero_tel"));
            user.setDateInscription(rs.getDate("date_inscription"));
            user.setAnneeAcademique(rs.getString("anne_academique"));
            user.setRole(rs.getString("role_utilisateur").charAt(0));

            // Générez le token JWT pour l'utilisateur ici
            return user;
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      // Gérer l'exception correctement, par exemple en renvoyant une réponse d'erreur.
    }
    return null; // Ou lancez une exception pour indiquer une authentification échouée
  }
}