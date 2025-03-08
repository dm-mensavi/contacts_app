package org.project.contactapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.project.contactapp.MainApp;
import org.project.contactapp.daos.PersonDAO;
import org.project.contactapp.entities.Person;

public class DeleteContactController {

    private Person selectedContact;

    public void setSelectedContact(Person contact) {
        this.selectedContact = contact;
    }

    @FXML
    protected void onDeleteClick() {
        // Show confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this contact?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                // Delete the contact
                boolean success = PersonDAO.deletePerson(selectedContact.getId());
                if (success) {
                    System.out.println("Contact deleted successfully!");
                    MainApp.navigateTo("allContacts-page.fxml"); // Navigate back to the all contacts page
                } else {
                    System.out.println("Failed to delete contact.");
                }
            }
        });
    }

    @FXML
    protected void onCancelClick() {
        MainApp.navigateTo("allContacts-page.fxml"); // Navigate back to the all contacts page
    }
}