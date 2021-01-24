package service;

import dao.ContactDao;
import model.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ContactServiceTest {

    public static final String CONTACT_NAME = "Juan";
    public static final String CONTACT_PHONENUMBER = "423323";
    private ContactDao contactDao;
    private ContactService contactService;

    @BeforeEach
    void setUp(){
        contactDao = mock(ContactDao.class);
        contactService = new ContactService(contactDao);
    }

    @Test
    void test_when_is_created_not_has_contacts(){
        when(contactDao.getContacts()).thenReturn(new ArrayList<>());

        assertFalse(contactService.hasGotAddedContacts());
        verify(contactDao).getContacts();
    }

    @Test
    void test_can_add_a_contact(){
        Contact aContact = new Contact(CONTACT_NAME, CONTACT_PHONENUMBER);

        List<Contact> contactList = List.of(aContact);

        when(contactDao.getContacts()).thenReturn(contactList);

        contactService.addContact(aContact);

        assertTrue(contactService.getContacts().contains(aContact));
        assertTrue(contactService.hasGotAddedContacts());
        verify(contactDao).addContact(aContact);
    }

    @Test
    void test_cant_remove_a_contact_if_do_not_exists(){
        Contact contact = new Contact(CONTACT_NAME, CONTACT_PHONENUMBER);

        doThrow(RuntimeException.class).
                when(contactDao).
                removeContact(contact);

        assertThrows(RuntimeException.class, () -> contactService.removeContact(contact));
        assertFalse(contactService.getContacts().contains(contact));
        verify(contactDao).removeContact(contact);
    }

    @Test
    void can_remove_a_existent_contact(){
        Contact contact = new Contact(CONTACT_NAME, CONTACT_PHONENUMBER);

        contactService.addContact(contact);

        contactService.removeContact(contact);

        assertFalse(contactService.getContacts().contains(contact));
        assertFalse(contactService.hasGotAddedContacts());
        verify(contactDao).removeContact(contact);
    }

    @Test
    void test_get_contacts(){
        Contact aContact = new Contact(CONTACT_NAME, CONTACT_PHONENUMBER);
        Contact otherContact = new Contact("Pepe", "3234423");

        List<Contact> contactList = List.of(aContact, otherContact);

        when(contactDao.getContacts()).thenReturn(contactList);

        contactService.addContact(aContact);
        contactService.addContact(otherContact);

        assertEquals(contactList, contactService.getContacts());
        verify(contactDao).getContacts();
    }

}
