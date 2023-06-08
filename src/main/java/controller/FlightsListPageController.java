package controller;

import commonStructures.AirportCode;
import commonStructures.CurrencyType;
import dataLayer.AirportTable;
import dataLayer.FlightTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import model.Airport;
import model.Flight;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class FlightsListPageController implements Initializable {
    @FXML
    private ComboBox<String> originAirportCb;

    @FXML
    private ComboBox<String> destinationAirportCb;

    @FXML
    private DatePicker departureDp;

    @FXML
    private DatePicker arrivalDp;

    @FXML
    private TableView<Flight> flightListTable;

    @FXML
    private TableColumn<Flight, String> flightIdCol;

    @FXML
    private TableColumn<Flight, Airport> originAirportCol;

    @FXML
    private TableColumn<Flight, Airport> destinationAirportCol;

    @FXML
    private TableColumn<Flight, LocalDateTime> flightDateTimeCol;

    @FXML
    private ComboBox<String> currencyCb;

    @FXML
    private ImageView searchImage;

    @FXML
    private Button searchBtn;

    @FXML
    private Button bookBtn;

    private List<Airport> airportsList;
    private List<Flight> flightsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        airportsList = new AirportTable().getAllRecords();
        flightsList = new FlightTable().getAllRecords();
        initOriginAirportComboBox();
        initDestinationAirportComboBox();
        initCurrencyComboBox();
        searchBtn.setOnAction(e -> fillFlightsTable());
    }

    private void initOriginAirportComboBox() {
        originAirportCb.getItems().clear();
        originAirportCb.getItems().add("");
        for (var airport : airportsList)
            if (airport.getCode() != AirportCode.NONE)
                originAirportCb.getItems().add(airport.getName());
    }

    private void initDestinationAirportComboBox() {
        destinationAirportCb.getItems().clear();
        destinationAirportCb.getItems().add("");
        for (var airport : airportsList)
            if (airport.getCode() != AirportCode.NONE)
                destinationAirportCb.getItems().add(airport.getName());
    }

    private void initCurrencyComboBox() {
        CurrencyType[] sortedCities = CurrencyType.values();
        Arrays.sort(sortedCities, Comparator.comparing(Enum::name));
        currencyCb.getItems().clear();
        currencyCb.getItems().add("");
        for (CurrencyType currency : sortedCities)
            if (currency != CurrencyType.NONE)
                currencyCb.getItems().add(currency.toString() + " " + currency.getCurrencySymbol(currency));
    }

    private void fillFlightsTable() {
        initFlightsTable();
    }

    private void initFlightsTable() {
        initFlightsTableColumns();
        flightListTable.setItems(getFlightRows());
    }

    private ObservableList<Flight> getFlightRows() {
         return FXCollections.observableArrayList(flightsList);
    }

    private void initFlightsTableColumns() {
        Flight recordById = new FlightTable().getRecordById("1");
        flightIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        originAirportCol.setCellValueFactory(new PropertyValueFactory<>("originAirport"));
        destinationAirportCol.setCellValueFactory(new PropertyValueFactory<>("destinationAirport"));
        flightDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
    }
}
