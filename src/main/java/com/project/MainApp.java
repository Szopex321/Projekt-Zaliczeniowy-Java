package com.project;

import com.project.util.AlertUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(MainApp.class.getResource("/com/project/MainView.fxml")));
            Parent root = loader.load();

            Scene scene = new Scene(root, 1024, 768);
            primaryStage.setTitle("System Zarządzania Flotą Pojazdów");
            primaryStage.setScene(scene);
            primaryStage.setMinHeight(700);
            primaryStage.setMinWidth(950);
            primaryStage.show();

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            String message = (e instanceof NullPointerException) ? "Nie znaleziono pliku FXML" : "Nie mozna zaladowac gui";
            AlertUtil.showError("Application Error", message + "\nError: " + e.getMessage());
            System.exit(1);
        }
        catch (Throwable t) {
            t.printStackTrace();
            AlertUtil.showError("Critical Application Error", "unexpected error" + t.getMessage());
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}