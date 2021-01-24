import dao.ContactDao;
import dao.ContactPostgresqlRepo;
import model.Contact;
import service.ContactService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class sampleMain {

    public static void main(String[] args) throws SQLException {

        //Instantiate the connection, dao, service and contacts.

        String databaseUrl = "jdbc:postgresql://localhost:5432/simple_jdbc";
        String user = "postgres";
        String password = "nicolasdemaio";

        Connection databaseConnection = DriverManager.getConnection(databaseUrl, user, password);

        ContactDao contactDao = new ContactPostgresqlRepo(databaseConnection);

        ContactService contactService = new ContactService(contactDao);

        Contact aContact = new Contact("Pepe", "3233234");
        Contact otherContact = new Contact("Juan", "4232323");

        //Print if the database has added contacts (False at this moment).
        System.out.println(contactService.hasGotAddedContacts());

        //Add two contacts
        contactService.addContact(aContact);
        contactService.addContact(otherContact);

        //Print true at this moment, because has two added contacts.
        System.out.println(contactService.hasGotAddedContacts());

        //Print to string all the contacts.
        for (Contact contact : contactService.getContacts()){
            System.out.println(contact);
        }

        //Remove contacts to empty the database.
        contactService.removeContact(aContact);
        contactService.removeContact(otherContact);

    }

}
