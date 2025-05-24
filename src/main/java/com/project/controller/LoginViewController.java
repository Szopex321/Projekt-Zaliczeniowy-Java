package com.project.controller;

import com.project.util.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class LoginViewController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    @FXML
    private void handleLogin(ActionEvent event) {
        System.out.println("Login " + usernameField.getText());
        openMainWindow();
    }

    private void openMainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/project/MainView.fxml")));
            Parent root = loader.load();
            Stage mainStage = new Stage();
            mainStage.setTitle("Zarządzanie Flotą pojazdó");
            mainStage.setScene(new Scene(root, 1024, 768));
            mainStage.setMinHeight(700);
            mainStage.setMinWidth(950);

            Stage loginStage = (Stage) usernameField.getScene().getWindow();
            loginStage.close();
            mainStage.show();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            AlertUtil.showError("Application Error", "Could not load the main application window: " + e.getMessage());
        }
    }
}