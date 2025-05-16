package com.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            // Użyj MainApp.class.getResource() dla większej pewności,
            // lub getClass().getResource() jeśli jesteś w niestatycznej metodzie MainApp.
            // "MainView.fxml" - szuka pliku w tym samym pakiecie co MainApp.class.
            // Jeśli FXML jest w src/main/resources/com/project/MainView.fxml
            // to ta ścieżka jest poprawna.
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                    MainApp.class.getResource("MainView.fxml"),
                    "Nie można znaleźć pliku FXML: MainView.fxml. Sprawdź ścieżkę i lokalizację."
            ));
            // Alternatywnie, jeśli FXML jest w np. src/main/resources/fxml/MainView.fxml
            // FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
            //        MainApp.class.getResource("/fxml/MainView.fxml"),
            //        "Nie można znaleźć pliku FXML: /fxml/MainView.fxml. Sprawdź ścieżkę i lokalizację."
            // ));


            Parent root = loader.load(); // To jest prawdopodobnie Twoja linia 15

            stage.setTitle("JavaFX Menu z FXML");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.err.println("Błąd wejścia/wyjścia podczas ładowania FXML:");
            e.printStackTrace();
        } catch (NullPointerException e) { // Ten catch jest ważny dzięki Objects.requireNonNull
            System.err.println("Błąd: Nie znaleziono pliku FXML (NullPointerException).");
            System.err.println(e.getMessage()); // Wyświetli komunikat z Objects.requireNonNull
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Wystąpił nieoczekiwany błąd podczas uruchamiania aplikacji:");
            e.printStackTrace();
        }



    }

    public static void main(String[] args) {
        launch();
    }
}