package com.project.controller;

import com.project.controller.dialog.UserDialogController;
import com.project.util.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class UsersViewController {

    @FXML private TableView<Object> userTable;
    @FXML private TextField searchUserField;
    @FXML private Button addUserButton;
    @FXML private Button editUserButton;
    @FXML private Button deleteUserButton;
    @FXML private Button refreshUsersButton;

    @FXML
    public void initialize() {
        System.out.println("UsersViewController: initialize().");
        if (userTable != null) userTable.setPlaceholder(new Label("Brak Urzytkownikow"));
        if (editUserButton != null) editUserButton.setDisable(true);
        if (deleteUserButton != null) deleteUserButton.setDisable(true);

        // Listener dla wyboru użytkownika
        if (userTable != null) {
            userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                boolean userSelected = (newSelection != null);
                if (editUserButton != null) editUserButton.setDisable(!userSelected);
                if (deleteUserButton != null) deleteUserButton.setDisable(!userSelected);
            });
        }
    }

    @FXML
    private void handleAddUser(ActionEvent event) {
        System.out.println("UsersViewController: handleAddUser");
        showUserDialog(true, null);
    }

    @FXML
    private void handleEditUser(ActionEvent event) {
        System.out.println("UsersViewController: handleEditUser");
        AlertUtil.showInfo("Action", "Edycja wymaga wybrania Urzytkownika");
    }

    @FXML
    private void handleDeleteUser(ActionEvent event) {
        System.out.println("UsersViewController: handleDeleteUser called");
        AlertUtil.showConfirmation("Potwierdz usunięcie", "Usun urzytkownika", "Napewno chcesz usunąć?").filter(response -> response == ButtonType.OK || response == ButtonType.YES).ifPresent(response -> AlertUtil.showInfo("Action", "Usunięto"));
    }

    @FXML
    private void handleRefreshUsers(ActionEvent event) {
        System.out.println("UsersViewController: handleRefreshUsers");
        if (userTable != null) userTable.getItems().clear();
    }

    private void showUserDialog(boolean isNew, Object userData) {
        String title = isNew ? "Add New User" : "Edit User";
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/project/Dialog/UserDialog.fxml")));
            DialogPane dialogPane = loader.load();

            UserDialogController controller = loader.getController();
            controller.setDialogData(isNew);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle(title + " (Demo)");
            dialog.initModality(Modality.WINDOW_MODAL);
            if (userTable != null && userTable.getScene() != null && userTable.getScene().getWindow() != null) {
                dialog.initOwner(userTable.getScene().getWindow());
            }

            // Konfiguracja akcji przycisku zapisu
            ButtonType saveButtonType = dialogPane.getButtonTypes().stream().filter(bt -> bt.getButtonData() == ButtonBar.ButtonData.OK_DONE).findFirst().orElse(null);

            if (saveButtonType != null) {
                Node saveButtonNode = dialog.getDialogPane().lookupButton(saveButtonType);
                if (saveButtonNode instanceof Button) {
                    ((Button) saveButtonNode).addEventFilter(ActionEvent.ACTION, e -> {
                        if (!controller.processSave()) { // processSave() zwraca false, jeśli walidacja nie przeszła
                            e.consume();
                        }
                    });
                }
            }

            dialog.showAndWait();
            System.out.println("UsersViewController: User dialog closed.");

        } catch (IOException | NullPointerException e) {
            System.err.println("Error opening UserDialog: " + e.getMessage());
            e.printStackTrace();
            AlertUtil.showError("Application Error", "Could not open the user dialog: " + e.getMessage());
        }
    }
}