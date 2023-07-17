package controller;

import commonstructures.CurrencyType;
import datalayer.factories.OrderDAOFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.Main;
import model.Order;
import model.submodel.Price;
import utilities.ButtonActionInitializer;
import utilities.GUIUtils;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;

import static buisnesslayer.CurrencyConverter.convertCurrency;
import static commonstructures.CurrencyType.getCurrencySymbol;
import static commonstructures.CurrencyType.valueOf;
import static java.lang.Double.parseDouble;
import static utilities.ConversionUtils.limitNumberOfDecimalPlaces;
import static utilities.GUIUtils.*;

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

        numberOfTicketsCombo.setOnAction(e -> calculatePrice());
        currencyCombo.setOnAction(e -> calculatePrice());

        ButtonActionInitializer buttonActionInitializer = new ButtonActionInitializer();
        buttonActionInitializer.setOnActionMethods(backBtn, 15, this::openFlightListPage);
        buttonActionInitializer.setOnActionMethods(orderRegisterBtn, 15, this::registerOrder);
        buttonActionInitializer.setOnActionMethods(cancelBtn, 15, this::closeBookingOrderPage);
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
                currencyCombo.getItems().add(currency.toString() + " " + getCurrencySymbol(currency));

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
        currentOrder.setRegistrationTime(LocalDateTime.now());
        currentOrder.setPrice(new Price(parseDouble(priceField.getText()), valueOf(currencyCombo.getValue().substring(0, 3))));
    }

    private void calculatePrice() {
        initCurrentOrder();
        currentOrder.setQuantity(numberOfTicketsCombo.getValue());
        currentOrder.calculateOrderPriceAmountByUSD();
        CurrencyType selectedCurrency = valueOf(currencyCombo.getValue().substring(0, 3));
        double convertedPrice = convertCurrency(currentOrder.getPrice().getCurrency(), selectedCurrency, currentOrder.getPrice().getAmount());
        currentOrder.getPrice().setAmount(convertedPrice);
        priceField.setText(String.valueOf(limitNumberOfDecimalPlaces(convertedPrice * currentOrder.getQuantity(), 2)));
    }

    private void openFlightListPage() {
        currentOrder = null;
        openPage(this, "../FlightsListPage.fxml");
    }

    private void registerOrder() {
        if (Double.parseDouble(numberOfTicketsCombo.getValue().toString()) == 0) {
            showMessageBox("Invalid Number Of Tickets", "The number of tickets is 0", "You must select at least 1", Alert.AlertType.WARNING);
            return;
        }

        OrderDAOFactory.createCustomerDAO().insertNewRecord(currentOrder);
        showMessageBox("Done!", "Order Registered successfully", "For further information see order history on the main window", Alert.AlertType.INFORMATION);
        closePageAfterOperation(orderRegisterBtn);
    }

    private void closeBookingOrderPage() {
        currentOrder = null;
        Main.selectedFlight = null;
        GUIUtils.closePageAfterOperation(backBtn);
    }
}
