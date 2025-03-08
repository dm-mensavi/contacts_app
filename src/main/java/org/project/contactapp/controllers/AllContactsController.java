package org.project.contactapp.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.project.contactapp.MainApp;
import org.project.contactapp.daos.PersonDAO;
import org.project.contactapp.entities.Person;

import java.util.List;
import java.util.stream.Collectors;

public class AllContactsController {

    @FXML
    private TableView<Person> contactTable; // TableView to display contacts

    @FXML
    private TableColumn<Person, String> firstNameColumn; // Column for First Name

    @FXML
    private TableColumn<Person, String> lastNameColumn; // Column for Last Name

    @FXML
    private TableColumn<Person, String> phoneNumberColumn; // Column for Phone Number

    @FXML
    private TextField searchField; // Search bar for filtering contacts

    @FXML
    private Button deleteContactButton; // Delete button

    private ObservableList<Person> allContacts;

    // Initialize the controller
    @FXML
    public void initialize() {
        // Bind columns to Person properties
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phone_number"));

        // Load all contacts into the TableView
        allContacts = FXCollections.observableArrayList(PersonDAO.getAllPersons());
        contactTable.setItems(allContacts);

        // Disable the delete button initially
        deleteContactButton.setDisable(true);

        // Add listener to enable/disable delete button based on selection
        contactTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            deleteContactButton.setDisable(newValue == null);
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            onSearchClick(); // Trigger search on text change
        });

        contactTable.setOnMouseClicked(this::handleRowDoubleClick);
    }

    // Handle the Delete button click
    @FXML
    private void onDeleteContactClick() {
        Person selectedContact = contactTable.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            boolean success = PersonDAO.deletePerson(selectedContact.getId());
            if (success) {
                allContacts.remove(selectedContact);
                System.out.println("Contact deleted successfully!");
            } else {
                System.out.println("Failed to delete contact.");
            }
        }
    }

    // Handle the Back button click
    @FXML
    private void onBackClick() {
        System.out.println("Back Clicked!");
        MainApp.navigateTo("home-page.fxml"); // Navigate back to the homepage
    }

    // Handle the Add Contact button click
    @FXML
    private void onAddContactClick() {
        System.out.println("Add Contact Clicked!");
        MainApp.navigateTo("addContact-page.fxml"); // Navigate to the Add Contact page
    }

    // Handle the Search button click
    @FXML
    protected void onSearchClick() {
        String query = searchField.getText().trim().toLowerCase(); // Get the search query

        if (query.isEmpty()) {
            // If the search query is empty, show all contacts
            contactTable.setItems(allContacts);
        } else {
            // Filter the contacts based on the search query
            List<Person> filteredContacts = allContacts.stream()
                    .filter(person -> person.getFirstname().toLowerCase().contains(query) ||
                            person.getLastname().toLowerCase().contains(query) ||
                            person.getPhone_number().toLowerCase().contains(query))
                    .collect(Collectors.toList());

            // Update the TableView with the filtered results
            contactTable.setItems(FXCollections.observableArrayList(filteredContacts));
        }
    }

    private void handleRowDoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) { // Check for double-click
            Person selectedContact = contactTable.getSelectionModel().getSelectedItem();
            if (selectedContact != null) {
                // Navigate to the detailed view page and pass the selected contact
                MainApp.navigateTo("contact-details.fxml", selectedContact);
            }
        }
    }
}