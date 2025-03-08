package org.project.contactapp.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.project.contactapp.MainApp;
import org.project.contactapp.daos.PersonDAO;
import org.project.contactapp.entities.Person;
import org.testfx.framework.junit5.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DeleteContactControllerTest extends ApplicationTest {

    private DeleteContactController controller;
    private Person selectedContact;

    @BeforeEach
    void setUp() {
        controller = new DeleteContactController();
        selectedContact = new Person();
        selectedContact.setId(1); // Assuming Person has an ID field
        controller.setSelectedContact(selectedContact);
    }

    @Test
    void testSetSelectedContact() {
        // Arrange
        Person newContact = new Person();
        newContact.setId(2);

        // Act
        controller.setSelectedContact(newContact);

        // Assert
        assertThat(controller.selectedContact).isSameAs(newContact);
    }

    @Test
    void testOnDeleteClick_success() {
        try (MockedStatic<PersonDAO> personDAOMock = Mockito.mockStatic(PersonDAO.class);
             MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Arrange
            personDAOMock.when(() -> PersonDAO.deletePerson(1)).thenReturn(true);

            // Act - We can't easily simulate the Alert dialog, so we’ll call the logic directly
            interact(() -> {
                Alert alert = mock(Alert.class);
                when(alert.showAndWait()).thenReturn(java.util.Optional.of(ButtonType.YES));
                controller.onDeleteClick(); // This would normally show the dialog
            });

            // Assert
            personDAOMock.verify(() -> PersonDAO.deletePerson(1));
            mainAppMock.verify(() -> MainApp.navigateTo("allContacts-page.fxml"));
        }
    }

    @Test
    void testOnDeleteClick_failure() {
        try (MockedStatic<PersonDAO> personDAOMock = Mockito.mockStatic(PersonDAO.class);
             MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Arrange
            personDAOMock.when(() -> PersonDAO.deletePerson(1)).thenReturn(false);

            // Act
            interact(() -> {
                Alert alert = mock(Alert.class);
                when(alert.showAndWait()).thenReturn(java.util.Optional.of(ButtonType.YES));
                controller.onDeleteClick();
            });

            // Assert
            personDAOMock.verify(() -> PersonDAO.deletePerson(1));
            mainAppMock.verifyNoInteractions(); // Navigation should not occur on failure
        }
    }

    @Test
    void testOnDeleteClick_cancel() {
        try (MockedStatic<PersonDAO> personDAOMock = Mockito.mockStatic(PersonDAO.class);
             MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Act
            interact(() -> {
                Alert alert = mock(Alert.class);
                when(alert.showAndWait()).thenReturn(java.util.Optional.of(ButtonType.NO));
                controller.onDeleteClick();
            });

            // Assert
            personDAOMock.verifyNoInteractions(); // Delete should not be called
            mainAppMock.verifyNoInteractions(); // Navigation should not occur
        }
    }

    @Test
    void testOnCancelClick() {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Act
            controller.onCancelClick();

            // Assert
            mainAppMock.verify(() -> MainApp.navigateTo("allContacts-page.fxml"));
        }
    }
}