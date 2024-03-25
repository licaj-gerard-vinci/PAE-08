package be.vinci.pae.business.contact;

/**
 * The Interface Contact.
 */
public interface Contact {

  /**
   * check if a contact can be updated to the 'taken' state.
   *
   * @param contact the contact to update
   */
  boolean checkContactTaken(ContactDTO contact);

  /**
   * check if a contact can be updated to the 'accepted' state.
   *
   * @param contact the contact to update
   */
  boolean checkContactAccepted(ContactDTO contact);

  /**
   * check if a contact can be updated to the 'refused' state.
   *
   * @param contact the contact to update
   */
  boolean checkContactRefused(ContactDTO contact);

  /**
   * Updates a contact to the 'unsupervised' state.
   *
   * @param contact the contact to update
   */
  boolean checkContactUnsupervised(ContactDTO contact);

  /**
   * Checks if a contact exists between a user and a company.
   *
   * @param idUser the ID of the user
   * @param idEntreprise the ID of the company
   * @return true if the contact exists, false otherwise
   */
  ContactDTO checkContact(int idUser, int idEntreprise);

  /**
   * Checks if a contact exists between a user and a company and if the contact is in a specific state.
   *
   * @param idUser the ID of the user
   * @param idEntreprise the ID of the company
   * @param etat the state of the contact
   * @return true if the contact exists and is in the specified state, false otherwise
   */
  boolean checkContactAndState(int idUser, int idEntreprise, String etat);

}
