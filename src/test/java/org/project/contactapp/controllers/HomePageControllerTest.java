package org.project.contactapp.controllers;

import javafx.fxml.FXML;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.project.contactapp.MainApp;
import org.testfx.framework.junit5.ApplicationTest;

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
            // Act
            controller.onViewContactsClick();

            // Assert
            mainAppMock.verify(() -> MainApp.navigateTo("allContacts-page.fxml"));
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
}