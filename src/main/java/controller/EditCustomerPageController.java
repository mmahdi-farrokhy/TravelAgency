package controller;

import commonStructures.City;
import dataLayer.CustomerTable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;
import utilities.GUIUtils;

import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;

import static utilities.GUIUtils.showMessageBox;

public class EditCustomerPageController implements Initializable {

    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private ComboBox<String> cityCombo;

    @FXML
    private TextField emailField;

    @FXML
    private Text errorText;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField nationalCodeField;

    @FXML
    private PasswordField passwordConfirmationField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField streetField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Main.loggedInCustomer == null) {
            showMessageBox("Attention", "User not registered", "Please login or sign up first", Alert.AlertType.WARNING);
            ((Stage) saveBtn.getScene().getWindow()).close();
        }

        errorText.setVisible(false);
        initPersonalInformation();
        initAddress();
        initCityCombo();
        initContactInformation();

        saveBtn.setOnMouseEntered(e -> GUIUtils.setButtonStyle((Button) e.getSource(), 8));
        saveBtn.setOnMouseExited(e -> GUIUtils.resetButtonStyle((Button) e.getSource(), 8));
        saveBtn.setOnAction(e -> saveCustomerChanges());
    }

    private void initPersonalInformation() {
        nationalCodeField.setText(Main.loggedInCustomer.getNationalCode());
        firstNameField.setText(Main.loggedInCustomer.getFullName().getFirstName());
        lastNameField.setText(Main.loggedInCustomer.getFullName().getLastName());
        birthDatePicker.setValue(Main.loggedInCustomer.getBirthDate());
    }

    private void initAddress() {
        cityCombo.setValue(Main.loggedInCustomer.getAddress().getCityName().toString());
        postalCodeField.setText(Main.loggedInCustomer.getAddress().getPostalCode());
        streetField.setText(Main.loggedInCustomer.getAddress().getStreetName());
    }

    private void initCityCombo() {
        City[] sortedCities = City.values();
        Arrays.sort(sortedCities, Comparator.comparing(Enum::name));
        cityCombo.getItems().clear();
        cityCombo.getItems().add("");
        for (City city : sortedCities)
            if (city != City.NONE)
                cityCombo.getItems().add(city.toString());
    }
    private void initContactInformation() {
        phoneNumberField.setText(Main.loggedInCustomer.getPhoneNumber());
        emailField.setText(Main.loggedInCustomer.getEmail());
    }

    private void saveCustomerChanges() {
        if(passwordConfirmed())
            new CustomerTable().
    }

    private boolean passwordConfirmed() {
        return GUIUtils.fieldValueNotNullOrEmpty(passwordField.getText()) &&
                passwordField.getText().equals(passwordConfirmationField.getText());
    }
}
