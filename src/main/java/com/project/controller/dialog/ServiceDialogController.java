package com.project.controller.dialog;

import com.project.util.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ServiceDialogController {

    @FXML private DatePicker serviceDatePicker;
    @FXML private TextArea descriptionArea;
    @FXML private TextField costField;
    @FXML private TextField mileageField;
    @FXML private TextField workshopNameField;

    private boolean isNewService;


    @FXML
    private void initialize() {
        System.out.println("ServiceDialogController: initialize() called.");
    }

    private void clearFields() {
        serviceDatePicker.setValue(LocalDate.now());
        descriptionArea.clear();
        costField.clear();
        mileageField.clear();
        workshopNameField.clear();
    }

    public boolean processSave() {
        if (serviceDatePicker.getValue() == null) {
            AlertUtil.showWarning("Sprawdzanie", "Data wymagana");
            return false;
        }
        if (descriptionArea.getText().trim().isEmpty()) {
            AlertUtil.showWarning("Sprawdzanie", "Opis nie może być pusty");
            return false;
        }
        try {
            if (!costField.getText().trim().isEmpty()) Double.parseDouble(costField.getText().replace(",", "."));
            if (!mileageField.getText().trim().isEmpty()) Integer.parseInt(mileageField.getText());
        } catch (NumberFormatException e) {
            AlertUtil.showWarning("Sprawdzanie", "cena i przebiek muszą być cyframi");
            return false;
        }

        System.out.println("Save. Date: " + serviceDatePicker.getValue());
        AlertUtil.showInfo("Zapisano", "Wprowadzono serwis " + (isNewService ? "Zapisano" : "Zakupiono"));
        return true;
    }
}