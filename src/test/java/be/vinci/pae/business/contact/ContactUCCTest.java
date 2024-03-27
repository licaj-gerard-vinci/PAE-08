package be.vinci.pae.business.contact;

import be.vinci.pae.business.entreprise.EntrepriseDTO;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.factory.Factory;
import be.vinci.pae.dal.contact.ContactDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import utils.ApplicationBinderTest;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void testInsertContact() {
        ContactDTO contact = Mockito.mock(ContactDTO.class);
        UserDTO user = Mockito.mock(UserDTO.class);
        EntrepriseDTO entreprise = Mockito.mock(EntrepriseDTO.class);
        when(user.getId()).thenReturn(8);
        when(entreprise.getId()).thenReturn(3);
        when(contact.getUtilisateur()).thenReturn(user);
        when(contact.getEntreprise()).thenReturn(entreprise);
        when(contact.getEtatContact()).thenReturn("initié");
        contactUCC.insertContact(contact);
        verify(contactDAO, times(1)).insertContact(contact);
    }

    @Test
    void testInsertContactNotInitiated() {
        ContactDTO contact = Mockito.mock(ContactDTO.class);
        UserDTO user = Mockito.mock(UserDTO.class);
        EntrepriseDTO entreprise = Mockito.mock(EntrepriseDTO.class);
        when(user.getId()).thenReturn(8);
        when(entreprise.getId()).thenReturn(3);
        when(contact.getUtilisateur()).thenReturn(user);
        when(contact.getEntreprise()).thenReturn(entreprise);
        when(contact.getEtatContact()).thenReturn("non initié");
        assertThrows(IllegalArgumentException.class, () -> contactUCC.insertContact(contact));
    }
}
