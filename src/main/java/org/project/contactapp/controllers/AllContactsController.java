//package org.project.contactapp.controllers;
//
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.ListView;
//import javafx.scene.control.TextField;
//import org.project.contactapp.MainApp;
//import org.project.contactapp.entities.Person;
//import org.project.contactapp.daos.PersonDAO;
//
//import java.util.List;
//
//public class AllContactsController {
//
//    @FXML
//    private ListView<Person> contactList; // ListView to display contacts
//
//    @FXML
//    private TextField searchField; // Search bar for filtering contacts
//
//    @FXML
//    private Button backButton; // Back button to return to the homepage
//
//    @FXML
//    private Button addContactButton; // Button to navigate to the Add Contact page
//
//    // Initialize the controller
//    @FXML
//    public void initialize() {
//        // Load all contacts into the ListView
//        loadContacts();
//
//        // Set up event handlers
//        backButton.setOnAction(event -> onBackClick());
//        addContactButton.setOnAction(event -> onAddContactClick());
//    }
//
//    // Load all contacts from the database into the ListView
//    private void loadContacts() {
//        try {
////            PersonDAO personDAO = new PersonDAO();
//            List<Person> persons = PersonDAO.getAllPersons(); // Fetch all contacts
//            contactList.getItems().clear(); // Clear the ListView
//            contactList.getItems().addAll(persons); // Add contacts to the ListView
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println("Failed to load contacts.");
//        }
//    }
//
//    // Handle the Back button click
//    @FXML
//    private void onBackClick() {
//        System.out.println("Back Clicked!");
//        MainApp.navigateTo("home-page.fxml"); // Navigate back to the homepage
//    }
//
//    // Handle the Add Contact button click
//    @FXML
//    private void onAddContactClick() {
//        System.out.println("Add Contact Clicked!");
//        MainApp.navigateTo("addContact-page.fxml"); // Navigate to the Add Contact page
//    }
//
//    // Handle the Search button click (if you add a Search button)
//    @FXML
//    private void onSearchClick() {
////        String searchTerm = searchField.getText().trim(); // Get the search term
////        if (!searchTerm.isEmpty()) {
////            try {
////                List<Person> filteredContacts = PersonDAO.searchContacts(searchTerm); // Search contacts
////                contactList.getItems().clear(); // Clear the ListView
////                contactList.getItems().addAll(filteredContacts); // Add filtered contacts to the ListView
////            } catch (Exception e) {
////                e.printStackTrace();
////                System.err.println("Failed to search contacts.");
////            }
////        } else {
////            loadContacts(); // Reload all contacts if the search term is empty
////        }
//    }
//}


package org.project.contactapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.project.contactapp.MainApp;
import org.project.contactapp.entities.Person;
import org.project.contactapp.daos.PersonDAO;

import java.util.List;

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
    private Button backButton; // Back button to return to the homepage

    @FXML
    private Button addContactButton; // Button to navigate to the Add Contact page

    // Initialize the controller
    @FXML
    public void initialize() {
        // Bind columns to Person properties
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phone_number"));

        // Load all contacts into the TableView
//        loadContacts();

        // Set up event handlers
//        backButton.setOnAction(event -> onBackClick());
//        addContactButton.setOnAction(event -> onAddContactClick());
        contactTable.getItems().setAll(PersonDAO.getAllPersons());
    }

    // Load all contacts from the database into the TableView
    private void loadContacts() {
//        try {
//            List<Person> persons = PersonDAO.getImportantPersons(); // Fetch all contacts
//            contactTable.getItems().clear(); // Clear the TableView
//            contactTable.getItems().addAll(persons); // Add contacts to the TableView
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println("Failed to load contacts.");
//        }
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
    private void onSearchClick() {
//        String searchTerm = searchField.getText().trim(); // Get the search term
//        if (!searchTerm.isEmpty()) {
//            try {
//                List<Person> filteredContacts = PersonDAO.searchContacts(searchTerm); // Search contacts
//                contactTable.getItems().clear(); // Clear the TableView
//                contactTable.getItems().addAll(filteredContacts); // Add filtered contacts to the TableView
//            } catch (Exception e) {
//                e.printStackTrace();
//                System.err.println("Failed to search contacts.");
//            }
//        } else {
//            loadContacts(); // Reload all contacts if the search term is empty
//        }
    }
}