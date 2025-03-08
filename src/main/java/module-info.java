module org.project.contactapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens org.project.contactapp to javafx.fxml;
    exports org.project.contactapp;
}