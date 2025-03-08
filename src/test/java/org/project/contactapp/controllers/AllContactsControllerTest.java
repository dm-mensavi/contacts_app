package org.project.contactapp.controllers;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.project.contactapp.MainApp;
import org.project.contactapp.daos.PersonDAO;
import org.project.contactapp.entities.Person;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AllContactsControllerTest extends ApplicationTest {

    private AllContactsController controller;
    private TableView<Person> contactTable;
    private TableColumn<Person, String> firstNameColumn;
    private TableColumn<Person, String> lastNameColumn;
    private TableColumn<Person, String> phoneNumberColumn;
    private TextField searchField;
    private Button deleteContactButton;

    @BeforeEach
    void setUp() {
        controller = new AllContactsController();
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
    }

    @Test
    void testInitialize() {
        try (MockedStatic<PersonDAO> personDAOMock = Mockito.mockStatic(PersonDAO.class)) {
            // Arrange
            List<Person> mockPersons = Arrays.asList(
                    new Person("John", "Doe", "1234567890"),
                    new Person("Jane", "Smith", "0987654321")
            );
            personDAOMock.when(PersonDAO::getAllPersons).thenReturn(mockPersons);

            // Act
            controller.initialize();

            // Assert
            assertThat(contactTable.getItems()).hasSize(2);
            assertThat(contactTable.getItems()).containsAll(mockPersons);
            assertThat(deleteContactButton.isDisabled()).isTrue();
        }
    }

    @Test
    void testOnDeleteContactClick_success() {
        try (MockedStatic<PersonDAO> personDAOMock = Mockito.mockStatic(PersonDAO.class)) {
            // Arrange
            Person person = new Person("John", "Doe", "1234567890");
            person.setId(1);
            controller.allContacts = FXCollections.observableArrayList(person);
            contactTable.setItems(controller.allContacts);
            contactTable.getSelectionModel().select(person);
            controller.initialize(); // To set up listeners
            personDAOMock.when(() -> PersonDAO.deletePerson(1)).thenReturn(true);

            // Simulate confirmation dialog (bypass Alert)
            interact(() -> controller.onDeleteContactClick());

            // Assert
            assertThat(controller.allContacts).isEmpty();
        }
    }

    @Test
    void testOnDeleteContactClick_failure() {
        try (MockedStatic<PersonDAO> personDAOMock = Mockito.mockStatic(PersonDAO.class)) {
            // Arrange
            Person person = new Person("John", "Doe", "1234567890");
            person.setId(1);
            controller.allContacts = FXCollections.observableArrayList(person);
            contactTable.setItems(controller.allContacts);
            contactTable.getSelectionModel().select(person);
            controller.initialize(); // To set up listeners
            personDAOMock.when(() -> PersonDAO.deletePerson(1)).thenReturn(false);

            // Simulate confirmation dialog (bypass Alert)
            interact(() -> controller.onDeleteContactClick());

            // Assert
            assertThat(controller.allContacts).contains(person);
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

    @Test
    void testHandleRowDoubleClick() {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Arrange
            Person person = new Person("John", "Doe", "1234567890");
            controller.allContacts = FXCollections.observableArrayList(person);
            contactTable.setItems(controller.allContacts);
            contactTable.getSelectionModel().select(person);
            controller.initialize(); // To set up listeners

            // Act - Simulate double-click
            interact(() -> {
                controller.handleRowDoubleClick(new javafx.scene.input.MouseEvent(
                        javafx.scene.input.MouseEvent.MOUSE_CLICKED,
                        0, 0, 0, 0, MouseButton.PRIMARY, 2, // 2 clicks
                        false, false, false, false, false, false, false, false, false, false, null
                ));
            });

            // Assert
            mainAppMock.verify(() -> MainApp.navigateTo("contact-details.fxml", person));
        }
    }
}