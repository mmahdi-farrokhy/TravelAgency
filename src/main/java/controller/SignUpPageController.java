package controller;

import commonStructures.City;
import exceptions.NoFlightSelectedException;
import exceptions.NoSuchFXMLFileExistingException;
import exceptions.NoUserLoggedInException;
import exceptions.UnexpectedException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import model.Customer;
import model.submodel.Address;
import model.submodel.FullName;

import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;

import static data.dao.factory.CustomerDAOFactory.createCustomerDAO;
import static main.Main.loggedInCustomer;
import static utilities.ButtonActionInitializer.setOnActionMethods;
import static utilities.GUIUtils.*;

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
        setOnActionMethods(signUpBtn, 8, this::signUpUser);
        accountCheckText.setOnMouseClicked(e -> openLoginPage());
    }

    private void openLoginPage() {
        try {
            closeCurrentPage(signUpBtn);
            openPage(this, "..//LoginPage.fxml");
        } catch (NoUserLoggedInException ex) {
            showMessageBox("Attention", ex.getMessage(), "Please login or sign up first", Alert.AlertType.ERROR);
        } catch (NoFlightSelectedException ex) {
            showMessageBox("Attention", ex.getMessage(), "Please select a flight from the list", Alert.AlertType.ERROR);
        } catch (NoSuchFXMLFileExistingException ex) {
            showMessageBox("Error", ex.getMessage(), "Could not load fxml file! \nPlease make sure the file name is correct.", Alert.AlertType.ERROR);
        } catch (UnexpectedException ex) {
            showMessageBox("Error", ex.getMessage(), "Something unexpected happened while trying to open the fxml file.", Alert.AlertType.ERROR);
        }
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
        if (areNecessaryFieldsFilled()) {
            if (isPasswordConfirmed(passwordField, passwordConfirmationField)) {
                errorText.setVisible(false);
                loggedInCustomer = getSignedUpCustomer();
                saveUserInDatabase(loggedInCustomer);
                closeCurrentPage(signUpBtn);
                showMessageBox("Sign Up", "Done!", "User created successfully", Alert.AlertType.INFORMATION);
                fillUserInformation(getSignedUpCustomer());
            } else {
                showError("Please confirm the password!");
            }
        } else {
            showError("Please fill in all necessary fields!");
        }
    }

    private void showError(String errorText) {
        this.errorText.setText(errorText);
        this.errorText.setVisible(true);
    }

    private static void saveUserInDatabase(Customer signedUpCustomer) {
        try {
            createCustomerDAO().insertNewRecord(signedUpCustomer);
        } catch (RuntimeException exception){
            showMessageBox("Error", "Creating new user failed!", "An account is already registered with this national code!", Alert.AlertType.WARNING);
        }
    }

    private boolean areNecessaryFieldsFilled() {
        return fieldValueNotNullOrEmpty(nationalCodeField.getText()) &&
                fieldValueNotNullOrEmpty(firstNameField.getText()) &&
                fieldValueNotNullOrEmpty(lastNameField.getText()) &&
                fieldValueNotNullOrEmpty(cityComboBox.getValue()) &&
                fieldValueNotNullOrEmpty(phoneNumberField.getText()) &&
                fieldValueNotNullOrEmpty(passwordField.getText()) &&
                fieldValueNotNullOrEmpty(passwordConfirmationField.getText());
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
