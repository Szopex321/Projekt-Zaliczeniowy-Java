package com.project.controller;

import com.project.controller.dialog.VehicleDialogController;
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

public class VehicleViewController {
    @FXML private TableView<Object> vehicleTable;
    @FXML private TextField searchVehicleField;
    @FXML private Button editVehicleButton;
    @FXML private Button deleteVehicleButton;

    // Pola FXML dla zakładki "Drivers"
    @FXML private ListView<Object> assignedDriversList;
    @FXML private Button assignDriverButton;
    @FXML private Button unassignDriverButton;

    // Pola FXML dla zakładki "Services"
    @FXML private TableView<Object> serviceHistoryTable;
    @FXML private Button addServiceButton;
    @FXML private Button editServiceButton;
    @FXML private Button deleteServiceButton;

    // Pola FXML dla zakładki "Inspections"
    @FXML private TableView<Object> inspectionHistoryTable;
    @FXML private Button addInspectionButton;
    @FXML private Button editInspectionButton;
    @FXML private Button deleteInspectionButton;


    @FXML
    public void initialize() {
        System.out.println("VehicleViewController: initialize()");
        if (vehicleTable != null) vehicleTable.setPlaceholder(new Label("Brak Pojazdów"));
        if (assignedDriversList != null) assignedDriversList.setPlaceholder(new Label("wybierz pojazd"));
        if (serviceHistoryTable != null) serviceHistoryTable.setPlaceholder(new Label("wybierz pojazd"));
        if (inspectionHistoryTable != null) inspectionHistoryTable.setPlaceholder(new Label("wybierz pojazd"));

        // Domyślne stany przycisków
        setButtonDisabledState(true, editVehicleButton, deleteVehicleButton, assignDriverButton, unassignDriverButton, addServiceButton, editServiceButton, deleteServiceButton, addInspectionButton, editInspectionButton, deleteInspectionButton);

        // Listener dla wyboru pojazdu
        if (vehicleTable != null) {
            vehicleTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                boolean vehicleSelected = (newSelection != null);
                setButtonDisabledState(!vehicleSelected, editVehicleButton, deleteVehicleButton, assignDriverButton,
                        addServiceButton, addInspectionButton);
                if (!vehicleSelected) { // czysci jesli nic nie wybrane
                    if (assignedDriversList != null) assignedDriversList.getItems().clear();
                    if (serviceHistoryTable != null) serviceHistoryTable.getItems().clear();
                    if (inspectionHistoryTable != null) inspectionHistoryTable.getItems().clear();
                    setButtonDisabledState(true, unassignDriverButton, editServiceButton, deleteServiceButton,
                            editInspectionButton, deleteInspectionButton);
                } else {
                    System.out.println("wybierz pojazd");
                }
            });
        }
        // Listenery dla list
        if (assignedDriversList != null && unassignDriverButton != null) {
            assignedDriversList.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> unassignDriverButton.setDisable(n == null));
        }
        if (serviceHistoryTable != null) {
            serviceHistoryTable.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
                setButtonDisabledState(n == null, editServiceButton, deleteServiceButton);
            });
        }
        if (inspectionHistoryTable != null) {
            inspectionHistoryTable.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
                setButtonDisabledState(n == null, editInspectionButton, deleteInspectionButton);
            });
        }
    }

    private void setButtonDisabledState(boolean disabled, Button... buttons) {
        for (Button button : buttons) {
            if (button != null) {
                button.setDisable(disabled);
            }
        }
    }

    @FXML
    private void handleAddVehicle(ActionEvent event) {
        System.out.println("VehicleViewController: handleAddVehicle");
        showVehicleDialog(true, null);
    }

    @FXML
    private void handleEditVehicle(ActionEvent event) {
        System.out.println("VehicleViewController: handleEditVehicle");
        AlertUtil.showInfo("Action", "Edycja wymaga wybranego pojazdu.");
    }

    @FXML
    private void handleDeleteVehicle(ActionEvent event) {
        System.out.println("VehicleViewController: handleDeleteVehicle");
        AlertUtil.showConfirmation("Potwwierdz", "Usun pojazd", "napewno usunąć pojazd?").filter(response -> response == ButtonType.OK || response == ButtonType.YES).ifPresent(response -> AlertUtil.showInfo("Action", "ok"));
    }

    @FXML
    private void handleRefreshVehicles(ActionEvent event) {
        System.out.println("VehicleViewController: handleRefreshVehicles");
        if (vehicleTable != null) vehicleTable.getItems().clear();

    }

    @FXML
    private void handleAssignDriver(ActionEvent event) {
        System.out.println("VehicleViewController: handleAssignDriver");
        AlertUtil.showInfo("Action", "ok");

    }

    @FXML
    private void handleUnassignDriver(ActionEvent event) {
        System.out.println("VehicleViewController: handleUnassignDriver");
        AlertUtil.showInfo("Action", "ok");
    }

    @FXML
    private void handleAddService(ActionEvent event) {
        System.out.println("VehicleViewController: handleAddService");
        showServiceDialog(true, null);
    }

    @FXML
    private void handleEditService(ActionEvent event) {
        System.out.println("VehicleViewController: handleEditService");
        AlertUtil.showInfo("Action", "Edycja wymaga wybranego pojazdu.");
    }

    @FXML
    private void handleDeleteService(ActionEvent event) {
        System.out.println("VehicleViewController: handleDeleteService");
        AlertUtil.showInfo("Action", "Serwis zostanie usunięty");
    }

    @FXML
    private void handleAddInspection(ActionEvent event) {
        System.out.println("VehicleViewController: handleAddInspection");
        showInspectionDialog(true, null);
    }

    @FXML
    private void handleEditInspection(ActionEvent event) {
        System.out.println("VehicleViewController: handleEditInspection called");
        AlertUtil.showInfo("Action", "Edycja wymaga wybranego przeglądu.");
    }

    @FXML
    private void handleDeleteInspection(ActionEvent event) {
        System.out.println("VehicleViewController: handleDeleteInspection called");
        AlertUtil.showInfo("Action", "Przegląd zostanie usunięty");
    }

    // --- Metody pomocnicze do otwierania dialogów ---

    private void showVehicleDialog(boolean isNew, Object vehicleData) {
        String title = isNew ? "Add New Vehicle" : "Edit Vehicle";
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/project/Dialog/VehicleDialog.fxml")));
            DialogPane dialogPane = loader.load();

            VehicleDialogController controller = loader.getController();
            controller.setDialogData(isNew /*, vehicleData */);

            Dialog<ButtonType> dialog = createDialog(dialogPane, title);
            configureSaveButtonAction(dialog, controller::processSave); // Przekazanie referencji do metody
            dialog.showAndWait();
        } catch (Exception e) {
            handleDialogLoadError("/com/project/Dialog/VehicleDialog.fxml", e);
        }
    }

    private void showServiceDialog(boolean isNew, Object serviceData) {
        String title = isNew ? "Add Service Entry" : "Edit Service Entry";
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/project/Dialog/ServiceDialog.fxml")));
            DialogPane dialogPane = loader.load();

            Dialog<ButtonType> dialog = createDialog(dialogPane, title);
            dialog.showAndWait();
        } catch (Exception e) {
            handleDialogLoadError("/com/project/Dialog/ServiceDialog.fxml", e);
        }
    }

    private void showInspectionDialog(boolean isNew, Object inspectionData) {
        String title = isNew ? "Add Technical Inspection" : "Edit Technical Inspection";
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/project/Dialog/InspectionDialog.fxml")));
            DialogPane dialogPane = loader.load();

            Dialog<ButtonType> dialog = createDialog(dialogPane, title);
            dialog.showAndWait();
        } catch (Exception e) {
            handleDialogLoadError("/com/project/Dialog/InspectionDialog.fxml", e);
        }
    }

    // Metoda pomocnicza do tworzenia standardowego dialogu
    private Dialog<ButtonType> createDialog(DialogPane dialogPane, String title) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        dialog.setTitle(title);
        dialog.initModality(Modality.WINDOW_MODAL);
        if (vehicleTable != null && vehicleTable.getScene() != null && vehicleTable.getScene().getWindow() != null) {
            dialog.initOwner(vehicleTable.getScene().getWindow());
        }
        return dialog;
    }

    // Metoda pomocnicza do konfigurowania akcji przycisku zapisu w dialogu
    private void configureSaveButtonAction(Dialog<ButtonType> dialog, java.util.function.Supplier<Boolean> saveProcess) {
        ButtonType saveButtonType = dialog.getDialogPane().getButtonTypes().stream().filter(bt -> bt.getButtonData() == ButtonBar.ButtonData.OK_DONE).findFirst().orElse(null);

        if (saveButtonType != null) {
            Node saveButtonNode = dialog.getDialogPane().lookupButton(saveButtonType);
            if (saveButtonNode instanceof Button) {
                ((Button) saveButtonNode).addEventFilter(ActionEvent.ACTION, event -> {
                    if (!saveProcess.get()) { // Jeśli proces zapisu zwróci false
                        event.consume();
                    }
                });
            }
        }
    }

    private void handleDialogLoadError(String fxmlPath, Exception e){
        System.err.println("Error opening dialog: " + fxmlPath);
        e.printStackTrace();
        AlertUtil.showError("Application Error", "Could not open dialog: " + fxmlPath + "\n" + e.getMessage());
    }
}