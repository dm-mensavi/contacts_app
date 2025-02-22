package org.project.contactapp.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.project.contactapp.MainApp;





public class HomePageController {

        @FXML
        private Label welcomeText;

        @FXML
        protected void onHelloButtonClick() {
            welcomeText.setText("Welcome to JavaFX Application!");
        }

        @FXML
        protected void onViewContactsClick() {
            try {
                System.out.println("View Contacts Clicked!");
                FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("allContacts-page.fxml"));
                //            Parent root = fxmlLoader.load();
                Scene scene = new Scene(fxmlLoader.load(), 300, 500);
                Stage stage = new Stage();
                stage.setTitle("All Contacts");
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @FXML
        protected void onAddContactClick() {
            try {
                System.out.println("Add Contact Clicked!");
                FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("addContact-page.fxml"));
                //            Parent root = fxmlLoader.load();
                Scene scene = new Scene(fxmlLoader.load(), 300, 500);
                Stage stage = new Stage();
                stage.setTitle("Add Contact");
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


