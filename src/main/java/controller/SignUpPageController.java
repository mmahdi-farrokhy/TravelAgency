package controller;

import commonStructures.City;
import dataLayer.CustomerTable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import model.Customer;
import model.submodel.Address;
import model.submodel.FullName;
import utilities.GUIUtils;

import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;

public class SignUpPageController implements Initializable {
    @FXML
    private TextField nationalCodeField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private ComboBox<String> cityComboBox;

    @FXML
    private TextField postalCodeField;

    @FXML
    private TextField streetField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordConfirmationField;

    @FXML
    private Button signUpBtn;

    @FXML
    private Text errorText;

    @FXML
    private TextField accountCheckText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCityComboBox();
        errorText.setVisible(false);
        signUpBtn.setOnAction(e -> signUpUser());
        accountCheckText.setOnMouseClicked(e -> GUIUtils.openPage(this, "..//LoginPage.fxml"));
    }

    private void initCityComboBox() {
        City[] sortedCities = City.values();
        Arrays.sort(sortedCities, Comparator.comparing(Enum::name));
        cityComboBox.getItems().clear();
        cityComboBox.getItems().add("");
        for (City city : sortedCities)
            if (city != City.NONE)
                cityComboBox.getItems().add(city.toString());
    }

    private void signUpUser() {
        if (necessaryFieldsAreFilled()) {
            if (passwordConfirmed()) {
                errorText.setVisible(false);
                Customer signedUpCustomer = getSignedUpCustomer();
                if (!saveUserInDatabase(signedUpCustomer)) return;
                GUIUtils.UserRegistryPage(signUpBtn);
                GUIUtils.showMessageBox("Sign Up", "Done!", "User created successfully", Alert.AlertType.INFORMATION);
                GUIUtils.fillUserInformation(signedUpCustomer);
            } else {
                errorText.setText("Please confirm the password!");
                errorText.setVisible(true);
            }
        } else {
            errorText.setText("Please fill in all necessary fields!");
            errorText.setVisible(true);
        }
    }

    private static boolean saveUserInDatabase(Customer signedUpCustomer) {
        try {
            CustomerTable customerTable = new CustomerTable();
            customerTable.insertNewRecord(signedUpCustomer);
            return true;
        } catch (RuntimeException ex) {
            GUIUtils.showMessageBox("Error", "Creating new user failed!", "An account is already registered with this national code!", Alert.AlertType.WARNING);
            return false;
        }
    }

    private boolean necessaryFieldsAreFilled() {
        return fieldValueNotNullOrEmpty(nationalCodeField.getText()) &&
                fieldValueNotNullOrEmpty(firstNameField.getText()) &&
                fieldValueNotNullOrEmpty(lastNameField.getText()) &&
                fieldValueNotNullOrEmpty(cityComboBox.getValue()) &&
                fieldValueNotNullOrEmpty(phoneNumberField.getText()) &&
                fieldValueNotNullOrEmpty(passwordField.getText()) &&
                fieldValueNotNullOrEmpty(passwordConfirmationField.getText());
    }

    private boolean passwordConfirmed() {
        return fieldValueNotNullOrEmpty(passwordField.getText()) &&
                passwordField.getText().equals(passwordConfirmationField.getText());
    }

    private boolean fieldValueNotNullOrEmpty(String fieldValue) {
        return fieldValue != null && fieldValue.trim() != "";
    }

    private Customer getSignedUpCustomer() {
        Customer customer = new Customer();
        customer.setNationalCode(nationalCodeField.getText().trim());
        customer.setFullName(new FullName(firstNameField.getText().trim(), lastNameField.getText().trim()));
        customer.setBirthDate(birthDatePicker.getValue());
        customer.setAddress(new Address(City.getValue(cityComboBox.getValue().trim()), streetField.getText().trim(), postalCodeField.getText().trim()));
        customer.setPhoneNumber(phoneNumberField.getText().trim());
        customer.setEmail(emailField.getText().trim());
        customer.setPassword(passwordField.getText());
        return customer;
    }
}
