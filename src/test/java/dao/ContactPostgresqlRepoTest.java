package dao;

import model.Contact;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ContactPostgresqlRepoTest {

    public static final String CONTACT_NAME = "Pepe";
    public static final String CONTACT_PHONENUMBER = "32324423";

    private Connection connection;
    private ContactPostgresqlRepo contactRepo;

    @BeforeEach
    void setUp(){
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/simple_jdbc", "postgres", "nicolasdemaio");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        contactRepo = new ContactPostgresqlRepo(connection);
    }

    @Test
    void when_is_created_has_not_added_contacts(){
        assertTrue(contactRepo.getContacts().isEmpty());
    }

    @Test
    void can_add_contact(){
        Contact contactToAdd = new Contact(CONTACT_NAME, CONTACT_PHONENUMBER);

        contactRepo.addContact(contactToAdd);

        assertFalse(contactRepo.getContacts().isEmpty());
        assertTrue(contactRepo.getContacts().contains(contactToAdd));

        //Teardown - Reason: the contact has been added, finally is removed to empty the database.
        contactRepo.removeContact(contactToAdd);
    }

    @Test
    void can_remove_contact(){
        Contact contactToRemove = new Contact(CONTACT_NAME, CONTACT_PHONENUMBER);

        contactRepo.addContact(contactToRemove);
        contactRepo.removeContact(contactToRemove);

        assertFalse(contactRepo.getContacts().contains(contactToRemove));
        assertTrue(contactRepo.getContacts().isEmpty());
    }

    @Test
    void test_get_contacts_with_two_contacts_added(){

        Contact aContact = new Contact("Juan", "12341234");
        Contact otherContact = new Contact("Nicolas", "332332341");

        List<Contact> contactsAdded = List.of(aContact, otherContact);

        contactRepo.addContact(aContact);
        contactRepo.addContact(otherContact);

        assertEquals(contactsAdded, contactRepo.getContacts());

        //Teardown
        contactRepo.removeContact(aContact);
        contactRepo.removeContact(otherContact);
    }


}
