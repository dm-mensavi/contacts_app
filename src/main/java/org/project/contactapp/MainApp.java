//
//package org.project.contactapp;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.layout.StackPane;
//import javafx.stage.Stage;
//
//public class MainApp extends Application {
//    private static StackPane root;
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        // Load the homepage FXML
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/contactapp/home-page.fxml"));
//        Parent homepage = loader.load();
//
//        // Set up the root container
//        root = new StackPane();
//        root.getChildren().add(homepage);
//
//        // Set up the main stage
//        Scene scene = new Scene(root, 300, 500);
//        primaryStage.setTitle("Contact App");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    /**
//     * Switches the current scene in the root container.
//     *
//     * @param fxmlFile The FXML file to load (e.g., "home-page.fxml").
//     */
//    public static void navigateTo(String fxmlFile) {
//        try {
//            // Load the FXML file from the correct path
//            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/org/project/contactapp/" + fxmlFile));
//            Parent newScene = loader.load();
//            root.getChildren().clear(); // Clear the current scene
//            root.getChildren().add(newScene); // Add the new scene
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println("Failed to load FXML file: " + fxmlFile);
//        }
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}


package org.project.contactapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.project.contactapp.controllers.ContactDetailsController;
import org.project.contactapp.entities.Person;

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
        navigateTo(fxmlFile, null); // Call the overloaded method with null data
    }

    /**
     * Switches the current scene in the root container and passes data to the controller.
     *
     * @param fxmlFile The FXML file to load (e.g., "contact-details.fxml").
     * @param data     The data to pass to the controller (e.g., a Person object).
     */
    public static void navigateTo(String fxmlFile, Object data) {
        try {
            // Load the FXML file from the correct path
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/org/project/contactapp/" + fxmlFile));
            Parent newScene = loader.load();

            // Pass data to the controller (if applicable)
            if (data != null) {
                Object controller = loader.getController();
                if (controller instanceof ContactDetailsController) {
                    ((ContactDetailsController) controller).setSelectedContact((Person) data);
                }
            }

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