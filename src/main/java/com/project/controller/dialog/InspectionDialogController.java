package com.project.controller.dialog;

import com.project.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class InspectionDialogController {

    @FXML private DatePicker inspectionDatePicker;
    @FXML private DatePicker validUntilDatePicker;
    @FXML private ComboBox<String> resultComboBox;
    @FXML private TextField stationField;
    @FXML private TextField diagnosticianNumberField;
    @FXML private TextArea notesArea;

    private boolean isNewInspection;

    public void setDialogData(boolean isNew) {
        this.isNewInspection = isNew;
        if (isNew) {
            clearFields();
            inspectionDatePicker.setValue(LocalDate.now());
            if (!resultComboBox.getItems().isEmpty()) {
                resultComboBox.getSelectionModel().selectFirst();
            }
        }
    }

    @FXML
    private void initialize() {
        System.out.println("InspectionDialogController: initialize()");
        resultComboBox.setItems(FXCollections.observableArrayList("Pozytywny", "Negatywny"));
        if (resultComboBox.getValue() == null && !resultComboBox.getItems().isEmpty()) {
            resultComboBox.getSelectionModel().selectFirst();
        }
    }

    private void clearFields() {
        inspectionDatePicker.setValue(LocalDate.now());
        validUntilDatePicker.setValue(null);
        if (!resultComboBox.getItems().isEmpty()) resultComboBox.getSelectionModel().selectFirst();
        stationField.clear();
        diagnosticianNumberField.clear();
        notesArea.clear();
    }

    public boolean processSave() {
        if (inspectionDatePicker.getValue() == null) {
            AlertUtil.showWarning("Sprawdzanie", "Data przeglądu wymagana");
            return false;
        }
        if (validUntilDatePicker.getValue() == null) {
            AlertUtil.showWarning("Sprawdzanie", "Data wygaśnięcia wymagana");
            return false;
        }
        if (resultComboBox.getValue() == null) {
            AlertUtil.showWarning("Sprawdzanie", "Wynik Przeglądu wymagany");
            return false;
        }

        System.out.println("Save. Date: " + inspectionDatePicker.getValue());
        AlertUtil.showInfo("Zapisano", "Dane przeglądu " + (isNewInspection ? "Zapisano" : "zaktualizowano"));
        return true;
    }
}