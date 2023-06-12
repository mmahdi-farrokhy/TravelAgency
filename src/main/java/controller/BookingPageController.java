package controller;

import buisnessLayer.CurrencyConverter;
import commonStructures.CurrencyType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.Main;
import model.Order;
import model.submodel.Price;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;

import static buisnessLayer.CurrencyConverter.convertCurrency;
import static commonStructures.CurrencyType.valueOf;
import static java.lang.Double.parseDouble;

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

    private Order currentOrder = new Order();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initNumberOfTicketsCombo();
        initCurrencyCombo();
        fillPersonalInformation();
        fillFlightInformation();

        numberOfTicketsCombo.setOnAction(e -> calculatePrice());
        orderRegisterBtn.setOnAction(e -> registerOrder());
    }

    private void initCurrentOrder() {
        currentOrder = new Order();
        currentOrder.setFlight(Main.selectedFlight);
        currentOrder.setCustomerInfo(Main.loggedInCustomer);
        currentOrder.setQuantity(numberOfTicketsCombo.getValue());
        currentOrder.setRegistrationTime(LocalDateTime.now());
        currentOrder.setPrice(new Price(parseDouble(priceField.getText()) , CurrencyType.BHD));
    }

    private void initNumberOfTicketsCombo() {
        for (int numberOfTickets = 1; numberOfTickets <= 10; numberOfTickets++)
            numberOfTicketsCombo.getItems().add(numberOfTickets);

        numberOfTicketsCombo.setValue(0);
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

    private void fillFlightInformation() {
        originAirportField.setText(Main.selectedFlight.getOriginAirport().getCode() + ": " + Main.selectedFlight.getOriginAirport().getName());
        destinationAirportField.setText(Main.selectedFlight.getDestinationAirport().getCode() + ": " + Main.selectedFlight.getDestinationAirport().getName());
        flightDateTimeField.setText(Main.selectedFlight.getDepartureTime().toString().replaceAll("-", "/").replace("T", " "));
        flightIdField.setText(Main.selectedFlight.getId());
        flightDistanceField.setText(Double.toString(Main.selectedFlight.estimateFlightDistance()));
    }

    private void calculatePrice() {
        currentOrder.setQuantity(numberOfTicketsCombo.getValue());
        currentOrder.setFlight(Main.selectedFlight);
        currentOrder.calculateOrderPriceAmountByUSD();
        CurrencyType selectedCurrency = valueOf(currencyCombo.getValue().substring(0, 3));
        double convertedPrice = convertCurrency(CurrencyType.USD, selectedCurrency, currentOrder.getPrice().getAmount());
        priceField.setText(String.valueOf(convertedPrice));
    }

    private void registerOrder() {
        initCurrentOrder();
    }
}
