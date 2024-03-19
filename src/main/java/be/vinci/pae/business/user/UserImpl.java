package be.vinci.pae.business.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.Date;
import org.mindrot.jbcrypt.BCrypt;


/**
 * Creating User object.
 */
public class UserImpl implements User {

  private int id;
  private String email;
  private String nom;
  private String prenom;
  private String numTel;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yy hh:mm:ss")
  private Date dateInscription;
  private String role;
  private String password;


  /**
   * Gets the user's login.
   *
   * @return the user's login.
   */
  @Override
  public String getEmail() {
    return email;
  }

  /**
   * Sets the user's login.
   *
   * @param email the new login for the user.
   */
  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets the user's ID.
   *
   * @return the user's ID.
   */
  @Override
  public int getId() {
    return id;
  }

  /**
   * Sets the user's ID.
   *
   * @param id the new ID for the user.
   */
  @Override
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the user's hashed password.
   *
   * @return the hashed password.
   */
  @Override
  public String getPassword() {
    return password;
  }

  /**
   * Sets the user's password after hashing it.
   *
   * @param password the new password to hash and set.
   */
  @Override
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Gets the user's last name.
   *
   * @return the user's last name.
   */
  @Override
  public String getNom() {
    return nom;
  }

  /**
   * Sets the user's last name.
   *
   * @param nom the new last name for the user.
   */
  @Override
  public void setNom(String nom) {
    this.nom = nom;
  }

  /**
   * Gets the user's first name.
   *
   * @return the user's first name.
   */
  @Override
  public String getPrenom() {
    return prenom;
  }

  /**
   * Sets the user's first name.
   *
   * @param prenom the new first name for the user.
   */
  @Override
  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  /**
   * Gets the user's phone number.
   *
   * @return the user's phone number.
   */
  @Override
  public String getNumTel() {
    return numTel;
  }

  /**
   * Sets the user's phone number.
   *
   * @param numTel the new phone number for the user.
   */
  @Override
  public void setNumTel(String numTel) {
    this.numTel = numTel;
  }

  /**
   * Gets the user's registration date.
   *
   * @return the user's registration date.
   */
  @Override
  public Date getDateInscription() {
    return dateInscription;
  }

  /**
   * Sets the user's registration date.
   *
   * @param dateInscription the new registration date for the user.
   */
  @Override
  public void setDateInscription(Date dateInscription) {
    this.dateInscription = dateInscription;
  }

  /**
   * Gets the user's role.
   *
   * @return the user's role.
   */
  @Override
  public String getRole() {
    return role;
  }

  /**
   * Sets the user's role.
   *
   * @param role the new role for the user.
   */
  @Override
  public void setRole(String role) {
    this.role = role;
  }

  /**
   * Checks if the provided password matches the user's hashed password.
   *
   * @param password the password to check.
   * @return true if the password matches, false otherwise.
   */
  @Override
  public boolean checkPassword(String password) {
    return BCrypt.checkpw(password, this.password);
  }

  /**
   * Hashes a password.
   *
   * @param password the password to hash.
   * @return the hashed password.
   */
  @Override
  public String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  /**
   * return string of attributs.
   */
  @Override
  public String toString() {
    return "id=" + id + ", email='" + email + '\'' + ", nom='" + nom + '\'' + ", prenom='"
            + prenom + '\'' + ", numTel='" + numTel + '\'' + ", dateInscription=" + dateInscription
            + ", role='" + role + '\'' + ", password='" + password + '\'' + '}';
  }
}