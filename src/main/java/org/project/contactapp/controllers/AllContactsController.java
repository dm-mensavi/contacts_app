package org.project.contactapp.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.project.contactapp.MainApp;
import org.project.contactapp.daos.PersonDAO;
import org.project.contactapp.entities.Person;

import java.util.List;
import java.util.stream.Collectors;

public class AllContactsController {

    @FXML
    TableView<Person> contactTable;
    @FXML
    TableColumn<Person, String> firstNameColumn;
    @FXML
    TableColumn<Person, String> lastNameColumn;
    @FXML
    TableColumn<Person, String> phoneNumberColumn;
    @FXML
    TextField searchField;
    @FXML
    Button deleteContactButton;

    ObservableList<Person> allContacts;

    @FXML
    public void initialize() {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phone_number"));

        allContacts = FXCollections.observableArrayList(PersonDAO.getAllPersons());
        contactTable.setItems(allContacts);

        deleteContactButton.setDisable(true);

        contactTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            deleteContactButton.setDisable(newValue == null);
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            onSearchClick();
        });

        contactTable.setOnMouseClicked(this::handleRowDoubleClick);
    }

    @FXML
    void onDeleteContactClick() {
        Person selectedContact = contactTable.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this contact?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    boolean success = PersonDAO.deletePerson(selectedContact.getId());
                    if (success) {
                        allContacts.remove(selectedContact);
                        System.out.println("Contact deleted successfully!");
                    } else {
                        System.out.println("Failed to delete contact.");
                    }
                }
            });
        }
    }

    @FXML
    void onBackClick() {
        MainApp.navigateTo("home-page.fxml");
    }

    @FXML
    void onAddContactClick() {
        MainApp.navigateTo("addContact-page.fxml");
    }

    @FXML
    protected void onSearchClick() {
        String query = searchField.getText().trim().toLowerCase();

        if (query.isEmpty()) {
            contactTable.setItems(allContacts);
        } else {
            List<Person> filteredContacts = allContacts.stream()
                    .filter(person -> person.getFirstname().toLowerCase().contains(query) ||
                            person.getLastname().toLowerCase().contains(query) ||
                            person.getPhone_number().toLowerCase().contains(query))
                    .collect(Collectors.toList());

            contactTable.setItems(FXCollections.observableArrayList(filteredContacts));
        }
    }

    void handleRowDoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Person selectedContact = contactTable.getSelectionModel().getSelectedItem();
            if (selectedContact != null) {
                MainApp.navigateTo("contact-details.fxml", selectedContact);
            }
        }
    }
}