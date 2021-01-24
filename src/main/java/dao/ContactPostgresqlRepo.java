package dao;

import model.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactPostgresqlRepo implements ContactDao {

    final String GETALL = "SELECT contactName, phoneNumber FROM contacts";
    final String INSERT = "INSERT INTO contacts VALUES (?, ?)";
    final String DELETE = "DELETE FROM contacts WHERE contactName = ? AND phoneNumber = ?";
    final String GETONE = "SELECT contactName, phoneNumber FROM contacts WHERE contactName = ? AND phoneNumber = ?";

    private Connection connection;

    public ContactPostgresqlRepo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addContact(Contact aContact) {
        this.queryStatement(INSERT, aContact);
    }

    @Override
    public void removeContact(Contact contact) {
        this.queryStatement(DELETE, contact);
    }

    @Override
    public List<Contact> getContacts() {
        Statement statement = null;
        List<Contact> contacts = new ArrayList<>();

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GETALL);
            while(resultSet.next()){
                contacts.add(contactFromDatabase(resultSet));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return contacts;
    }

    private Contact contactFromDatabase(ResultSet resultSet) throws SQLException {
        String contactName = resultSet.getString("contactName");
        String phoneNumber = resultSet.getString("phoneNumber");

        return new Contact(contactName, phoneNumber);
    }

    private void queryStatement(String sql, Contact aContact){
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, aContact.getName());
            statement.setString(2, aContact.getPhoneNumber());
            statement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }
}
