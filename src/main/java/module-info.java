module com.project {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.project.controller to javafx.fxml;


    opens com.project to javafx.fxml;
    exports com.project;
}