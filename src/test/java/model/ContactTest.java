package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactTest {

    public static final String CONTACT_NAME = "Juan";
    public static final String CONTACT_PHONENUMBER = "44223323";

    private Contact contact;

    @BeforeEach
    void setUp(){
        contact = new Contact(CONTACT_NAME, CONTACT_PHONENUMBER);
    }

    @Test
    void test_constructor(){
        assertEquals(CONTACT_NAME, contact.getName());
        assertEquals(CONTACT_PHONENUMBER, contact.getPhoneNumber());
    }

    @Test
    void test_getName(){
        String newName = "Pepe";

        contact.setName(newName);

        assertEquals(newName, contact.getName());
    }

    @Test
    void test_getPhoneNumber(){
        String newPhoneNumber = "4432322";

        contact.setPhoneNumber(newPhoneNumber);

        assertEquals(newPhoneNumber, contact.getPhoneNumber());
    }

}
