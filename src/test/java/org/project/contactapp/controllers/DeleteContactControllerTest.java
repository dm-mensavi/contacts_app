package org.project.contactapp.controllers;

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

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class DeleteContactControllerTest extends ApplicationTest {

    private DeleteContactController controller;
    @Mock
    private PersonDAO mockPersonDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new DeleteContactController() {
            // Override constructor to inject mock PersonDAO
            {
                this.personDAO = mockPersonDAO;
            }
        };
    }

    @Test
    void testSetSelectedContact() {
        // Arrange
        Person person = new Person();
        person.setId(1);

        // Act
        controller.setSelectedContact(person);

        // Assert
        assertThat(controller.selectedContact).isSameAs(person);
    }

    @Test
    void testOnDeleteClick_success() throws SQLException {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Arrange
            Person person = new Person();
            person.setId(1);
            controller.setSelectedContact(person);
            when(mockPersonDAO.deletePerson(1)).thenReturn(true);

            // Act - Simulate confirmation (bypass Alert)
            interact(() -> controller.onDeleteClick());

            // Assert
            verify(mockPersonDAO).deletePerson(1);
            mainAppMock.verify(() -> MainApp.navigateTo("allContacts-page.fxml"));
        }
    }

    @Test
    void testOnDeleteClick_failure() throws SQLException {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Arrange
            Person person = new Person();
            person.setId(1);
            controller.setSelectedContact(person);
            when(mockPersonDAO.deletePerson(1)).thenReturn(false);

            // Act - Simulate confirmation
            interact(() -> controller.onDeleteClick());

            // Assert
            verify(mockPersonDAO).deletePerson(1);
            mainAppMock.verifyNoInteractions(); // Navigation should not occur on failure
        }
    }

    @Test
    void testOnDeleteClick_databaseError() throws SQLException {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Arrange
            Person person = new Person();
            person.setId(1);
            controller.setSelectedContact(person);
            when(mockPersonDAO.deletePerson(1)).thenThrow(new SQLException("Delete error"));

            // Act - Simulate confirmation
            interact(() -> controller.onDeleteClick());

            // Assert
            verify(mockPersonDAO).deletePerson(1);
            mainAppMock.verifyNoInteractions(); // Navigation should not occur on error
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