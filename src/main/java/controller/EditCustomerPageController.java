package controller;

import commonStructures.City;
import data.factory.CustomerDAOFactory;
import exceptions.PasswordNotConfirmedException;
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

import static main.Main.loggedInCustomer;
import static utilities.ButtonActionInitializer.setOnActionMethods;
import static utilities.GUIUtils.*;

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
        errorText.setVisible(false);
        initPersonalInformation();
        initAddress();
        initCityCombo();
        initContactInformation();
        setOnActionMethods(saveBtn, 8, this::tryToSaveCustomerChanges);
    }

    private void initPersonalInformation() {
        nationalCodeField.setText(loggedInCustomer.getNationalCode());
        firstNameField.setText(loggedInCustomer.getCustomerFirstName());
        lastNameField.setText(loggedInCustomer.getCustomerLastName());
        birthDatePicker.setValue(loggedInCustomer.getBirthDate());
    }

    private void initAddress() {
        cityCombo.setValue(loggedInCustomer.getCustomerCityName().toString());
        postalCodeField.setText(loggedInCustomer.getCustomerPostalCode());
        streetField.setText(loggedInCustomer.getCustomerStreetName());
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
        phoneNumberField.setText(loggedInCustomer.getPhoneNumber());
        emailField.setText(loggedInCustomer.getEmail());
    }

    private void tryToSaveCustomerChanges() {
        try {
            saveCustomerChanges();
            showMessageBox("Done", "User information updated!", "Your information is updated successfully.", Alert.AlertType.INFORMATION);
        } catch (PasswordNotConfirmedException ex) {
            showMessageBox("Attention", "Password is not confirmed!", "Please confirm your password correctly", Alert.AlertType.WARNING);
        }
    }

    private void saveCustomerChanges() throws PasswordNotConfirmedException {
        if (isPasswordConfirmed(passwordField, passwordConfirmationField)) {
            Customer newCustomer = new Customer();
            newCustomer.setNationalCode(nationalCodeField.getText());
            newCustomer.setFullName(new FullName(firstNameField.getText(), lastNameField.getText()));
            newCustomer.setBirthDate(birthDatePicker.getValue());
            newCustomer.setAddress(new Address(City.getValue(cityCombo.getValue()), streetField.getText(), postalCodeField.getText()));
            newCustomer.setPhoneNumber(phoneNumberField.getText());
            newCustomer.setEmail(emailField.getText());
            newCustomer.setPassword(passwordField.getText());
            CustomerDAOFactory.createCustomerDAO().updateRecord(newCustomer);
            closeCurrentPage(saveBtn);
        } else
            throw new PasswordNotConfirmedException("Password is not confirmed!");
    }
}
