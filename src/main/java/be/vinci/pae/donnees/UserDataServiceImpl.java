package be.vinci.pae.services;

/**
 * return
 */

public interface UserDataServiceImpl {
/*
  @Inject
  private Factory factory;

  @Override
  public UserDTO getOneByEmail(String email) {
    UserDTO user = factory.getUserDTO();
    try {
      PreparedStatement getUser = factory.getGetUser();
      getUser.setString(1, email);
      ResultSet rs = getUser.executeQuery();
      while (rs.next()) {
        user.setId_utilisateurr(rs.getInt("id_utilisateur"));
        user.setNom(rs.getString("nom"));
        user.setPrenom(rs.getString("prenom"));
        user.setEmail(rs.getString("email"));
        user.setMot_de_passe(rs.getString("mot_de_passe"));
        user.setNumero_tel(rs.getString("numero_tel"));
        user.setDate_inscription(rs.getDate("date_inscription"));
        user.setRole_utilisateur(UserDTO.Role.valueOf(rs.getString("role_utilisateur")));
        return user;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

*/
}
