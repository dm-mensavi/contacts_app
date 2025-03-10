package org.project.contactapp.controllers;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ContactDetailsControllerTest extends ApplicationTest {

    private ContactDetailsController controller;
    @Mock
    private PersonDAO mockPersonDAO;

    private TextField lastNameField;
    private TextField firstNameField;
    private TextField nicknameField;
    private TextField phoneNumberField;
    private TextField addressField;
    private TextField emailAddressField;
    private DatePicker birthDateField;
    private ImageView imageView;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Create controller instance and inject mock PersonDAO
        controller = new ContactDetailsController() {
            {
                this.personDAO = mockPersonDAO;
            }
        };

        // Initialize UI components
        lastNameField = new TextField();
        firstNameField = new TextField();
        nicknameField = new TextField();
        phoneNumberField = new TextField();
        addressField = new TextField();
        emailAddressField = new TextField();
        birthDateField = new DatePicker();
        imageView = new ImageView();

        // Set controller fields
        controller.lastNameField = lastNameField;
        controller.firstNameField = firstNameField;
        controller.nicknameField = nicknameField;
        controller.phoneNumberField = phoneNumberField;
        controller.addressField = addressField;
        controller.emailAddressField = emailAddressField;
        controller.birthDateField = birthDateField;
        controller.imageView = imageView;

        // Initialize controller
        controller.initialize();
    }

    @Test
    void testInitialize() {
        // Assert that all text fields are editable and date picker is enabled
        assertThat(lastNameField.isEditable()).isTrue();
        assertThat(firstNameField.isEditable()).isTrue();
        assertThat(nicknameField.isEditable()).isTrue();
        assertThat(phoneNumberField.isEditable()).isTrue();
        assertThat(addressField.isEditable()).isTrue();
        assertThat(emailAddressField.isEditable()).isTrue();
        assertThat(birthDateField.isDisable()).isFalse();
    }

    @Test
    void testSetSelectedContact() {
        // Arrange: Create a Person object with sample data
        Person person = new Person();
        person.setId(1);
        person.setLastname("Doe");
        person.setFirstname("John");
        person.setNickname("Johnny");
        person.setPhone_number("1234567890");
        person.setAddress("123 Main St");
        person.setEmail_address("john.doe@example.com");
        person.setBirth_date(LocalDate.of(1990, 1, 1));
        person.setImage_path("/path/to/image.jpg");

        // Act: Set the selected contact in the controller
        controller.setSelectedContact(person);

        // Assert: Verify that the UI fields are populated with the person's data
        assertThat(lastNameField.getText()).isEqualTo("Doe");
        assertThat(firstNameField.getText()).isEqualTo("John");
        assertThat(nicknameField.getText()).isEqualTo("Johnny");
        assertThat(phoneNumberField.getText()).isEqualTo("1234567890");
        assertThat(addressField.getText()).isEqualTo("123 Main St");
        assertThat(emailAddressField.getText()).isEqualTo("john.doe@example.com");
        assertThat(birthDateField.getValue()).isEqualTo(LocalDate.of(1990, 1, 1));
        assertThat(imageView.getImage()).isNotNull();
        assertThat(imageView.getImage().getUrl()).isEqualTo("file:/path/to/image.jpg");
        assertThat(controller.selectedContact).isSameAs(person);
    }

    @Test
    void testPhoneNumberValidation_onlyAllowsNumbers() {
        // Act & Assert: Verify that non-numeric input is cleared
        phoneNumberField.setText("abc123");
        assertThat(phoneNumberField.getText()).isEmpty();

        // Act & Assert: Verify that numeric input is accepted
        phoneNumberField.setText("123456");
        assertThat(phoneNumberField.getText()).isEqualTo("123456");
    }

    @Test
    void testOnSaveClick_success() throws SQLException {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Arrange: Set up a Person object and mock successful update
            Person person = new Person();
            person.setId(1);
            controller.setSelectedContact(person);
            lastNameField.setText("Smith");
            firstNameField.setText("Jane");
            phoneNumberField.setText("0987654321");
            emailAddressField.setText("jane.smith@example.com");
            birthDateField.setValue(LocalDate.of(1995, 5, 5));
            when(mockPersonDAO.updatePerson(any(Person.class))).thenReturn(true);

            // Act: Simulate save button click
            controller.onSaveClick();

            // Assert: Verify that the person's data is updated and navigation occurs
            assertThat(person.getLastname()).isEqualTo("Smith");
            assertThat(person.getFirstname()).isEqualTo("Jane");
            assertThat(person.getPhone_number()).isEqualTo("0987654321");
            assertThat(person.getEmail_address()).isEqualTo("jane.smith@example.com");
            assertThat(person.getBirth_date()).isEqualTo(LocalDate.of(1995, 5, 5));
            mainAppMock.verify(() -> MainApp.navigateTo("allContacts-page.fxml"));
        }
    }

    @Test
    void testOnSaveClick_failure() throws SQLException {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Arrange: Set up a Person object and mock update failure
            Person person = new Person();
            person.setId(1);
            controller.setSelectedContact(person);
            lastNameField.setText("Smith");
            when(mockPersonDAO.updatePerson(any(Person.class))).thenReturn(false);

            // Act: Simulate save button click
            controller.onSaveClick();

            // Assert: Verify that the person's data is not updated and no navigation occurs
            assertThat(person.getLastname()).isEqualTo("Smith");
            mainAppMock.verifyNoInteractions();
        }
    }

    @Test
    void testOnSaveClick_databaseError() throws SQLException {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Arrange: Set up a Person object and mock database error
            Person person = new Person();
            person.setId(1);
            controller.setSelectedContact(person);
            lastNameField.setText("Smith");
            when(mockPersonDAO.updatePerson(any(Person.class))).thenThrow(new SQLException("Update error"));

            // Act: Simulate save button click
            controller.onSaveClick();

            // Assert: Verify that the person's data is not updated and no navigation occurs
            assertThat(person.getLastname()).isEqualTo("Smith");
            mainAppMock.verifyNoInteractions();
        }
    }

    @Test
    void testOnCancelClick() {
        // Arrange: Set up a Person object and modify UI fields
        Person person = new Person();
        person.setLastname("Doe");
        person.setFirstname("John");
        person.setPhone_number("1234567890");
        controller.setSelectedContact(person);
        lastNameField.setText("Smith");
        firstNameField.setText("Jane");
        phoneNumberField.setText("0987654321");

        // Act: Simulate cancel button click
        controller.onCancelClick();

        // Assert: Verify that the UI fields are reset to the original person's data
        assertThat(lastNameField.getText()).isEqualTo("Doe");
        assertThat(firstNameField.getText()).isEqualTo("John");
        assertThat(phoneNumberField.getText()).isEqualTo("1234567890");
    }

    @Test
    void testOnBackClick() {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Act: Simulate back button click
            controller.onBackClick();

            // Assert: Verify navigation to all contacts page
            mainAppMock.verify(() -> MainApp.navigateTo("allContacts-page.fxml"));
        }
    }
}