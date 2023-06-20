package controller;

import commonStructures.CurrencyType;
import dataLayer.OrderTable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.Main;
import model.Order;
import model.submodel.Price;
import utilities.GUIUtils;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;

import static buisnessLayer.CurrencyConverter.convertCurrency;
import static commonStructures.CurrencyType.valueOf;
import static java.lang.Double.parseDouble;
import static utilities.ConversionUtils.limitNumberOfDecimalPlaces;
import static utilities.GUIUtils.showMessageBox;

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

    @FXML
    private Button cancelBtn;

    private Order currentOrder;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        priceField.setText("0");
        initNumberOfTicketsCombo();
        initCurrencyCombo();
        fillPersonalInformation();
        fillFlightInformation();
        initCurrentOrder();

        numberOfTicketsCombo.setOnAction(e -> calculatePrice());
        currencyCombo.setOnAction(e -> calculatePrice());

        backBtn.setOnMouseEntered(e -> GUIUtils.setButtonStyle((Button) e.getSource(), 15));
        backBtn.setOnMouseExited(e -> GUIUtils.resetButtonStyle((Button) e.getSource(), 15));
        backBtn.setOnAction(e -> openFlightListPage());

        orderRegisterBtn.setOnMouseEntered(e -> GUIUtils.setButtonStyle((Button) e.getSource(), 15));
        orderRegisterBtn.setOnMouseExited(e -> GUIUtils.resetButtonStyle((Button) e.getSource(), 15));
        orderRegisterBtn.setOnAction(e -> registerOrder());

        cancelBtn.setOnMouseEntered(e -> GUIUtils.setButtonStyle((Button) e.getSource(), 8));
        cancelBtn.setOnMouseExited(e -> GUIUtils.resetButtonStyle((Button) e.getSource(), 8));
        cancelBtn.setOnAction(e -> closeBookingOrderPage());
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

    private void initCurrentOrder() {
        currentOrder = new Order();
        currentOrder.setQuantity(numberOfTicketsCombo.getValue());
        currentOrder.setFlight(Main.selectedFlight);
        currentOrder.setCustomerInfo(Main.loggedInCustomer);
        currentOrder.setQuantity(numberOfTicketsCombo.getValue());
        currentOrder.setRegistrationTime(LocalDateTime.now());
        currentOrder.setPrice(new Price(parseDouble(priceField.getText()), CurrencyType.BHD));
    }

    private void calculatePrice() {
        currentOrder.setQuantity(numberOfTicketsCombo.getValue());
        currentOrder.calculateOrderPriceAmountByUSD();
        CurrencyType selectedCurrency = valueOf(currencyCombo.getValue().substring(0, 3));
        double convertedPrice = convertCurrency(CurrencyType.USD, selectedCurrency, currentOrder.getPrice().getAmount());
        priceField.setText(String.valueOf(limitNumberOfDecimalPlaces(convertedPrice * currentOrder.getQuantity(), 2)));
    }

    private void openFlightListPage() {
        currentOrder = null;
        GUIUtils.openPage(this, "../FlightsListPage.fxml");
    }

    private void registerOrder() {
        if (Double.parseDouble(numberOfTicketsCombo.getValue().toString()) == 0) {
            showMessageBox("Wrong Number Of Tickets", "The number of tickets is 0", "You must select at least 1", Alert.AlertType.WARNING);
            return;
        }

        new OrderTable().insertNewRecord(currentOrder);
        showMessageBox("Done!", "Order Registered successfully", "For further information see order history on the main window", Alert.AlertType.INFORMATION);
    }

    private void closeBookingOrderPage() {
        currentOrder = null;
        Main.selectedFlight = null;
        backBtn.getScene().getWindow().hide();
    }
}
