//module org.project.contactapp {
//    requires javafx.controls;
//    requires javafx.fxml;
//
//    requires com.dlsc.formsfx;
//
//    requires java.sql;
//
//    opens org.project.contactapp to javafx.fxml;
//    exports org.project.contactapp;
//    exports org.project.contactapp.controllers;
//    opens org.project.contactapp.controllers to javafx.fxml;
//
//     // Add this if AssertJ is a named module
//    opens org.project.contactapp.entities to org.assertj.core;
//}
//


module org.project.contactapp {
    // JavaFX modules
    requires javafx.controls;
    requires javafx.fxml;

    // Third-party libraries
    requires com.dlsc.formsfx;
    requires java.sql;



    // Open packages for reflection
    opens org.project.contactapp to javafx.fxml;
    opens org.project.contactapp.controllers to javafx.fxml;
    opens org.project.contactapp.entities to org.assertj.core; // Open for AssertJ reflection

    // Exports
    exports org.project.contactapp;
    exports org.project.contactapp.controllers;
    exports org.project.contactapp.entities; // Export entities if needed
}
