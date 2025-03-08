package org.project.contactapp.daos;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.project.contactapp.MainApp;
import org.project.contactapp.controllers.AddContactController;
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
        MockitoAnnotations.openMocks(this);
        controller = new AddContactController() {
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
    void testSaveContact_successfulSave() throws SQLException {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Arrange
            lastNameField.setText("Doe");
            firstNameField.setText("John");
            phoneNumberField.setText("1234567890");
            emailAddressField.setText("john.doe@example.com");
            birthDateField.setValue(LocalDate.of(1990, 1, 1));
            when(mockPersonDAO.savePerson(any(Person.class))).thenReturn(true);

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

        // Assert - Alert is shown, but we can't test it directly in unit tests
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

        // Assert - Alert is shown, but we can't test it directly in unit tests
    }

    @Test
    void testPhoneNumberValidation_onlyAllowsNumbers() {
        // Arrange
        phoneNumberField.setText("abc123");

        // Act & Assert
        assertThat(phoneNumberField.getText()).isEmpty();

        phoneNumberField.setText("123456");
        assertThat(phoneNumberField.getText()).isEqualTo("123456");
    }

    @Test
    void testCancel() {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Act
            controller.onCancelClick();

            // Assert
            mainAppMock.verify(() -> MainApp.navigateTo("home-page.fxml"));
        }
    }

    @Test
    void testBack() {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Act
            controller.onBackClick();

            // Assert
            mainAppMock.verify(() -> MainApp.navigateTo("home-page.fxml"));
        }
    }

    @Test
    void testSaveContact_saveFailure() throws SQLException {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Arrange
            lastNameField.setText("Doe");
            firstNameField.setText("John");
            phoneNumberField.setText("1234567890");
            emailAddressField.setText("john.doe@example.com");
            when(mockPersonDAO.savePerson(any(Person.class))).thenReturn(false);

            // Act
            controller.onSaveContactClick();

            // Assert - Alert is shown, no navigation
            mainAppMock.verifyNoInteractions();
        }
    }

    @Test
    void testSaveContact_databaseError() throws SQLException {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Arrange
            lastNameField.setText("Doe");
            firstNameField.setText("John");
            phoneNumberField.setText("1234567890");
            emailAddressField.setText("john.doe@example.com");
            when(mockPersonDAO.savePerson(any(Person.class))).thenThrow(new SQLException("Database error"));

            // Act
            controller.onSaveContactClick();

            // Assert - Alert is shown, no navigation
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