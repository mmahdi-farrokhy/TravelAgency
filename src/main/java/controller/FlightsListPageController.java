package controller;

import commonStructures.AirportCode;
import dataLayer.AirportTable;
import dataLayer.FlightTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Airport;
import model.Flight;
import model.submodel.FlightTableRow;
import utilities.GUIUtils;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

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


    private List<Airport> airportsList;
    private List<Flight> flightsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        airportsList = new AirportTable().getAllRecords();
        flightsList = new FlightTable().getAllRecords();

        initOriginAirportComboBox();
        initDestinationAirportComboBox();

        clearFiltersBtn.setOnMouseEntered(e -> GUIUtils.setButtonStyle((Button) e.getSource(), 8));
        clearFiltersBtn.setOnMouseExited(e -> GUIUtils.resetButtonStyle((Button) e.getSource(), 8));
        clearFiltersBtn.setOnAction(e -> clearFilters());

        searchBtn.setOnMouseEntered(e -> GUIUtils.setButtonStyle((Button) e.getSource(), 8));
        searchBtn.setOnMouseExited(e -> GUIUtils.resetButtonStyle((Button) e.getSource(), 8));
        searchBtn.setOnAction(e -> fillFlightsTable());

        bookBtn.setOnMouseEntered(e -> GUIUtils.setButtonStyle((Button) e.getSource(), 8));
        bookBtn.setOnMouseExited(e -> GUIUtils.resetButtonStyle((Button) e.getSource(), 8));
        bookBtn.setOnAction(e -> openBookingPage());
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
                    flight.getOriginAirport().getName(),
                    flight.getDestinationAirport().getName(),
                    flight.getDepartureTime().toLocalDate(),
                    flight.getDepartureTime().toLocalTime()));

        List<FlightTableRow> filteredFlightRows = filterFlightRows(flightRows);
        return FXCollections.observableArrayList(filteredFlightRows);
    }

    private List<FlightTableRow> filterFlightRows(List<FlightTableRow> flightRows) {
        List<FlightTableRow> filteredFlightRows = flightRows;
        if (originAirportCb.getValue() != null && originAirportCb.getValue() != "")
            filteredFlightRows = filterByOriginAirport(filteredFlightRows);

        if (destinationAirportCb.getValue() != null && destinationAirportCb.getValue() != "")
            filteredFlightRows = filterByDestinationAirport(filteredFlightRows);

        if (departureDp.getValue() != null && departureDp.getValue().toString() != "")
            filteredFlightRows = filterByDepartureDate(filteredFlightRows);

        if (arrivalDp.getValue() != null && arrivalDp.getValue().toString() != "")
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
        GUIUtils.openPage(this, "../BookingPage.fxml");
    }

    private void clearFilters() {
        originAirportCb.setValue("");
        destinationAirportCb.setValue("");
        departureDp.setValue(null);
        departureDp.getEditor().clear();
        arrivalDp.setValue(null);
        arrivalDp.getEditor().clear();
    }
}
