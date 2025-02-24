package org.project.contactapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.project.contactapp.MainApp;
import org.project.contactapp.daos.PersonDAO;
import org.project.contactapp.entities.Person;

import java.time.LocalDate;

public class ContactDetailsController {

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField nicknameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField emailAddressField;

    @FXML
    private DatePicker birthDateField;

    private Person selectedContact; // The contact being edited
    private boolean isEditing = false; // Track whether we're in edit mode

    @FXML
    public void initialize() {
        // Enable editing for all fields
        setEditable(true);
    }

    /**
     * Sets the selected contact and populates the fields.
     *
     * @param contact The contact to display/edit.
     */
    public void setSelectedContact(Person contact) {
        this.selectedContact = contact;

        // Populate the fields with the contact's data
        lastNameField.setText(selectedContact.getLastname());
        firstNameField.setText(selectedContact.getFirstname());
        nicknameField.setText(selectedContact.getNickname());
        phoneNumberField.setText(selectedContact.getPhone_number());
        addressField.setText(selectedContact.getAddress());
        emailAddressField.setText(selectedContact.getEmail_address());

        // Format the birth date (if not null)
        if (selectedContact.getBirth_date() != null) {
            birthDateField.setValue(selectedContact.getBirth_date());
        } else {
            birthDateField.setValue(null);
        }
    }

    /**
     * Enables or disables editing for all fields.
     *
     * @param editable Whether the fields should be editable.
     */
    private void setEditable(boolean editable) {
        lastNameField.setEditable(editable);
        firstNameField.setEditable(editable);
        nicknameField.setEditable(editable);
        phoneNumberField.setEditable(editable);
        addressField.setEditable(editable);
        emailAddressField.setEditable(editable);
        birthDateField.setDisable(!editable);
    }

    /**
     * Handles the Save button click.
     */
    @FXML
    protected void onSaveClick() {
        // Update the selected contact with the new data
        selectedContact.setLastname(lastNameField.getText());
        selectedContact.setFirstname(firstNameField.getText());
        selectedContact.setNickname(nicknameField.getText());
        selectedContact.setPhone_number(phoneNumberField.getText());
        selectedContact.setAddress(addressField.getText());
        selectedContact.setEmail_address(emailAddressField.getText());
        selectedContact.setBirth_date(birthDateField.getValue());

        // Save the updated contact to the database
        boolean success = PersonDAO.updatePerson(selectedContact);

        if (success) {
            System.out.println("Contact updated successfully!");
            MainApp.navigateTo("allContacts-page.fxml"); // Navigate back to the all contacts page
        } else {
            System.out.println("Failed to update contact.");
        }
    }

    /**
     * Handles the Cancel button click.
     */
    @FXML
    protected void onCancelClick() {
        // Reset the fields to the original values
        setSelectedContact(selectedContact);
    }

    /**
     * Handles the Back button click.
     */
    @FXML
    protected void onBackClick() {
        System.out.println("Back Clicked!");
        MainApp.navigateTo("allContacts-page.fxml"); // Navigate back to the all contacts page
    }
}