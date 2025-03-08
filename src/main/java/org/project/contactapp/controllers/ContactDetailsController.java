package org.project.contactapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.project.contactapp.MainApp;
import org.project.contactapp.daos.PersonDAO;
import org.project.contactapp.entities.Person;

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

    @FXML
    private ImageView imageView;

    private Person selectedContact; // The contact being edited

    @FXML
    public void initialize() {
        // Enable editing for all fields
        setEditable(true);
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
        selectedContact.setLastname(lastNameField.getText());
        selectedContact.setFirstname(firstNameField.getText());
        selectedContact.setNickname(nicknameField.getText());
        selectedContact.setPhone_number(phoneNumberField.getText());
        selectedContact.setAddress(addressField.getText());
        selectedContact.setEmail_address(emailAddressField.getText());
        selectedContact.setBirth_date(birthDateField.getValue());

        boolean success = PersonDAO.updatePerson(selectedContact);

        if (success) {
            System.out.println("Contact updated successfully!");
            MainApp.navigateTo("allContacts-page.fxml");
        } else {
            System.out.println("Failed to update contact.");
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
}