package org.project.contactapp.daos;

import org.project.contactapp.entities.Person;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    private final Connection connection;

    // Constructor for dependency injection
    public PersonDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Person> getAllPersons() throws SQLException {
        List<Person> persons = new ArrayList<>();
        String query = "SELECT * FROM person";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String lastname = resultSet.getString("lastname");
                String firstname = resultSet.getString("firstname");
                String nickname = resultSet.getString("nickname");
                String phone_number = resultSet.getString("phone_number");
                String address = resultSet.getString("address");
                String email_address = resultSet.getString("email_address");
                String birth_date_str = resultSet.getString("birth_date");
                String image_path = resultSet.getString("image_path");

                LocalDate birth_date = null;
                if (birth_date_str != null && !birth_date_str.isEmpty()) {
                    long timestamp = Long.parseLong(birth_date_str);
                    birth_date = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
                }

                Person person = new Person(id, lastname, firstname, nickname, phone_number, address, email_address, birth_date);
                person.setImage_path(image_path);
                persons.add(person);
            }
        }
        return persons;
    }

    public boolean savePerson(Person person) throws SQLException {
        String query = "INSERT INTO person (lastname, firstname, nickname, phone_number, address, email_address, birth_date, image_path) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setPersonParameters(preparedStatement, person);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean updatePerson(Person person) throws SQLException {
        String query = "UPDATE person SET lastname = ?, firstname = ?, nickname = ?, phone_number = ?, address = ?, email_address = ?, birth_date = ?, image_path = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setPersonParameters(preparedStatement, person);
            preparedStatement.setInt(9, person.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean deletePerson(int id) throws SQLException {
        String query = "DELETE FROM person WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    private void setPersonParameters(PreparedStatement preparedStatement, Person person) throws SQLException {
        preparedStatement.setString(1, person.getLastname());
        preparedStatement.setString(2, person.getFirstname());
        preparedStatement.setString(3, person.getNickname());
        preparedStatement.setString(4, person.getPhone_number());
        preparedStatement.setString(5, person.getAddress());
        preparedStatement.setString(6, person.getEmail_address());
        if (person.getBirth_date() != null) {
            preparedStatement.setDate(7, Date.valueOf(person.getBirth_date()));
        } else {
            preparedStatement.setNull(7, Types.DATE);
        }
        preparedStatement.setString(8, person.getImage_path());
    }
}