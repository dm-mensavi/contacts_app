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
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Create controller instance and inject mock PersonDAO
        controller = new DeleteContactController() {
            {
                this.personDAO = mockPersonDAO;
            }

            @Override
            protected boolean confirmDelete() {
                return true; // Simulate user clicking YES automatically
            }
        };
    }

    @Test
    void testSetSelectedContact() {
        // Arrange: Create a Person object with sample data
        Person person = new Person();
        person.setId(1);

        // Act: Set the selected contact in the controller
        controller.setSelectedContact(person);

        // Assert: Verify that the selected contact is set correctly
        assertThat(controller.selectedContact).isSameAs(person);
    }

    @Test
    void testOnDeleteClick_failure() throws SQLException {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Arrange: Set up a Person object and mock delete failure
            Person person = new Person();
            person.setId(1);
            controller.setSelectedContact(person);
            when(mockPersonDAO.deletePerson(1)).thenReturn(false);

            // Act: Simulate delete button click
            interact(() -> controller.onDeleteClick());

            // Assert: Verify that the delete operation was attempted and no navigation occurred
            verify(mockPersonDAO).deletePerson(1);
            mainAppMock.verifyNoInteractions();
        }
    }

    @Test
    void testOnDeleteClick_databaseError() throws SQLException {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Arrange: Set up a Person object and mock database error
            Person person = new Person();
            person.setId(1);
            controller.setSelectedContact(person);
            when(mockPersonDAO.deletePerson(1)).thenThrow(new SQLException("Delete error"));

            // Act: Simulate delete button click
            interact(() -> controller.onDeleteClick());

            // Assert: Verify that the delete operation was attempted and no navigation occurred
            verify(mockPersonDAO).deletePerson(1);
            mainAppMock.verifyNoInteractions();
        }
    }

    @Test
    void testOnCancelClick() {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Act: Simulate cancel button click
            controller.onCancelClick();

            // Assert: Verify navigation to all contacts page
            mainAppMock.verify(() -> MainApp.navigateTo("allContacts-page.fxml"));
        }
    }
}