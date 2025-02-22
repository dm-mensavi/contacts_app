package org.project.contactapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public  class AddContactController {

    @FXML
    private TextField lastNameField;
    @FXML private TextField firstNameField;
    @FXML private TextField nicknameField;
    @FXML private TextField phoneNumberField;
    @FXML private TextField addressField;
    @FXML private TextField emailField;
    @FXML private DatePicker birthDatePicker;

    @FXML
    private void onSaveContactClick(ActionEvent event) {
        // Example: Show an alert when the button is clicked
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Contact Saved");
        alert.setHeaderText(null);
        alert.setContentText("The contact has been successfully saved!");
        alert.showAndWait();
    }

    @FXML
    private void onCancelClick(ActionEvent event) {
        // Close the window or navigate back
        System.out.println("Cancel button clicked.");
    }
}
