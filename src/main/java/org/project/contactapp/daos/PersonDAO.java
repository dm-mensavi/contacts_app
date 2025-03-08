package org.project.contactapp.daos;

import org.project.contactapp.DatabaseConnection.dbConnection;
import org.project.contactapp.entities.Person;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {


    public static List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        String query = "SELECT * FROM person";

        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement();
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

                LocalDate birth_date = null;
                if (birth_date_str != null && !birth_date_str.isEmpty()) {
                    long timestamp = Long.parseLong(birth_date_str);
                    birth_date = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
                }

                Person person = new Person(id, lastname, firstname, nickname, phone_number, address, email_address, birth_date);
                persons.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return persons;
    }


    public static boolean savePerson(Person person) {
        String query = "INSERT INTO person (lastname, firstname, nickname, phone_number, address, email_address, birth_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, person.getLastname());
            preparedStatement.setString(2, person.getFirstname());
            preparedStatement.setString(3, person.getNickname());
            preparedStatement.setString(4, person.getPhone_number());
            preparedStatement.setString(5, person.getAddress());
            preparedStatement.setString(6, person.getEmail_address());
            preparedStatement.setDate(7, Date.valueOf(person.getBirth_date()));

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updatePerson(Person person) {
        String query = "UPDATE person SET lastname = ?, firstname = ?, nickname = ?, phone_number = ?, " +
                "address = ?, email_address = ?, birth_date = ? WHERE id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, person.getLastname());
            preparedStatement.setString(2, person.getFirstname());
            preparedStatement.setString(3, person.getNickname());
            preparedStatement.setString(4, person.getPhone_number());
            preparedStatement.setString(5, person.getAddress());
            preparedStatement.setString(6, person.getEmail_address());
            preparedStatement.setDate(7, Date.valueOf(person.getBirth_date()));
            preparedStatement.setInt(8, person.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deletePerson(int id) {
        String query = "DELETE FROM person WHERE id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}