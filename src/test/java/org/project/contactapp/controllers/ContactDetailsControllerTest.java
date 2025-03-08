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
        MockitoAnnotations.openMocks(this);
        controller = new ContactDetailsController() {
            // Override constructor to inject mock PersonDAO
            {
                this.personDAO = mockPersonDAO;
            }
        };

        lastNameField = new TextField();
        firstNameField = new TextField();
        nicknameField = new TextField();
        phoneNumberField = new TextField();
        addressField = new TextField();
        emailAddressField = new TextField();
        birthDateField = new DatePicker();
        imageView = new ImageView();

        controller.lastNameField = lastNameField;
        controller.firstNameField = firstNameField;
        controller.nicknameField = nicknameField;
        controller.phoneNumberField = phoneNumberField;
        controller.addressField = addressField;
        controller.emailAddressField = emailAddressField;
        controller.birthDateField = birthDateField;
        controller.imageView = imageView;

        controller.initialize();
    }

    @Test
    void testInitialize() {
        // Assert
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
        // Arrange
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

        // Act
        controller.setSelectedContact(person);

        // Assert
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
        // Act & Assert
        phoneNumberField.setText("abc123");
        assertThat(phoneNumberField.getText()).isEmpty();

        phoneNumberField.setText("123456");
        assertThat(phoneNumberField.getText()).isEqualTo("123456");
    }

    @Test
    void testOnSaveClick_success() throws SQLException {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Arrange
            Person person = new Person();
            person.setId(1);
            controller.setSelectedContact(person);
            lastNameField.setText("Smith");
            firstNameField.setText("Jane");
            phoneNumberField.setText("0987654321");
            emailAddressField.setText("jane.smith@example.com");
            birthDateField.setValue(LocalDate.of(1995, 5, 5));
            when(mockPersonDAO.updatePerson(any(Person.class))).thenReturn(true);

            // Act
            controller.onSaveClick();

            // Assert
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
            // Arrange
            Person person = new Person();
            person.setId(1);
            controller.setSelectedContact(person);
            lastNameField.setText("Smith");
            when(mockPersonDAO.updatePerson(any(Person.class))).thenReturn(false);

            // Act
            controller.onSaveClick();

            // Assert
            assertThat(person.getLastname()).isEqualTo("Smith");
            mainAppMock.verifyNoInteractions(); // Navigation should not occur on failure
        }
    }

    @Test
    void testOnSaveClick_databaseError() throws SQLException {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Arrange
            Person person = new Person();
            person.setId(1);
            controller.setSelectedContact(person);
            lastNameField.setText("Smith");
            when(mockPersonDAO.updatePerson(any(Person.class))).thenThrow(new SQLException("Update error"));

            // Act
            controller.onSaveClick();

            // Assert
            assertThat(person.getLastname()).isEqualTo("Smith");
            mainAppMock.verifyNoInteractions(); // Navigation should not occur on error
        }
    }

    @Test
    void testOnCancelClick() {
        // Arrange
        Person person = new Person();
        person.setLastname("Doe");
        person.setFirstname("John");
        person.setPhone_number("1234567890");
        controller.setSelectedContact(person);

        // Modify fields
        lastNameField.setText("Smith");
        firstNameField.setText("Jane");
        phoneNumberField.setText("0987654321");

        // Act
        controller.onCancelClick();

        // Assert
        assertThat(lastNameField.getText()).isEqualTo("Doe");
        assertThat(firstNameField.getText()).isEqualTo("John");
        assertThat(phoneNumberField.getText()).isEqualTo("1234567890");
    }

    @Test
    void testOnBackClick() {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Act
            controller.onBackClick();

            // Assert
            mainAppMock.verify(() -> MainApp.navigateTo("allContacts-page.fxml"));
        }
    }
}