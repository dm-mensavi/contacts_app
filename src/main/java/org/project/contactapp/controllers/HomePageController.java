package org.project.contactapp.controllers;

import javafx.fxml.FXML;
import org.project.contactapp.MainApp;

public class HomePageController {

    public HomePageController() {
        // Empty constructor for now; can be used for future dependencies if needed
    }

    @FXML
    protected void onViewContactsClick() {
        System.out.println("View Contacts Clicked!");
        MainApp.navigateTo("allContacts-page.fxml"); // Navigate to the "View Contacts" page
    }

    @FXML
    protected void onAddContactClick() {
        System.out.println("Add Contact Clicked!");
        MainApp.navigateTo("addContact-page.fxml"); // Navigate to the "Add Contact" page
    }
}