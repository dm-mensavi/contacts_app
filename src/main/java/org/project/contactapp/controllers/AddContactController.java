package org.project.contactapp.controllers;

    import javafx.application.Platform;
    import javafx.fxml.FXML;
    import javafx.scene.control.*;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import javafx.stage.FileChooser;
    import org.project.contactapp.MainApp;
    import org.project.contactapp.daos.PersonDAO;
    import org.project.contactapp.entities.Person;

    import java.io.File;
    import java.time.LocalDate;
    import java.util.regex.Pattern;

    public class AddContactController {

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
        TextField imagePathField;
        @FXML
        ImageView imageView;

        @FXML
        public void initialize() {
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

        @FXML
        protected void onSaveContactClick() {
            String lastname = lastNameField.getText();
            String firstname = firstNameField.getText();
            String nickname = nicknameField.getText();
            String phoneNumber = phoneNumberField.getText();
            String address = addressField.getText();
            String emailAddress = emailAddressField.getText();
            LocalDate birthDate = birthDateField.getValue();
            String imagePath = imagePathField.getText();

            if (lastname.isEmpty() || firstname.isEmpty() || phoneNumber.isEmpty()) {
                showAlert("Validation Error", "Please fill in all required fields.");
                return;
            }

            if (!isValidEmail(emailAddress)) {
                showAlert("Validation Error", "Please enter a valid email address.");
                return;
            }

            Person newPerson = new Person();
            newPerson.setLastname(lastname);
            newPerson.setFirstname(firstname);
            newPerson.setNickname(nickname);
            newPerson.setPhone_number(phoneNumber);
            newPerson.setAddress(address);
            newPerson.setEmail_address(emailAddress);
            newPerson.setBirth_date(birthDate);
            newPerson.setImage_path(imagePath);

            boolean success = PersonDAO.savePerson(newPerson);

            if (success) {
                MainApp.showToast("Contact saved successfully!");
                MainApp.navigateTo("allContacts-page.fxml");
            } else {
                showAlert("Error", "Failed to save contact.");
            }
        }

        @FXML
        protected void onCancelClick() {
            MainApp.navigateTo("home-page.fxml");
        }

        @FXML
        protected void onBackClick() {
            MainApp.navigateTo("home-page.fxml");
        }

        @FXML
        protected void onBrowseImageClick() {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                imagePathField.setText(selectedFile.getAbsolutePath());
                imageView.setImage(new Image(selectedFile.toURI().toString()));
            }
        }

        private boolean isValidEmail(String email) {
            String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
            Pattern pattern = Pattern.compile(emailRegex);
            return pattern.matcher(email).matches();
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