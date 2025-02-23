package org.project.contactapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.project.contactapp.MainApp;
import org.project.contactapp.daos.PersonDAO;
import org.project.contactapp.entities.Person;

import java.time.LocalDate;

public class AddContactController {

    // Form fields
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
    protected void onBackClick() {
        System.out.println("Back Clicked!");
        MainApp.navigateTo("home-page.fxml"); // Navigate back to the homepage
    }

    @FXML
    protected void onSaveContactClick() {
        System.out.println("Save Contact Clicked!");

        // Capture input from the form fields
        String lastname = lastNameField.getText();
        String firstname = firstNameField.getText();
        String nickname = nicknameField.getText();
        String phoneNumber = phoneNumberField.getText();
        String address = addressField.getText();
        String emailAddress = emailAddressField.getText();
        LocalDate birthDate = birthDateField.getValue();

        // Validate input (optional)
        if (lastname.isEmpty() || firstname.isEmpty() || phoneNumber.isEmpty()) {
            System.out.println("Please fill in all required fields.");
            return; // Exit if required fields are empty
        }

        // Create a new Person object
        Person newPerson = new Person();
        newPerson.setLastname(lastname);
        newPerson.setFirstname(firstname);
        newPerson.setNickname(nickname);
        newPerson.setPhone_number(phoneNumber);
        newPerson.setAddress(address);
        newPerson.setEmail_address(emailAddress);
        newPerson.setBirth_date(birthDate);

        // Save the new person to the database
        boolean success = PersonDAO.savePerson(newPerson);

        if (success) {
            System.out.println("Contact saved successfully!");
            MainApp.navigateTo("allContacts-page.fxml"); // Navigate back to the homepage
        } else {
            System.out.println("Failed to save contact.");
        }
    }

    @FXML
    protected void onCancelClick() {
        System.out.println("Cancel Clicked!");
        MainApp.navigateTo("home-page.fxml"); // Navigate back to the homepage
    }
}