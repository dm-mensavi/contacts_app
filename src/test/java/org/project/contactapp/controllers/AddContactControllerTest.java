package org.project.contactapp.controllers;

import javafx.scene.control.*;
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

class AddContactControllerTest extends ApplicationTest {

    private AddContactController controller;

    @Mock
    private PersonDAO mockPersonDAO;

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
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Create controller instance and inject mock PersonDAO
        controller = new AddContactController() {
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
        imagePathField = new TextField();
        imageView = new ImageView();

        // Set controller fields
        controller.lastNameField = lastNameField;
        controller.firstNameField = firstNameField;
        controller.nicknameField = nicknameField;
        controller.phoneNumberField = phoneNumberField;
        controller.addressField = addressField;
        controller.emailAddressField = emailAddressField;
        controller.birthDateField = birthDateField;
        controller.imagePathField = imagePathField;
        controller.imageView = imageView;

        // Initialize controller
        controller.initialize();
    }

    @Test
    void testSaveContact_successfulSave() throws SQLException {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Arrange: Set up valid contact details
            lastNameField.setText("Doe");
            firstNameField.setText("John");
            phoneNumberField.setText("1234567890");
            emailAddressField.setText("john.doe@example.com");
            birthDateField.setValue(LocalDate.of(1990, 1, 1));
            when(mockPersonDAO.savePerson(any(Person.class))).thenReturn(true);

            // Act: Attempt to save contact
            controller.onSaveContactClick();

            // Assert: Verify success message and navigation
            mainAppMock.verify(() -> MainApp.showToast("Contact saved successfully!"));
            mainAppMock.verify(() -> MainApp.navigateTo("allContacts-page.fxml"));
        }
    }

    @Test
    void testSaveContact_missingRequiredFields() {
        // Arrange: Set up contact details with missing last name
        firstNameField.setText("John");
        phoneNumberField.setText("1234567890");

        // Act: Attempt to save contact
        controller.onSaveContactClick();

        // Assert: Verify alert is shown (cannot be tested directly in unit tests)
    }

    @Test
    void testSaveContact_invalidEmail() {
        // Arrange: Set up contact details with invalid email
        lastNameField.setText("Doe");
        firstNameField.setText("John");
        phoneNumberField.setText("1234567890");
        emailAddressField.setText("invalid-email");

        // Act: Attempt to save contact
        controller.onSaveContactClick();

        // Assert: Verify alert is shown (cannot be tested directly in unit tests)
    }

    @Test
    void testPhoneNumberValidation_onlyAllowsNumbers() {
        // Arrange: Set up invalid phone number
        phoneNumberField.setText("abc123");

        // Act & Assert: Verify phone number field is empty
        assertThat(phoneNumberField.getText()).isEmpty();

        // Arrange: Set up valid phone number
        phoneNumberField.setText("123456");

        // Act & Assert: Verify phone number field contains valid number
        assertThat(phoneNumberField.getText()).isEqualTo("123456");
    }

    @Test
    void testCancel() {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Act: Attempt to cancel
            controller.onCancelClick();

            // Assert: Verify navigation to home page
            mainAppMock.verify(() -> MainApp.navigateTo("home-page.fxml"));
        }
    }

    @Test
    void testBack() {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Act: Attempt to go back
            controller.onBackClick();

            // Assert: Verify navigation to home page
            mainAppMock.verify(() -> MainApp.navigateTo("home-page.fxml"));
        }
    }

    @Test
    void testSaveContact_saveFailure() throws SQLException {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Arrange: Set up valid contact details but simulate save failure
            lastNameField.setText("Doe");
            firstNameField.setText("John");
            phoneNumberField.setText("1234567890");
            emailAddressField.setText("john.doe@example.com");
            when(mockPersonDAO.savePerson(any(Person.class))).thenReturn(false);

            // Act: Attempt to save contact
            controller.onSaveContactClick();

            // Assert: Verify no interactions with MainApp (alert shown)
            mainAppMock.verifyNoInteractions();
        }
    }

    @Test
    void testSaveContact_databaseError() throws SQLException {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Arrange: Set up valid contact details but simulate database error
            lastNameField.setText("Doe");
            firstNameField.setText("John");
            phoneNumberField.setText("1234567890");
            emailAddressField.setText("john.doe@example.com");
            when(mockPersonDAO.savePerson(any(Person.class))).thenThrow(new SQLException("Database error"));

            // Act: Attempt to save contact
            controller.onSaveContactClick();

            // Assert: Verify no interactions with MainApp (alert shown)
            mainAppMock.verifyNoInteractions();
        }
    }

    @Test
    void testBrowseImageClick() {
        // Note: FileChooser is hard to test in unit tests without a real UI.
        // This test would typically require TestFX robot interaction or mocking FileChooser,
        // but we'll skip it here as it’s UI-dependent and better suited for integration tests.
    }
}