package org.project.contactapp.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.project.contactapp.DatabaseConnection.dbConnection;
import org.project.contactapp.MainApp;
import org.project.contactapp.daos.PersonDAO;
import org.project.contactapp.entities.Person;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DeleteContactController {

    Person selectedContact;
    PersonDAO personDAO;

    public DeleteContactController() {
        try {
            Connection connection = dbConnection.getConnection();
            this.personDAO = new PersonDAO(connection);
        } catch (SQLException e) {
            Platform.runLater(() -> showAlert("Database Error", "Failed to initialize database connection: " + e.getMessage()));
        }
    }

    public void setSelectedContact(Person contact) {
        this.selectedContact = contact;
    }

    @FXML
    protected void onDeleteClick() {
        if (personDAO == null) {
            showAlert("Error", "Database connection not available.");
            return;
        }

        // Show confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this contact?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    boolean success = personDAO.deletePerson(selectedContact.getId());
                    if (success) {
                        System.out.println("Contact deleted successfully!");
                        MainApp.navigateTo("allContacts-page.fxml"); // Navigate back to the all contacts page
                    } else {
                        showAlert("Error", "Failed to delete contact.");
                    }
                } catch (SQLException e) {
                    showAlert("Database Error", "Error deleting contact: " + e.getMessage());
                }
            }
        });
    }

    @FXML
    protected void onCancelClick() {
        MainApp.navigateTo("allContacts-page.fxml"); // Navigate back to the all contacts page
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

    protected abstract boolean confirmDelete();
}