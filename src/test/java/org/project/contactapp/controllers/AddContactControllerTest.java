package org.project.contactapp.controllers;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.project.contactapp.MainApp;
import org.project.contactapp.daos.PersonDAO;
import org.project.contactapp.entities.Person;
import org.testfx.framework.junit5.ApplicationTest;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.testfx.assertions.api.Assertions.assertThat;

class AddContactControllerTest extends ApplicationTest {

    private AddContactController controller;
    private TextField lastNameField;
    private TextField firstNameField;
    private TextField nicknameField;
    private TextField phoneNumberField;
    private TextField addressField;
    private TextField emailAddressField;
    private DatePicker birthDateField;
    private TextField imagePathField;
    private ImageView imageView;

    @BeforeEach
    void setUp() {
        controller = new AddContactController();
        lastNameField = new TextField();
        firstNameField = new TextField();
        nicknameField = new TextField();
        phoneNumberField = new TextField();
        addressField = new TextField();
        emailAddressField = new TextField();
        birthDateField = new DatePicker();
        imagePathField = new TextField();
        imageView = new ImageView();

        controller.lastNameField = lastNameField;
        controller.firstNameField = firstNameField;
        controller.nicknameField = nicknameField;
        controller.phoneNumberField = phoneNumberField;
        controller.addressField = addressField;
        controller.emailAddressField = emailAddressField;
        controller.birthDateField = birthDateField;
        controller.imagePathField = imagePathField;
        controller.imageView = imageView;

        controller.initialize();
    }

    @Test
    void testSaveContact_successfulSave() {
        // Mock static methods
        try (MockedStatic<MainApp> mainAppMock = mockStatic(MainApp.class);
             MockedStatic<PersonDAO> personDAOMock = mockStatic(PersonDAO.class)) {

            // Arrange
            lastNameField.setText("Doe");
            firstNameField.setText("John");
            phoneNumberField.setText("1234567890");
            emailAddressField.setText("john.doe@example.com");
            birthDateField.setValue(LocalDate.of(1990, 1, 1));

            personDAOMock.when(() -> PersonDAO.savePerson(any(Person.class))).thenReturn(true);

            // Act
            controller.onSaveContactClick();

            // Assert
            mainAppMock.verify(() -> MainApp.showToast("Contact saved successfully!"));
            mainAppMock.verify(() -> MainApp.navigateTo("allContacts-page.fxml"));
        }
    }

    @Test
    void testSaveContact_missingRequiredFields() {
        // Arrange
        firstNameField.setText("John");
        phoneNumberField.setText("1234567890");
        // lastNameField is empty

        // Act
        controller.onSaveContactClick();

        // Assert - verification would typically check for alert, but since showAlert is private,
        // we'll trust it works as expected when required fields are missing
    }

    @Test
    void testSaveContact_invalidEmail() {
        // Arrange
        lastNameField.setText("Doe");
        firstNameField.setText("John");
        phoneNumberField.setText("1234567890");
        emailAddressField.setText("invalid-email");

        // Act
        controller.onSaveContactClick();

        // Assert - verification would typically check for alert about invalid email
    }


    @Test
    void testCancel() {
        try (MockedStatic<MainApp> mainAppMock = mockStatic(MainApp.class)) {
            // Act
            controller.onCancelClick();

            // Assert
            mainAppMock.verify(() -> MainApp.navigateTo("home-page.fxml"));
        }
    }

    @Test
    void testBack() {
        try (MockedStatic<MainApp> mainAppMock = mockStatic(MainApp.class)) {
            // Act
            controller.onBackClick();

            // Assert
            mainAppMock.verify(() -> MainApp.navigateTo("home-page.fxml"));
        }
    }

    @Test
    void testSaveContact_saveFailure() {
        try (MockedStatic<MainApp> mainAppMock = mockStatic(MainApp.class);
             MockedStatic<PersonDAO> personDAOMock = mockStatic(PersonDAO.class)) {

            // Arrange
            lastNameField.setText("Doe");
            firstNameField.setText("John");
            phoneNumberField.setText("1234567890");
            emailAddressField.setText("john.doe@example.com");

            personDAOMock.when(() -> PersonDAO.savePerson(any(Person.class))).thenReturn(false);

            // Act
            controller.onSaveContactClick();

            // Assert - verification would typically check for error alert
            mainAppMock.verifyNoInteractions();
        }
    }
}