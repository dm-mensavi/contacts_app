package org.project.contactapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.project.contactapp.MainApp;

public class HomePageController {
    @FXML
    private ImageView imageView;

    public HomePageController() {
        // Empty constructor for now; can be used for future dependencies if needed
    }

    @FXML
    public void initialize() {
        try {
            Image image = new Image(getClass().getResource("/org/project/contactapp/images/background.png").toExternalForm());
            imageView.setImage(image);
        } catch (NullPointerException e) {
            System.out.println("Error: Image file not found. Check resource path.");
        }
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
}//package org.project.contactapp.controllers;
//
//import javafx.fxml.FXML;
//import org.project.contactapp.MainApp;
//
//public class HomePageController {
//
//    public HomePageController() {
//        // Empty constructor for now; can be used for future dependencies if needed
//    }
//
//    @FXML
//    protected void onViewContactsClick() {
//        System.out.println("View Contacts Clicked!");
//        MainApp.navigateTo("allContacts-page.fxml"); // Navigate to the "View Contacts" page
//    }
//
//    @FXML
//    protected void onAddContactClick() {
//        System.out.println("Add Contact Clicked!");
//        MainApp.navigateTo("addContact-page.fxml"); // Navigate to the "Add Contact" page
//    }
//}