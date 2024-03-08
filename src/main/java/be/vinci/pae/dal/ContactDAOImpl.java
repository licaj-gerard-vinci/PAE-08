package be.vinci.pae.dal;

import be.vinci.pae.business.ContactDetailledDTO;
import be.vinci.pae.business.Factory;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class ContactDAOImpl.
 */
public class ContactDAOImpl implements ContactDAO {

  @Inject
  private DALService dalService;

  @Inject
  private Factory factory;

  /**
   * Gets the contacts.
   *
   * @param id the user ID.
   * @return the contacts.
   */
  @Override
  public List<ContactDetailledDTO> getContacts(int id) {
    String query =
        "SELECT ent.nom, ent.appellation, con.etat_contact, con.lieux_rencontre, con.raison_refus, con.id_contact "
            +
            "FROM pae.utilisateurs AS usr " +
            "JOIN pae.contacts AS con ON usr.id_utilisateur = con.utilisateur " +
            "JOIN pae.entreprises AS ent ON con.entreprise = ent.id_entreprise " +
            "WHERE usr.id_utilisateur = ?";

    List<ContactDetailledDTO> contacts = new ArrayList<>();

    try (PreparedStatement statement = dalService.preparedStatement(query)) {
      statement.setInt(1, id);
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          contacts.add(rsToDetailedContact(rs));
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return contacts;
  }

  /**
   * Rs to detailed contact.
   *
   * @param rs the rs
   * @return the contact detailled DTO
   * @throws SQLException the SQL exception
   */
  private ContactDetailledDTO rsToDetailedContact(ResultSet rs) throws SQLException {
    ContactDetailledDTO contact = factory.getDetailledContactDTO();
    contact.setId(rs.getInt("id_contact"));
    contact.setEtatContact(rs.getString("etat_contact"));
    contact.setLieuxRencontre(rs.getString("lieux_rencontre"));
    contact.setRaisonRefus(rs.getString("raison_refus"));
    contact.setNomEntreprise(rs.getString("nom"));
    contact.setAppellation(rs.getString("appellation"));
    return contact;
  }
}
