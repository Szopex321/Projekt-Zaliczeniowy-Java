package com.project.controller.dialog;

import com.project.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class VehicleDialogController {
    @FXML private ChoiceBox<String> vehicleTypeChoiceBox;
    @FXML private TextField makeField;
    @FXML private TextField modelField;
    @FXML private TextField yearField;
    @FXML private TextField regNumField;
    @FXML private Label doorsLabel, bodyTypeLabel, engineCapLabel, motorTypeLabel;
    @FXML private TextField doorsField, bodyTypeField, engineCapField, motorTypeField;

    private boolean isNew;

    public void setDialogData(boolean isNew) {
        this.isNew = isNew;
        if (isNew) {
            clearFields();
        }
        if (vehicleTypeChoiceBox.getItems().isEmpty()) {
            vehicleTypeChoiceBox.setItems(FXCollections.observableArrayList("Samochód", "Motocykl"));
        }
        if (!vehicleTypeChoiceBox.getItems().isEmpty()) {
            vehicleTypeChoiceBox.getSelectionModel().selectFirst();
        } else {
            updateSpecificFieldsVisibility(null);
        }
    }

    @FXML
    private void initialize() {
        if (vehicleTypeChoiceBox.getItems().isEmpty()) {
            vehicleTypeChoiceBox.setItems(FXCollections.observableArrayList("Samochód", "Motocykl"));
        }
        vehicleTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> updateSpecificFieldsVisibility(newVal)
        );
        if (!vehicleTypeChoiceBox.getItems().isEmpty()) {
            vehicleTypeChoiceBox.getSelectionModel().selectFirst();
        } else {
            updateSpecificFieldsVisibility(null);
        }
    }

    private void updateSpecificFieldsVisibility(String vehicleType) {
        boolean isCar = "Samochód".equals(vehicleType);
        boolean isMotorcycle = "Motocykl".equals(vehicleType);
        //pokazywanie/ukrywanie pól
        if(doorsLabel != null) doorsLabel.setVisible(isCar); if(doorsField != null) doorsField.setVisible(isCar);
        if(doorsLabel != null) doorsLabel.setManaged(isCar); if(doorsField != null) doorsField.setManaged(isCar);

    }

    private void clearFields() {
        makeField.clear(); modelField.clear(); yearField.clear(); regNumField.clear();
        doorsField.clear(); bodyTypeField.clear(); engineCapField.clear(); motorTypeField.clear();
    }

    // Ta metoda jest wywoływana przez kod, który otworzył dialog, gdy użytkownik kliknie "Save"
    public boolean processSave() {
        System.out.println("Save. Make: " + makeField.getText());
        AlertUtil.showInfo("Zapisz", "Zapisano");
        return true;
    }
}