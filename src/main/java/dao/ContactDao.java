package dao;

import model.Contact;

import java.util.List;

public interface ContactDao {

    void addContact(Contact aContact);

    void removeContact(Contact contact);

    List<Contact> getContacts();
}
