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
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Create controller instance and inject mock PersonDAO
        controller = new AllContactsController() {
            {
                this.personDAO = mockPersonDAO;
            }
        };

        // Initialize UI components
        contactTable = new TableView<>();
        firstNameColumn = new TableColumn<>("First Name");
        lastNameColumn = new TableColumn<>("Last Name");
        phoneNumberColumn = new TableColumn<>("Phone Number");
        searchField = new TextField();
        deleteContactButton = new Button();

        // Set controller fields
        controller.contactTable = contactTable;
        controller.firstNameColumn = firstNameColumn;
        controller.lastNameColumn = lastNameColumn;
        controller.phoneNumberColumn = phoneNumberColumn;
        controller.searchField = searchField;
        controller.deleteContactButton = deleteContactButton;

        // Initialize controller
        controller.initialize();
    }

    @Test
    void testInitialize_success() throws SQLException {
        // Arrange: Set up mock data
        List<Person> mockPersons = Arrays.asList(
                new Person("John", "Doe", "1234567890"),
                new Person("Jane", "Smith", "0987654321")
        );
        when(mockPersonDAO.getAllPersons()).thenReturn(mockPersons);

        // Act: Initialize controller
        controller.initialize();

        // Assert: Verify table is populated and delete button is disabled
        assertThat(contactTable.getItems()).hasSize(2);
        assertThat(contactTable.getItems()).containsAll(mockPersons);
        assertThat(deleteContactButton.isDisabled()).isTrue();
    }

    @Test
    void testInitialize_databaseError() throws SQLException {
        // Arrange: Simulate database error
        when(mockPersonDAO.getAllPersons()).thenThrow(new SQLException("Database error"));

        // Act: Initialize controller
        controller.initialize();

        // Assert: Verify table is empty
        assertThat(contactTable.getItems()).isEmpty();
        // Alert is shown, but not testable here
    }

    @Test
    void testOnDeleteContactClick_success() throws SQLException {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Arrange: Set up selected contact and mock delete operation
            Person person = new Person("John", "Doe", "1234567890");
            person.setId(1);
            controller.allContacts = FXCollections.observableArrayList(person);
            contactTable.setItems(controller.allContacts);
            contactTable.getSelectionModel().select(person);
            controller.initialize(); // To set up listeners
            when(mockPersonDAO.deletePerson(1)).thenReturn(true);

            // Act: Simulate delete contact click
            interact(() -> controller.onDeleteContactClick());

            // Assert: Verify contact is removed from list
            assertThat(controller.allContacts).isEmpty();
        }
    }

    @Test
    void testOnBackClick() {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Act: Simulate back button click
            controller.onBackClick();

            // Assert: Verify navigation to home page
            mainAppMock.verify(() -> MainApp.navigateTo("home-page.fxml"));
        }
    }

    @Test
    void testOnAddContactClick() {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Act: Simulate add contact button click
            controller.onAddContactClick();

            // Assert: Verify navigation to add contact page
            mainAppMock.verify(() -> MainApp.navigateTo("addContact-page.fxml"));
        }
    }
}