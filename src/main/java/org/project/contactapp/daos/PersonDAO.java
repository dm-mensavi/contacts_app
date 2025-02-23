//package org.project.contactapp.daos;
//
//import org.project.contactapp.DatabaseConnection.dbConnection;
//import org.project.contactapp.entities.Person;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class PersonDAO {
//    public static List<Person> getAllPersons() {
//        List<Person> persons = new ArrayList<>();
//        String sql = "SELECT * FROM contactdb";
//
//        try (Connection connection = dbConnection.getConnection();
//             Statement stmt = connection.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//
//            while (rs.next()) {
//                persons.add(new Person(
//                        rs.getInt("id"),
//                        rs.getString("firstname"),
//                        rs.getString("lastname"),
//                        rs.getString("nickname"),
//                        rs.getString("phone_number"),
//                        rs.getString("address"),
//                        rs.getString("email_address"),
//                        rs.getString("birth_date")
//                ));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return persons;
//    }
//}


package org.project.contactapp.daos;

import org.project.contactapp.DatabaseConnection.dbConnection;
import org.project.contactapp.entities.Person;
//import org.project.contactapp.entities.Person;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public  class  PersonDAO {

    public static List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        String query = "SELECT * FROM person"; // Ensure table name matches

        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String lastname = resultSet.getString("lastname"); // Use lastname
                String firstname = resultSet.getString("firstname"); // Use firstname
                String nickname = resultSet.getString("nickname");
                String phone_number = resultSet.getString("phone_number");
                String address = resultSet.getString("address");
                String email_address = resultSet.getString("email_address");
                LocalDate birth_date = resultSet.getDate("birth_date").toLocalDate();

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

            // Set parameters for the query
            preparedStatement.setString(1, person.getLastname());
            preparedStatement.setString(2, person.getFirstname());
            preparedStatement.setString(3, person.getNickname());
            preparedStatement.setString(4, person.getPhone_number());
            preparedStatement.setString(5, person.getAddress());
            preparedStatement.setString(6, person.getEmail_address());
            preparedStatement.setDate(7, java.sql.Date.valueOf(person.getBirth_date()));

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if the person was saved successfully

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if an error occurred
        }
    }
}