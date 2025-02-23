package org.project.contactapp.daos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.contactapp.DatabaseConnection.dbConnection;
import org.project.contactapp.entities.Person;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class PersonDaoTest {

    private PersonDAO personDao;

    @BeforeEach
    public void initDatabase() throws SQLException {
        personDao = new PersonDAO();

        // Initialize the database with test data
        try (Connection connection = dbConnection.getConnection();
             Statement stmt = connection.createStatement()) {

            // Create the person table if it doesn't exist
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS person (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY, " +
                            "firstname VARCHAR(50) NOT NULL, " +
                            "lastname VARCHAR(50) NOT NULL, " +
                            "nickname VARCHAR(50), " +
                            "phone_number VARCHAR(20) NOT NULL, " +
                            "address VARCHAR(100), " +
                            "email_address VARCHAR(100), " +
                            "birth_date DATE)"
            );

            // Clean up previous data
            stmt.executeUpdate("DELETE FROM person");

            // Insert test data
            stmt.executeUpdate("INSERT INTO person (firstname, lastname, nickname, phone_number, address, email_address, birth_date) " +
                    "VALUES ('John', 'Doe', 'Johnny', '123456789', '123 Street, City', 'john.doe@example.com', '1990-01-01')");
            stmt.executeUpdate("INSERT INTO person (firstname, lastname, nickname, phone_number, address, email_address, birth_date) " +
                    "VALUES ('Jane', 'Smith', 'Janey', '987654321', '456 Avenue, City', 'jane.smith@example.com', '1992-05-10')");
            stmt.executeUpdate("INSERT INTO person (firstname, lastname, nickname, phone_number, address, email_address, birth_date) " +
                    "VALUES ('Bob', 'Brown', 'Bobby', '555666777', '789 Road, City', 'bob.brown@example.com', '1985-07-15')");
        } catch (SQLException e) {
            fail("Failed to initialize database: " + e.getMessage());
        }
    }

    @Test
    public void shouldListAllPersons() {
        // WHEN: Retrieve all persons from the database
        List<Person> persons = personDao.getAllPersons();

        // THEN: Verify the retrieved data
        assertThat(persons).hasSize(3); // Check that 3 persons are retrieved

        // Verify the details of each person
        assertThat(persons).extracting("lastname", "firstname", "nickname", "phone_number", "address", "email_address", "birth_date")
                .containsExactlyInAnyOrder(
                        org.assertj.core.groups.Tuple.tuple("Doe", "John", "Johnny", "123456789", "123 Street, City", "john.doe@example.com", LocalDate.of(1990, 1, 1)),
                        org.assertj.core.groups.Tuple.tuple("Smith", "Jane", "Janey", "987654321", "456 Avenue, City", "jane.smith@example.com", LocalDate.of(1992, 5, 10)),
                        org.assertj.core.groups.Tuple.tuple("Brown", "Bob", "Bobby", "555666777", "789 Road, City", "bob.brown@example.com", LocalDate.of(1985, 7, 15))
                );
    }
}