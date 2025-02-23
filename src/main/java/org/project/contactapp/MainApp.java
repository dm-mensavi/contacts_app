
package org.project.contactapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {
    private static StackPane root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the homepage FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/contactapp/home-page.fxml"));
        Parent homepage = loader.load();

        // Set up the root container
        root = new StackPane();
        root.getChildren().add(homepage);

        // Set up the main stage
        Scene scene = new Scene(root, 300, 500);
        primaryStage.setTitle("Contact App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Switches the current scene in the root container.
     *
     * @param fxmlFile The FXML file to load (e.g., "home-page.fxml").
     */
    public static void navigateTo(String fxmlFile) {
        try {
            // Load the FXML file from the correct path
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/org/project/contactapp/" + fxmlFile));
            Parent newScene = loader.load();
            root.getChildren().clear(); // Clear the current scene
            root.getChildren().add(newScene); // Add the new scene
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to load FXML file: " + fxmlFile);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}