package org.project.contactapp.controllers;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.project.contactapp.MainApp;
import org.project.contactapp.daos.PersonDAO;
import org.project.contactapp.entities.Person;
import org.testfx.framework.junit5.ApplicationTest;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AllContactsControllerTest extends ApplicationTest {

    private AllContactsController controller;
    @Mock
    private PersonDAO mockPersonDAO;

    private TableView<Person> contactTable;
    private TableColumn<Person, String> firstNameColumn;
    private TableColumn<Person, String> lastNameColumn;
    private TableColumn<Person, String> phoneNumberColumn;
    private TextField searchField;
    private Button deleteContactButton;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        controller = new AllContactsController() {
            // Override constructor to inject mock PersonDAO
            {
                this.personDAO = mockPersonDAO;
            }
        };

        contactTable = new TableView<>();
        firstNameColumn = new TableColumn<>("First Name");
        lastNameColumn = new TableColumn<>("Last Name");
        phoneNumberColumn = new TableColumn<>("Phone Number");
        searchField = new TextField();
        deleteContactButton = new Button();

        controller.contactTable = contactTable;
        controller.firstNameColumn = firstNameColumn;
        controller.lastNameColumn = lastNameColumn;
        controller.phoneNumberColumn = phoneNumberColumn;
        controller.searchField = searchField;
        controller.deleteContactButton = deleteContactButton;

        controller.initialize();
    }

    @Test
    void testInitialize_success() throws SQLException {
        // Arrange
        List<Person> mockPersons = Arrays.asList(
                new Person("John", "Doe", "1234567890"),
                new Person("Jane", "Smith", "0987654321")
        );
        when(mockPersonDAO.getAllPersons()).thenReturn(mockPersons);

        // Act
        controller.initialize();

        // Assert
        assertThat(contactTable.getItems()).hasSize(2);
        assertThat(contactTable.getItems()).containsAll(mockPersons);
        assertThat(deleteContactButton.isDisabled()).isTrue();
    }

    @Test
    void testInitialize_databaseError() throws SQLException {
        // Arrange
        when(mockPersonDAO.getAllPersons()).thenThrow(new SQLException("Database error"));

        // Act
        controller.initialize();

        // Assert
        assertThat(contactTable.getItems()).isEmpty();
        // Alert is shown, but not testable here
    }

    @Test
    void testOnDeleteContactClick_success() throws SQLException {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Arrange
            Person person = new Person("John", "Doe", "1234567890");
            person.setId(1);
            controller.allContacts = FXCollections.observableArrayList(person);
            contactTable.setItems(controller.allContacts);
            contactTable.getSelectionModel().select(person);
            controller.initialize(); // To set up listeners
            when(mockPersonDAO.deletePerson(1)).thenReturn(true);

            // Act - Simulate confirmation (bypass Alert)
            interact(() -> controller.onDeleteContactClick());

            // Assert
            assertThat(controller.allContacts).isEmpty();
        }
    }

    @Test
    void testOnDeleteContactClick_failure() throws SQLException {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Arrange
            Person person = new Person("John", "Doe", "1234567890");
            person.setId(1);
            controller.allContacts = FXCollections.observableArrayList(person);
            contactTable.setItems(controller.allContacts);
            contactTable.getSelectionModel().select(person);
            controller.initialize(); // To set up listeners
            when(mockPersonDAO.deletePerson(1)).thenReturn(false);

            // Act - Simulate confirmation
            interact(() -> controller.onDeleteContactClick());

            // Assert
            assertThat(controller.allContacts).contains(person);
            // Alert is shown, but not testable here
        }
    }

    @Test
    void testOnDeleteContactClick_databaseError() throws SQLException {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Arrange
            Person person = new Person("John", "Doe", "1234567890");
            person.setId(1);
            controller.allContacts = FXCollections.observableArrayList(person);
            contactTable.setItems(controller.allContacts);
            contactTable.getSelectionModel().select(person);
            controller.initialize(); // To set up listeners
            when(mockPersonDAO.deletePerson(1)).thenThrow(new SQLException("Delete error"));

            // Act - Simulate confirmation
            interact(() -> controller.onDeleteContactClick());

            // Assert
            assertThat(controller.allContacts).contains(person);
            // Alert is shown, but not testable here
        }
    }

    @Test
    void testOnBackClick() {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Act
            controller.onBackClick();

            // Assert
            mainAppMock.verify(() -> MainApp.navigateTo("home-page.fxml"));
        }
    }

    @Test
    void testOnAddContactClick() {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Act
            controller.onAddContactClick();

            // Assert
            mainAppMock.verify(() -> MainApp.navigateTo("addContact-page.fxml"));
        }
    }

    @Test
    void testOnSearchClick() {
        // Arrange
        Person person1 = new Person("John", "Doe", "1234567890");
        Person person2 = new Person("Jane", "Smith", "0987654321");
        controller.allContacts = FXCollections.observableArrayList(person1, person2);
        contactTable.setItems(controller.allContacts);
        controller.initialize(); // To set up listeners

        // Act
        searchField.setText("John");
        controller.onSearchClick();

        // Assert
        assertThat(contactTable.getItems()).hasSize(1);
        assertThat(contactTable.getItems()).contains(person1);
    }

    @Test
    void testOnSearchClick_emptyQuery() {
        // Arrange
        Person person1 = new Person("John", "Doe", "1234567890");
        Person person2 = new Person("Jane", "Smith", "0987654321");
        controller.allContacts = FXCollections.observableArrayList(person1, person2);
        contactTable.setItems(controller.allContacts);
        controller.initialize(); // To set up listeners

        // Act
        searchField.setText("");
        controller.onSearchClick();

        // Assert
        assertThat(contactTable.getItems()).hasSize(2);
        assertThat(contactTable.getItems()).containsAll(controller.allContacts);
    }


}