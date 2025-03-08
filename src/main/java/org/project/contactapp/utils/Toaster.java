package org.project.contactapp.utils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Toaster {
    private final Stage stage;

    public Toaster(Stage stage) {
        this.stage = stage;
    }

    public void showToast(String message) {
        Popup popup = new Popup();
        popup.setAutoFix(true);
        popup.setAutoHide(true);
        popup.setHideOnEscape(true);

        Label label = new Label(message);
        label.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-padding: 10px;");
        StackPane pane = new StackPane(label);
        pane.setStyle("-fx-background-color: black; -fx-padding: 10px;");
        popup.getContent().add(pane);

        popup.show(stage);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> popup.hide()));
        timeline.play();
    }
}