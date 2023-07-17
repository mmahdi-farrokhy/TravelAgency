package controller;

import common.structures.City;
import data.layer.factories.CustomerDAOFactory;
import exceptions.PasswordNotConfirmedException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import main.Main;
import model.Customer;
import model.submodel.Address;
import model.submodel.FullName;
import utilities.ButtonActionInitializer;

import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;

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

        ButtonActionInitializer buttonActionInitializer = new ButtonActionInitializer();
        buttonActionInitializer.setOnActionMethods(saveBtn, 8, () -> {
            try {
                saveCustomerChanges();
            } catch (PasswordNotConfirmedException ex) {
                showMessageBox("Attention", "Password is not confirmed!", "Please confirm your password correctly", Alert.AlertType.WARNING);
            }
        });
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

    private void saveCustomerChanges() throws PasswordNotConfirmedException {
        if (passwordConfirmed(passwordField, passwordConfirmationField)) {
            Customer newCustomer = new Customer();
            newCustomer.setNationalCode(nationalCodeField.getText());
            newCustomer.setFullName(new FullName(firstNameField.getText(), lastNameField.getText()));
            newCustomer.setBirthDate(birthDatePicker.getValue());
            newCustomer.setAddress(new Address(City.getValue(cityCombo.getValue()), streetField.getText(), postalCodeField.getText()));
            newCustomer.setPhoneNumber(phoneNumberField.getText());
            newCustomer.setEmail(emailField.getText());
            newCustomer.setPassword(passwordField.getText());
            CustomerDAOFactory.createCustomerDAO().updateRecord(newCustomer);
            closePageAfterOperation(saveBtn);
            showMessageBox("Done", "User information updated!", "Your information is updated successfully.", Alert.AlertType.INFORMATION);
        } else
            throw new PasswordNotConfirmedException("Password is not confirmed!");
    }
}
