package org.project.contactapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.project.contactapp.DatabaseConnection.dbConnection;
import org.project.contactapp.controllers.ContactDetailsController;
import org.project.contactapp.entities.Person;
import org.project.contactapp.utils.Toaster;

public class MainApp extends Application {
    private static StackPane root;
    private static Toaster toaster;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize the database
        dbConnection.initializeDatabase();

        // Load the homepage FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/contactapp/home-page.fxml"));
        Parent homepage = loader.load();

        // Set up the root container
        root = new StackPane();
        root.getChildren().add(homepage);

        // Set up the main stage
        Scene scene = new Scene(root, 400, 700);
        primaryStage.setTitle("Contact App");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initialize the toaster
        toaster = new Toaster(primaryStage);
    }

    public static void navigateTo(String fxmlFile) {
        navigateTo(fxmlFile, null);
    }

    public static void navigateTo(String fxmlFile, Object data) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/org/project/contactapp/" + fxmlFile));
            Parent newScene = loader.load();

            if (data != null) {
                Object controller = loader.getController();
                if (controller instanceof ContactDetailsController) {
                    ((ContactDetailsController) controller).setSelectedContact((Person) data);
                }
            }

            root.getChildren().clear();
            root.getChildren().add(newScene);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to load FXML file: " + fxmlFile);
        }
    }

    public static void showToast(String message) {
        toaster.showToast(message);
    }

    public static void main(String[] args) {
        launch(args);
    }
}