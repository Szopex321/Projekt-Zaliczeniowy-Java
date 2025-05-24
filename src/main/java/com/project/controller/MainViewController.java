package com.project.controller;

import com.project.util.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainViewController {
    @FXML private Label currentUserLabel;
    @FXML private TabPane mainTabPane;
    @FXML private Tab vehicleTab;
    @FXML private Tab usersTab;

    @FXML
    public void initialize() {
        System.out.println("MainViewController: initialize()");
        if (currentUserLabel != null) {
            currentUserLabel.setText("Urzytkownik: Gość");
        }

        // Ładowanie zawartości zakładek
        loadTabContent(vehicleTab, "/com/project/VehicleView.fxml");
        loadTabContent(usersTab, "/com/project/UsersView.fxml");

        if (mainTabPane != null && vehicleTab != null) {
            mainTabPane.getSelectionModel().select(vehicleTab); // Domyślnie wybrana zakładka
        } else {
            System.err.println("MainViewController: mainTabPane or vehicleTab is null! Check fx:id in MainView.fxml.");
        }
    }

    private void loadTabContent(Tab tab, String fxmlPath) {
        if (tab == null) {
            System.err.println("MainViewController: Attempt to load FXML " + fxmlPath + " into a null Tab. Check fx:id for the Tab.");
            return;
        }
        try {
            Node content = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath), "Cannot find FXML: " + fxmlPath));
            tab.setContent(content);
        } catch (IOException | NullPointerException e) {
            System.err.println("MainViewController: Error loading FXML content for tab: " + fxmlPath);
            e.printStackTrace();
            tab.setContent(new Label("Error loading: " + fxmlPath + "\n" + e.getMessage()));
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        System.out.println("MainViewController: Logout action triggered");
        AlertUtil.showInfo("Wyloguj", "Wylogowano");

        try {
            Node sourceNode = (Node) event.getSource();
            Stage currentStage = (Stage) sourceNode.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/project/LoginView.fxml")));
            Parent loginRoot = loader.load();
            Scene loginScene = new Scene(loginRoot);

            currentStage.setTitle("Logowanie do systemu");
            currentStage.setScene(loginScene);
            currentStage.setResizable(false);

        } catch (IOException | NullPointerException e) {
            System.err.println("MainViewController: Error loading LoginView after logout.");
            e.printStackTrace();
            AlertUtil.showError("Application Error", "Could not load the login screen: " + e.getMessage());
        }
    }
}