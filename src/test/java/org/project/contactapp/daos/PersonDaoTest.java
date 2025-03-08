package org.project.contactapp.daos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.project.contactapp.DatabaseConnection.dbConnection;
import org.project.contactapp.entities.Person;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PersonDAOTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private Statement mockStatement;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException {
        // Initialize mocks
        mockConnection = mock(Connection.class);
        mockStatement = mock(Statement.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Mock dbConnection to return our mock Connection
        try (MockedStatic<dbConnection> dbMock = Mockito.mockStatic(dbConnection.class)) {
            dbMock.when(dbConnection::getConnection).thenReturn(mockConnection);
        }
    }

    @Test
    void testGetAllPersons_success() throws SQLException {
        // Arrange
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery("SELECT * FROM person")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false); // Two rows
        when(mockResultSet.getInt("id")).thenReturn(1).thenReturn(2);
        when(mockResultSet.getString("lastname")).thenReturn("Doe").thenReturn("Smith");
        when(mockResultSet.getString("firstname")).thenReturn("John").thenReturn("Jane");
        when(mockResultSet.getString("nickname")).thenReturn("Johnny").thenReturn("Janie");
        when(mockResultSet.getString("phone_number")).thenReturn("1234567890").thenReturn("0987654321");
        when(mockResultSet.getString("address")).thenReturn("123 Main St").thenReturn("456 Oak Ave");
        when(mockResultSet.getString("email_address")).thenReturn("john.doe@example.com").thenReturn("jane.smith@example.com");
        when(mockResultSet.getString("birth_date")).thenReturn(String.valueOf(LocalDate.of(1990, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()))
                .thenReturn(String.valueOf(LocalDate.of(1995, 5, 5).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        when(mockResultSet.getString("image_path")).thenReturn("/path/to/john.jpg").thenReturn("/path/to/jane.jpg");

        // Act
        List<Person> persons = PersonDAO.getAllPersons();

        // Assert
        assertThat(persons).hasSize(2);
        Person person1 = persons.get(0);
        assertThat(person1.getId()).isEqualTo(1);
        assertThat(person1.getLastname()).isEqualTo("Doe");
        assertThat(person1.getFirstname()).isEqualTo("John");
        assertThat(person1.getPhone_number()).isEqualTo("1234567890");
        assertThat(person1.getBirth_date()).isEqualTo(LocalDate.of(1990, 1, 1));

        Person person2 = persons.get(1);
        assertThat(person2.getId()).isEqualTo(2);
        assertThat(person2.getLastname()).isEqualTo("Smith");
        assertThat(person2.getFirstname()).isEqualTo("Jane");
        assertThat(person2.getBirth_date()).isEqualTo(LocalDate.of(1995, 5, 5));
    }

    @Test
    void testGetAllPersons_emptyResult() throws SQLException {
        // Arrange
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery("SELECT * FROM person")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // No rows

        // Act
        List<Person> persons = PersonDAO.getAllPersons();

        // Assert
        assertThat(persons).isEmpty();
    }

    @Test
    void testSavePerson_success() throws SQLException {
        // Arrange
        Person person = new Person(0, "Doe", "John", "Johnny", "1234567890", "123 Main St", "john.doe@example.com", LocalDate.of(1990, 1, 1));
        person.setImage_path("/path/to/image.jpg");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // 1 row affected

        // Act
        boolean result = PersonDAO.savePerson(person);

        // Assert
        assertThat(result).isTrue();
        verify(mockPreparedStatement).setString(1, "Doe");
        verify(mockPreparedStatement).setString(2, "John");
        verify(mockPreparedStatement).setString(3, "Johnny");
        verify(mockPreparedStatement).setString(4, "1234567890");
        verify(mockPreparedStatement).setString(5, "123 Main St");
        verify(mockPreparedStatement).setString(6, "john.doe@example.com");
        verify(mockPreparedStatement).setDate(7, Date.valueOf(LocalDate.of(1990, 1, 1)));
        verify(mockPreparedStatement).setString(8, "/path/to/image.jpg");
    }

    @Test
    void testSavePerson_failure() throws SQLException {
        // Arrange
        Person person = new Person(0, "Doe", "John", null, "1234567890", null, null, null);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Insert failed"));

        // Act
        boolean result = PersonDAO.savePerson(person);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    void testUpdatePerson_success() throws SQLException {
        // Arrange
        Person person = new Person(1, "Smith", "Jane", "Janie", "0987654321", "456 Oak Ave", "jane.smith@example.com", LocalDate.of(1995, 5, 5));
        person.setImage_path("/path/to/jane.jpg");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // 1 row affected

        // Act
        boolean result = PersonDAO.updatePerson(person);

        // Assert
        assertThat(result).isTrue();
        verify(mockPreparedStatement).setString(1, "Smith");
        verify(mockPreparedStatement).setString(2, "Jane");
        verify(mockPreparedStatement).setString(3, "Janie");
        verify(mockPreparedStatement).setString(4, "0987654321");
        verify(mockPreparedStatement).setString(5, "456 Oak Ave");
        verify(mockPreparedStatement).setString(6, "jane.smith@example.com");
        verify(mockPreparedStatement).setDate(7, Date.valueOf(LocalDate.of(1995, 5, 5)));
        verify(mockPreparedStatement).setString(8, "/path/to/jane.jpg");
        verify(mockPreparedStatement).setInt(9, 1);
    }

    @Test
    void testUpdatePerson_failure() throws SQLException {
        // Arrange
        Person person = new Person(1, "Smith", "Jane", null, "0987654321", null, null, null);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Update failed"));

        // Act
        boolean result = PersonDAO.updatePerson(person);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    void testDeletePerson_success() throws SQLException {
        // Arrange
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // 1 row affected

        // Act
        boolean result = PersonDAO.deletePerson(1);

        // Assert
        assertThat(result).isTrue();
        verify(mockPreparedStatement).setInt(1, 1);
    }

    @Test
    void testDeletePerson_failure() throws SQLException {
        // Arrange
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Delete failed"));

        // Act
        boolean result = PersonDAO.deletePerson(1);

        // Assert
        assertThat(result).isFalse();
    }
}