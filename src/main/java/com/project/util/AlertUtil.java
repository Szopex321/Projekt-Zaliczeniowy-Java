package com.project.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Window;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;


public class AlertUtil {

    public static void showError(String title, String content) {
        showAndWaitAlert(null, Alert.AlertType.ERROR, title, null, content);
    }

    public static void showError(Window owner, String title, String header, String content) {
        showAndWaitAlert(owner, Alert.AlertType.ERROR, title, header, content);
    }

    public static void showWarning(String title, String content) {
        showAndWaitAlert(null, Alert.AlertType.WARNING, title, null, content);
    }

    public static void showWarning(Window owner, String title, String header, String content) {
        showAndWaitAlert(owner, Alert.AlertType.WARNING, title, header, content);
    }

    public static void showInfo(String title, String content) {
        showAndWaitAlert(null, Alert.AlertType.INFORMATION, title, null, content);
    }

    public static void showInfo(Window owner, String title, String header, String content) {
        showAndWaitAlert(owner, Alert.AlertType.INFORMATION, title, header, content);
    }

    public static Optional<ButtonType> showConfirmation(String title, String header, String content) {
        return showAndWaitConfirmation(null, title, header, content);
    }

    public static Optional<ButtonType> showConfirmation(Window owner, String title, String header, String content) {
        return showAndWaitConfirmation(owner, title, header, content);
    }

    public static void showException(String title, String content, Throwable exception) {
        showException(null, title, content, exception);
    }

    public static void showException(Window owner, String title, String content, Throwable exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (owner != null) {
            alert.initOwner(owner);
            alert.initModality(Modality.WINDOW_MODAL);
        }
        alert.setTitle(title);
        alert.setHeaderText("An unexpected error occurred.");
        alert.setContentText(content);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);
        alert.getDialogPane().setExpanded(false);

        alert.showAndWait();
    }

    private static void showAndWaitAlert(Window owner, Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        if (owner != null) {
            alert.initOwner(owner);
            alert.initModality(Modality.WINDOW_MODAL);
        }
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private static Optional<ButtonType> showAndWaitConfirmation(Window owner, String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (owner != null) {
            alert.initOwner(owner);
            alert.initModality(Modality.WINDOW_MODAL);
        }
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }
}