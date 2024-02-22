package be.vinci.pae.business;

public interface User extends UserDTO {

  boolean checkPassword(String password);

  String hashPassword(String password);

  @Override
  String toString();
}
