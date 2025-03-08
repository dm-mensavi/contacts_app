package org.project.contactapp.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.project.contactapp.DatabaseConnection.dbConnection;
import org.project.contactapp.MainApp;
import org.project.contactapp.daos.PersonDAO;
import org.project.contactapp.entities.Person;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class ContactDetailsController {

    @FXML
    TextField lastNameField;

    @FXML
    TextField firstNameField;

    @FXML
    TextField nicknameField;

    @FXML
    TextField phoneNumberField;

    @FXML
    TextField addressField;

    @FXML
    TextField emailAddressField;

    @FXML
    DatePicker birthDateField;

    @FXML
    ImageView imageView;

    Person selectedContact; // The contact being edited
    PersonDAO personDAO;

    public ContactDetailsController() {
        try {
            Connection connection = dbConnection.getConnection();
            this.personDAO = new PersonDAO(connection);
        } catch (SQLException e) {
            Platform.runLater(() -> showAlert("Database Error", "Failed to initialize database connection: " + e.getMessage()));
        }
    }

    @FXML
    public void initialize() {
        // Enable editing for all fields
        setEditable(true);
        setPhoneNumberValidation();
    }

    private void setPhoneNumberValidation() {
        Pattern pattern = Pattern.compile("\\d*");
        TextFormatter<String> formatter = new TextFormatter<>(change -> {
            if (pattern.matcher(change.getControlNewText()).matches()) {
                return change;
            } else {
                return null;
            }
        });
        phoneNumberField.setTextFormatter(formatter);
    }

    public void setSelectedContact(Person contact) {
        this.selectedContact = contact;

        // Populate the fields with the contact's data
        lastNameField.setText(selectedContact.getLastname());
        firstNameField.setText(selectedContact.getFirstname());
        nicknameField.setText(selectedContact.getNickname());
        phoneNumberField.setText(selectedContact.getPhone_number());
        addressField.setText(selectedContact.getAddress());
        emailAddressField.setText(selectedContact.getEmail_address());
        birthDateField.setValue(selectedContact.getBirth_date());

        // Load the image if the path is not null or empty
        if (selectedContact.getImage_path() != null && !selectedContact.getImage_path().isEmpty()) {
            imageView.setImage(new Image("file:" + selectedContact.getImage_path()));
        }
    }

    private void setEditable(boolean editable) {
        lastNameField.setEditable(editable);
        firstNameField.setEditable(editable);
        nicknameField.setEditable(editable);
        phoneNumberField.setEditable(editable);
        addressField.setEditable(editable);
        emailAddressField.setEditable(editable);
        birthDateField.setDisable(!editable);
    }

    @FXML
    protected void onSaveClick() {
        if (personDAO == null) {
            showAlert("Error", "Database connection not available.");
            return;
        }

        selectedContact.setLastname(lastNameField.getText());
        selectedContact.setFirstname(firstNameField.getText());
        selectedContact.setNickname(nicknameField.getText());
        selectedContact.setPhone_number(phoneNumberField.getText());
        selectedContact.setAddress(addressField.getText());
        selectedContact.setEmail_address(emailAddressField.getText());
        selectedContact.setBirth_date(birthDateField.getValue());

        try {
            boolean success = personDAO.updatePerson(selectedContact);
            if (success) {
                System.out.println("Contact updated successfully!");
                MainApp.navigateTo("allContacts-page.fxml");
            } else {
                showAlert("Error", "Failed to update contact.");
            }
        } catch (SQLException e) {
            showAlert("Database Error", "Error updating contact: " + e.getMessage());
        }
    }

    @FXML
    protected void onCancelClick() {
        setSelectedContact(selectedContact);
    }

    @FXML
    protected void onBackClick() {
        MainApp.navigateTo("allContacts-page.fxml");
    }

    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}