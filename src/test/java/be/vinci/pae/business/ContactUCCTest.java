package be.vinci.pae.business;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.dal.contact.ContactDAO;
import be.vinci.pae.presentation.exceptions.NotFoundException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import utils.ApplicationBinderTest;

public class ContactUCCTest {

  private ContactUCC contactUCC;
  private Factory factory;
  private ContactDAO contactDAO;

  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    contactUCC = locator.getService(ContactUCC.class);
    factory = locator.getService(Factory.class);
    contactDAO = locator.getService(ContactDAO.class);
  }

  @Test
  @DisplayName("Test insert contact")
  void testInsertContactDefault() {
    EntrepriseDTO entreprise = factory.getEntrepriseDTO();
    UserDTO user = factory.getPublicUser();
    ContactDTO contact = Mockito.mock(ContactDTO.class);
    Mockito.when(contact.getUtilisateur()).thenReturn(user);
    Mockito.when(contact.getEntreprise()).thenReturn(entreprise);
    Mockito.when(contact.getEtatContact()).thenReturn("initié");

    Mockito.when(contactDAO.checkContactExists(user.getId(), entreprise.getId()))
        .thenReturn(contact);
  }

  @Test
  @DisplayName("Test insert contact not initiated")
  void testInsertContactNotInitiated() {
    ContactDTO contact = Mockito.mock(ContactDTO.class);
    UserDTO user = Mockito.mock(UserDTO.class);
    EntrepriseDTO entreprise = Mockito.mock(EntrepriseDTO.class);
    when(user.getId()).thenReturn(8);
    when(entreprise.getId()).thenReturn(3);
    when(contact.getUtilisateur()).thenReturn(user);
    when(contact.getEntreprise()).thenReturn(entreprise);
    when(contact.getEtatContact()).thenReturn("non initié");
    assertThrows(NotFoundException.class, () -> contactUCC.insertContact(contact));
  }


}
