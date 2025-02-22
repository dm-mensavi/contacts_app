package org.project.contactapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.project.contactapp.MainApp;

public class AllContactsController {
//    public static class AddContactController {
//
//        @FXML
//        private TextField lastNameField;
//        @FXML private TextField firstNameField;
//        @FXML private TextField nicknameField;
//        @FXML private TextField phoneNumberField;
//        @FXML private TextField addressField;
//        @FXML private TextField emailField;
//        @FXML private DatePicker birthDatePicker;
//
//        @FXML
//        private void onSaveContactClick(ActionEvent event) {
//            // Example: Show an alert when the button is clicked
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Contact Saved");
//            alert.setHeaderText(null);
//            alert.setContentText("The contact has been successfully saved!");
//            alert.showAndWait();
//        }
//
//        @FXML
//        private void onCancelClick(ActionEvent event) {
//            // Close the window or navigate back
//            System.out.println("Cancel button clicked.");
//        }
//    }

//    public static class HomePageController {
//        @FXML
//        private Label welcomeText;
//
//        @FXML
//        protected void onHelloButtonClick() {
//            welcomeText.setText("Welcome to JavaFX Application!");
//        }
//
//        @FXML
//        protected void onViewContactsClick() {
//            try {
//                System.out.println("View Contacts Clicked!");
//                FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("allContacts-page.fxml"));
//    //            Parent root = fxmlLoader.load();
//                Scene scene = new Scene(fxmlLoader.load(), 300, 500);
//                Stage stage = new Stage();
//                stage.setTitle("All Contacts");
//                stage.setScene(scene);
//                stage.show();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @FXML
//        protected void onAddContactClick() {
//            try {
//                System.out.println("Add Contact Clicked!");
//                FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("addContact-page.fxml"));
//    //            Parent root = fxmlLoader.load();
//                Scene scene = new Scene(fxmlLoader.load(), 300, 500);
//                Stage stage = new Stage();
//                stage.setTitle("Add Contact");
//                stage.setScene(scene);
//                stage.show();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
