package be.vinci.pae.donnees;

import be.vinci.pae.business.User;
import be.vinci.pae.business.UserImpl;
import be.vinci.pae.donnees.utils.Json;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;

/**
 * Provides services related to user data management, including retrieval, creation, login, and
 * registration of users. Utilizes a JSON-based storage mechanism and JWT for authentication
 * tokens.
 */
public class UserDataService {

  private static final String COLLECTION_NAME = "users";
  private static Json<User> jsonDB = new Json<>(User.class);
  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();

  /**
   * Retrieves all users from the JSON database.
   *
   * @return a list of all users.
   */
  public List<User> getAll() {
    return jsonDB.parse(COLLECTION_NAME);
  }

  /**
   * Retrieves a single user by their ID.
   *
   * @param id the user's ID.
   * @return the user with the specified ID or null if not found.
   */
  public User getOne(int id) {
    return getAll().stream().filter(user -> user.getId() == id).findAny().orElse(null);
  }

  /**
   * Retrieves a single user by their login.
   *
   * @param login the user's login.
   * @return the user with the specified login or null if not found.
   */
  public User getOne(String login) {
    return getAll().stream().filter(user -> user.getEmail().equals(login)).findAny().orElse(null);
  }

  /**
   * Creates and stores a new user in the JSON database.
   *
   * @param user the user to create.
   * @return the created user with an assigned ID.
   */
  public User createOne(User user) {
    List<User> users = getAll();
    user.setId(nextItemId());
    users.add(user);
    jsonDB.serialize(users, COLLECTION_NAME);
    return user;
  }

  /**
   * Generates the next available user ID.
   *
   * @return the next available ID.
   */
  public int nextItemId() {
    List<User> users = getAll();
    return users.isEmpty() ? 1 : users.get(users.size() - 1).getId() + 1;
  }

  /**
   * Attempts to log in a user with the provided login and password.
   *
   * @param login    the user's login.
   * @param password the user's password.
   * @return an ObjectNode containing the user's token, ID, and login if successful; null otherwise.
   */
  public ObjectNode login(String login, String password) {
    User user = getOne(login);
    if (user != null && user.checkPassword(password)) {
      return generateTokenForUser(user);
    }
    return null;
  }

  /**
   * Registers a new user with the provided login and password.
   *
   * @param login    the desired login for the new user.
   * @param password the password for the new user.
   * @return an ObjectNode containing the new user's token, ID, and login if successful; null
   * otherwise.
   * <p>
   * Line continuation have incorrect indentation level, expected level should be 4.
   */
  public ObjectNode register(String login, String password) {
    if (getOne(login) != null) { // User already exists
      return null;
    }

    User newUser = new UserImpl();
    newUser.setEmail(login);
    newUser.setPassword(newUser.hashPassword(password));
    User registeredUser = createOne(newUser);
    return generateTokenForUser(registeredUser);
  }

  /**
   * Generates a JWT token for the given user.
   *
   * @param user the user for whom to generate the token.
   * @return an ObjectNode containing the token, user ID, and login.
   */
  private ObjectNode generateTokenForUser(User user) {
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