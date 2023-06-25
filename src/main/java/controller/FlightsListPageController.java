package controller;

import commonStructures.AirportCode;
import dataLayer.AirportTable;
import dataLayer.FlightTable;
import exceptions.NoFlightSelectedException;
import exceptions.NoUserLoggedInException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;
import model.Airport;
import model.Flight;
import model.submodel.FlightTableRow;
import utilities.ButtonActionInitializer;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static commonStructures.AirportCode.valueOf;
import static utilities.GUIUtils.*;

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
    private TableView<FlightTableRow> flightListTable;

    @FXML
    private TableColumn<FlightTableRow, String> flightIdCol;

    @FXML
    private TableColumn<FlightTableRow, String> originAirportCol;

    @FXML
    private TableColumn<FlightTableRow, String> destinationAirportCol;

    @FXML
    private TableColumn<FlightTableRow, LocalDate> flightDateCol;

    @FXML
    private TableColumn<FlightTableRow, LocalTime> flightTimeCol;

    @FXML
    private Button clearFiltersBtn;

    @FXML
    private Button searchBtn;

    @FXML
    private Button bookBtn;

    @FXML
    private Button cancelBtn;

    private List<Airport> airportsList;
    private List<Flight> flightsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        airportsList = new AirportTable().getAllRecords();
        flightsList = new FlightTable().getAllRecords();
        initAirportCombo(originAirportCb);
        initAirportCombo(destinationAirportCb);
        ButtonActionInitializer buttonActionInitializer = new ButtonActionInitializer();
        buttonActionInitializer.setOnActionMethods(clearFiltersBtn, 8, this::clearFilters);
        buttonActionInitializer.setOnActionMethods(searchBtn, 8, this::fillFlightsTable);
        buttonActionInitializer.setOnActionMethods(bookBtn, 8, this::openBookingPage);
        buttonActionInitializer.setOnActionMethods(cancelBtn, 8, this::closeFlightsListPage);
    }

    private void initAirportCombo(ComboBox<String> airportCombo){
        airportCombo.getItems().clear();
        airportCombo.getItems().add("");
        for (var airport : airportsList)
            if (airport.getCode() != AirportCode.NONE)
                airportCombo.getItems().add(airport.getCode() + ": " + airport.getName());
    }

    private void clearFilters() {
        originAirportCb.setValue("");
        destinationAirportCb.setValue("");
        departureDp.setValue(null);
        departureDp.getEditor().clear();
        arrivalDp.setValue(null);
        arrivalDp.getEditor().clear();
    }

    private void fillFlightsTable() {
        initFlightsTableColumns();
        flightListTable.setItems(getFlightRows());
    }

    private void initFlightsTableColumns() {
        flightIdCol.setCellValueFactory(new PropertyValueFactory<>("flightIdCol"));
        originAirportCol.setCellValueFactory(new PropertyValueFactory<>("originAirportCol"));
        destinationAirportCol.setCellValueFactory(new PropertyValueFactory<>("destinationAirportCol"));
        flightDateCol.setCellValueFactory(new PropertyValueFactory<>("dateCol"));
        flightTimeCol.setCellValueFactory(new PropertyValueFactory<>("timeCol"));
    }

    private ObservableList<FlightTableRow> getFlightRows() {
        List<FlightTableRow> flightRows = new LinkedList<>();
        for (Flight flight : flightsList)
            flightRows.add(new FlightTableRow(flight.getId(),
                    flight.getOriginAirport().getCode() + ": " + flight.getOriginAirport().getName(),
                    flight.getDestinationAirport().getCode() + ": " + flight.getDestinationAirport().getName(),
                    flight.getDepartureTime().toLocalDate(),
                    flight.getDepartureTime().toLocalTime()));

        List<FlightTableRow> filteredFlightRows = filterFlightRows(flightRows);
        return FXCollections.observableArrayList(filteredFlightRows);
    }

    private List<FlightTableRow> filterFlightRows(List<FlightTableRow> flightRows) {
        List<FlightTableRow> filteredFlightRows = flightRows;
        if (originAirportCb.getValue() != null && !Objects.equals(originAirportCb.getValue(), ""))
            filteredFlightRows = filterByOriginAirport(filteredFlightRows);

        if (destinationAirportCb.getValue() != null && !Objects.equals(destinationAirportCb.getValue(), ""))
            filteredFlightRows = filterByDestinationAirport(filteredFlightRows);

        if (departureDp.getValue() != null && !departureDp.getValue().toString().equals(""))
            filteredFlightRows = filterByDepartureDate(filteredFlightRows);

        if (arrivalDp.getValue() != null && !arrivalDp.getValue().toString().equals(""))
            filteredFlightRows = filterByArrivalDate(filteredFlightRows);

        return filteredFlightRows;
    }

    private List<FlightTableRow> filterByOriginAirport(List<FlightTableRow> flightRows) {
        return flightRows.stream().filter(row -> row.getOriginAirportCol().trim().equals(originAirportCb.getValue().trim())).collect(Collectors.toList());
    }

    private List<FlightTableRow> filterByDestinationAirport(List<FlightTableRow> flightRows) {
        return flightRows.stream().filter(row -> row.getDestinationAirportCol().equals(destinationAirportCb.getValue().trim())).collect(Collectors.toList());
    }

    private List<FlightTableRow> filterByDepartureDate(List<FlightTableRow> flightRows) {
        return flightRows.stream().filter(row -> row.getDateCol().isAfter(departureDp.getValue())).collect(Collectors.toList());
    }

    private List<FlightTableRow> filterByArrivalDate(List<FlightTableRow> flightRows) {
        return flightRows.stream().filter(row -> row.getDateCol().isBefore(arrivalDp.getValue())).collect(Collectors.toList());
    }

    private void openBookingPage() {
        try {
            Main.selectedFlight = getSelectedFlightFromTable();
            closePageAfterOperation(bookBtn);
            openPage(this, "../BookingPage.fxml");
        } catch (NoUserLoggedInException e) {
            showMessageBox("Attention", "User not registered", "Please login or sign up first", Alert.AlertType.ERROR);
        } catch (NoFlightSelectedException e) {
            showMessageBox("Attention", "No flight is selected", "Please select a flight from the list", Alert.AlertType.WARNING);
        }
    }

    private Flight getSelectedFlightFromTable() {
        FlightTableRow selectedFlightRow = flightListTable.getSelectionModel().getSelectedItem();
        if (selectedFlightRow == null)
            return null;

        String id = selectedFlightRow.getFlightIdCol();
        Airport originAirport = new Airport(valueOf(selectedFlightRow.getOriginAirportCol().substring(0, 3)));
        Airport destinationAirport = new Airport(valueOf(selectedFlightRow.getDestinationAirportCol().substring(0, 3)));
        LocalDateTime departureTime = LocalDateTime.of(selectedFlightRow.getDateCol(), selectedFlightRow.getTimeCol());

        Flight selectedFlight = new Flight();
        selectedFlight.setId(id);
        selectedFlight.setOriginAirport(originAirport);
        selectedFlight.setDestinationAirport(destinationAirport);
        selectedFlight.setDepartureTime(departureTime);
        return selectedFlight;
    }

    private void closeFlightsListPage() {
        Main.selectedFlight = null;
        bookBtn.getScene().getWindow().hide();
    }
}
