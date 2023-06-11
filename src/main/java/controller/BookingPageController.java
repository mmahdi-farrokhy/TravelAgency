package controller;

import commonStructures.CurrencyType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.Main;

import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;

public class BookingPageController implements Initializable {
    @FXML
    private TextField fullNameField;

    @FXML
    private TextField nationalCodeField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField originAirportField;

    @FXML
    private TextField destinationAirportField;

    @FXML
    private TextField flightDateTimeField;

    @FXML
    private TextField flightIdField;

    @FXML
    private ComboBox<Integer> numberOfTicketsCombo;

    @FXML
    private TextField priceField;

    @FXML
    private TextField flightDistanceField;

    @FXML
    private Button backBtn;

    @FXML
    private ComboBox<String> currencyCombo;

    @FXML
    private Button orderRegisterBtn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initNumberOfTicketsCombo();
        initCurrencyCombo();
        fillPersonalInformation();
    }

    private void initNumberOfTicketsCombo() {
        for (int numberOfTickets = 1; numberOfTickets <= 10; numberOfTickets++)
            numberOfTicketsCombo.getItems().add(numberOfTickets);
    }

    private void initCurrencyCombo() {
        CurrencyType[] sortedCurrencies = CurrencyType.values();
        Arrays.sort(sortedCurrencies, Comparator.comparing(Enum::name));
        currencyCombo.getItems().clear();
        currencyCombo.getItems().add("");
        for (CurrencyType currency : sortedCurrencies)
            if (currency != CurrencyType.NONE)
                currencyCombo.getItems().add(currency.toString() + " " + currency.getCurrencySymbol(currency));

        currencyCombo.setValue(CurrencyType.USD + " $");
    }

    private void fillPersonalInformation() {
        fullNameField.setText(Main.loggedInCustomer.getFullName().getRawFullName());
        nationalCodeField.setText(Main.loggedInCustomer.getNationalCode());
        addressField.setText(Main.loggedInCustomer.getAddress().getRawAddress());
        phoneNumberField.setText(Main.loggedInCustomer.getPhoneNumber());
    }
}
