package controller;

import commonStructures.CurrencyType;
import data.factory.OrderDAOFactory;
import exceptions.NoFlightSelectedException;
import exceptions.NoSuchFXMLFileExistingException;
import exceptions.NoUserLoggedInException;
import exceptions.UnexpectedException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Order;
import model.submodel.Price;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

import static commonStructures.CurrencyType.*;
import static java.lang.Double.parseDouble;
import static main.Main.loggedInCustomer;
import static main.Main.selectedFlight;
import static utilities.ButtonActionInitializer.setOnActionMethods;
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
        setOnActionMethods(backBtn, 15, this::getBackToFlightListPage);
        setOnActionMethods(orderRegisterBtn, 15, this::registerOrder);
        setOnActionMethods(cancelBtn, 15, this::getBackToMainPage);
    }

    private void initNumberOfTicketsCombo() {
        IntStream.rangeClosed(1, 10).forEach(numberOfTicketsCombo.getItems()::add);
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

        currencyCombo.setValue(CurrencyType.USD + " " + dollarSign);
    }

    private void fillPersonalInformation() {
        nationalCodeField.setText(loggedInCustomer.getNationalCode());
        fullNameField.setText(loggedInCustomer.getRawFullName());
        addressField.setText(loggedInCustomer.getRawAddress());
        phoneNumberField.setText(loggedInCustomer.getPhoneNumber());
    }

    private void fillFlightInformation() {
        originAirportField.setText(selectedFlight.getFlightOriginAirportCode() + ": " + selectedFlight.getFlightOriginAirportName());
        destinationAirportField.setText(selectedFlight.getFlightDestinationAirportCode() + ": " + selectedFlight.getFlightDestinationAirportName());
        flightDateTimeField.setText(selectedFlight.getDepartureTime().toString().replaceAll("-", "/").replace("T", " "));
        flightIdField.setText(selectedFlight.getId());
        flightDistanceField.setText(Double.toString(selectedFlight.estimateFlightDistance()));
    }

    private void initCurrentOrder() {
        currentOrder = new Order();
        currentOrder.setQuantity(numberOfTicketsCombo.getValue());
        currentOrder.setFlight(selectedFlight);
        currentOrder.setCustomerInfo(loggedInCustomer);
        currentOrder.setRegistrationTime(LocalDateTime.now());
        currentOrder.setPrice(new Price(parseDouble(priceField.getText()), valueOf(currencyCombo.getValue().substring(0, 3))));
    }

    private void calculatePrice() {
        initCurrentOrder();
        currentOrder.setQuantity(numberOfTicketsCombo.getValue());
        currentOrder.calculateOrderPriceAmountByUSD();
        CurrencyType selectedCurrency = valueOf(currencyCombo.getValue().substring(0, 3));
        double convertedPrice = tryToConvertCurrencies(selectedCurrency);
        currentOrder.getPrice().setAmount(convertedPrice);
        priceField.setText(String.valueOf(limitNumberOfDecimalPlaces(convertedPrice * currentOrder.getQuantity(), 2)));
    }

    private double tryToConvertCurrencies(CurrencyType selectedCurrency) {
        double convertedPrice = 0;
        try {
            convertedPrice = convertCurrency(currentOrder.getOrderPriceCurrency(), selectedCurrency, currentOrder.getOrderPriceAmount());
        } catch (IOException e) {
            showMessageBox("Error","Currency Conversion Failed","Make sure you are connected to the internet.", Alert.AlertType.ERROR);
        }
        return convertedPrice;
    }

    private void getBackToFlightListPage() {
        currentOrder = null;
        openFlightsListPage();
    }

    private void openFlightsListPage() {
        try {
            closeCurrentPage(backBtn);
            openPage(this, "../FlightsListPage.fxml");
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

    private void registerOrder() {
        if (Double.parseDouble(numberOfTicketsCombo.getValue().toString()) != 0) {
            OrderDAOFactory.createOrderDAO().insertNewRecord(currentOrder);
            showMessageBox("Done!", "Order Registered successfully", "For further information see order history on the main window", Alert.AlertType.INFORMATION);
            closeCurrentPage(orderRegisterBtn);
        } else
            showMessageBox("Invalid Number Of Tickets", "The number of tickets is 0", "You must select at least 1", Alert.AlertType.WARNING);
    }

    private void getBackToMainPage() {
        currentOrder = null;
        selectedFlight = null;
        closeCurrentPage(backBtn);
    }
}
