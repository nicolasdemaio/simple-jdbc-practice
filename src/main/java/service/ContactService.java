package service;

import dao.ContactDao;
import model.Contact;

import java.util.List;

public class ContactService {

    private ContactDao contactDao;

    public ContactService(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public boolean hasGotAddedContacts() {
        return !this.contactDao.getContacts().isEmpty();
    }

    public void addContact(Contact aContact) {
        this.contactDao.addContact(aContact);
    }

    public void removeContact(Contact aContact) {
        this.contactDao.removeContact(aContact);
    }

    public List<Contact> getContacts() {
        return this.contactDao.getContacts();
    }
}
