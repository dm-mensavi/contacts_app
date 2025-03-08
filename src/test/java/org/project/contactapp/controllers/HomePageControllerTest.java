package org.project.contactapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.project.contactapp.MainApp;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

class HomePageControllerTest extends ApplicationTest {

    private HomePageController controller;

    @BeforeEach
    void setUp() {
        controller = new HomePageController();
    }

    @Test
    void testOnViewContactsClick() {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Redirect System.out for testing println
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(outContent));

            // Act
            controller.onViewContactsClick();

            // Assert
            mainAppMock.verify(() -> MainApp.navigateTo("allContacts-page.fxml"));
            assertThat(outContent.toString()).isEqualTo("View Contacts Clicked!" + System.lineSeparator());

            // Restore System.out
            System.setOut(originalOut);
        }
    }

    @Test
    void testOnAddContactClick() {
        try (MockedStatic<MainApp> mainAppMock = Mockito.mockStatic(MainApp.class)) {
            // Redirect System.out for testing println
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(outContent));

            // Act
            controller.onAddContactClick();

            // Assert
            mainAppMock.verify(() -> MainApp.navigateTo("addContact-page.fxml"));
            assertThat(outContent.toString()).isEqualTo("Add Contact Clicked!" + System.lineSeparator());

            // Restore System.out
            System.setOut(originalOut);
        }
    }
}