module com.project {

    requires javafx.controls;
    requires javafx.fxml;

    opens com.project to javafx.fxml, javafx.graphics;
    opens com.project.controller to javafx.fxml;
    opens com.project.controller.dialog to javafx.fxml;

    exports com.project;
    exports com.project.util;
}