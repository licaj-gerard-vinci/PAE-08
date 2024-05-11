package be.vinci.pae.business.contact;

/**
 * The Interface Contact.
 */
public interface Contact extends ContactDTO {

  /**
   * Check if a contact can be updated to the 'taken' state.
   *
   * @param actualState The actual state.
   * @param expectedState The expected state of the contact.
   * @return true if the contact can be updated, false otherwise.
   */
  boolean checkState(String actualState, String expectedState);
}
