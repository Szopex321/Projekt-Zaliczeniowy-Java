package com.project.controller.dialog;

import com.project.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class UserDialogController {

    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField peselField;
    @FXML private ChoiceBox<String> roleChoiceBox;
    @FXML private CheckBox activeCheckBox;
    @FXML private CheckBox adminCheckBox;

    // Driver Specific Fields
    @FXML private Label licenseNumberLabel;
    @FXML private TextField licenseNumberField;
    @FXML private Label licenseIssueDateLabel;
    @FXML private DatePicker licenseIssueDatePicker;
    @FXML private Label licenseExpiryDateLabel;
    @FXML private DatePicker licenseExpiryDatePicker;
    @FXML private Label licenseCategoriesLabel;
    @FXML private TextField licenseCategoriesField;

    private boolean isNewUser;

    public void setDialogData(boolean isNew) {
        this.isNewUser = isNew;
        if (isNew) {
            clearFields();
            activeCheckBox.setSelected(true);
            passwordField.setPromptText("Hasło jest wymagane dla nowego urzytkownika");
            if (!roleChoiceBox.getItems().isEmpty()) {
                roleChoiceBox.getSelectionModel().selectFirst(); // Domyślna rola
            }
        }
        updateDriverFieldsVisibility(roleChoiceBox.getValue());
    }

    @FXML
    private void initialize() {
        System.out.println("UserDialogController: initialize().");
        roleChoiceBox.setItems(FXCollections.observableArrayList("ADMIN", "KIEROWCA"));
        roleChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> updateDriverFieldsVisibility(newVal)
        );
        if (roleChoiceBox.getValue() == null && !roleChoiceBox.getItems().isEmpty()) {
            roleChoiceBox.getSelectionModel().selectFirst();
        } else {
            updateDriverFieldsVisibility(roleChoiceBox.getValue());
        }
    }

    private void updateDriverFieldsVisibility(String role) {
        boolean isDriver = "DRIVER (Demo)".equals(role);
        if (licenseNumberLabel != null) licenseNumberLabel.setVisible(isDriver);
        if (licenseNumberField != null) licenseNumberField.setVisible(isDriver);
        if (licenseIssueDateLabel != null) licenseIssueDateLabel.setVisible(isDriver);
        if (licenseIssueDatePicker != null) licenseIssueDatePicker.setVisible(isDriver);
        if (licenseExpiryDateLabel != null) licenseExpiryDateLabel.setVisible(isDriver);
        if (licenseExpiryDatePicker != null) licenseExpiryDatePicker.setVisible(isDriver);
        if (licenseCategoriesLabel != null) licenseCategoriesLabel.setVisible(isDriver);
        if (licenseCategoriesField != null) licenseCategoriesField.setVisible(isDriver);

        if (licenseNumberLabel != null) licenseNumberLabel.setManaged(isDriver);
        if (licenseNumberField != null) licenseNumberField.setManaged(isDriver);
    }

    private void clearFields() {
        loginField.clear();
        passwordField.clear();
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        peselField.clear();
        if (!roleChoiceBox.getItems().isEmpty()) roleChoiceBox.getSelectionModel().selectFirst();
        activeCheckBox.setSelected(true);
        adminCheckBox.setSelected(false);
        licenseNumberField.clear();
        licenseIssueDatePicker.setValue(null);
        licenseExpiryDatePicker.setValue(null);
        licenseCategoriesField.clear();
    }

    public boolean processSave() {
        if (loginField.getText().trim().isEmpty()) {
            AlertUtil.showWarning("Zapisywanie", "Login nie może być pusty");
            return false;
        }
        if (isNewUser && passwordField.getText().trim().isEmpty()){
            AlertUtil.showWarning("Zapisywanie", "Hasło nie może być pusty");
            return false;
        }

        System.out.println("Save. Login: " + loginField.getText());
        AlertUtil.showInfo("Zapisz", "Dane urzytkownika Będą " + (isNewUser ? "Zapisano" : "Zaktualizowano"));
        return true;
    }
}