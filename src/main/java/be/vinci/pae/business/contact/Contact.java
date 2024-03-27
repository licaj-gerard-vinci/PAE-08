package be.vinci.pae.business.contact;

/**
 * The Interface Contact.
 */
public interface Contact {

  /**
   * check if a contact can be updated to the 'taken' state.
   *
   * @param contact the contact to update
   * @return true if the contact can be updated, false otherwise
   */
  boolean checkContactTaken(ContactDTO contact);

  /**
   * check if a contact can be updated to the 'accepted' state.
   *
   * @param contact the contact to update
   * @return true if the contact can be updated, false otherwise
   */
  boolean checkContactAccepted(ContactDTO contact);

  /**
   * check if a contact can be updated to the 'refused' state.
   *
   * @param contact the contact to update
   * @return true if the contact can be updated, false otherwise
   */
  boolean checkContactRefused(ContactDTO contact);

  /**
   * Updates a contact to the 'unsupervised' state.
   *
   * @param contact the contact to update
   * @return true if the contact can be updated, false otherwise
   */
  boolean checkContactUnsupervised(ContactDTO contact);

  /**
   * Checks if a contact exists between a user and a company.
   *
   * @param idUser       the ID of the user
   * @param idCompany the ID of the company
   * @return the contactDTO of the contact if it exists, null otherwise
   */
  ContactDTO checkContact(int idUser, int idCompany);

  /**
   * Checks if a contact exists between a user and a company and if the contact is in a specific
   * state.
   *
   * @param idUser       the ID of the user
   * @param idCompany the ID of the company
   * @param state         the state of the contact
   * @return true if the contact exists and is in the specified state, false otherwise
   */
  boolean checkContactAndState(int idUser, int idCompany, String state);
}
