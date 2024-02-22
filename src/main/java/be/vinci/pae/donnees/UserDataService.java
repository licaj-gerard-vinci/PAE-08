package be.vinci.pae.donnees;

import be.vinci.pae.business.User;
import be.vinci.pae.business.UserDTO;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;

public interface UserDataService {

  List<User> getAll();

  User getOne(int id);

  User getOne(String login);

  User createOne(User user);

  int nextItemId();

  ObjectNode login(String email, String password);

  ObjectNode register(String login, String password);

  ObjectNode generateTokenForUser(UserDTO user);
}
