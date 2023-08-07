package controller;

import commonStructures.AirportCode;
import data.dao.factory.AirportDAOFactory;
import data.dao.factory.FlightDAOFactory;
import dto.FlightDTO;
import exceptions.NoFlightSelectedException;
import exceptions.NoSuchFXMLFileExistingException;
import exceptions.NoUserLoggedInException;
import exceptions.UnexpectedException;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Airport;
import model.Flight;
import model.submodel.FlightTableRow;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import static commonStructures.AirportCode.valueOf;
import static dto.FlightDTO.flightListFromFlightDTOList;
import static javafx.collections.FXCollections.observableArrayList;
import static main.Main.selectedFlight;
import static model.submodel.FlightTableRow.filterFlightRows;
import static utilities.ButtonActionInitializer.setOnActionMethods;
import static utilities.GUIUtils.*;

public class FlightsListPageController implements Initializable {
    @FXML
    public ComboBox<String> originAirportCb;

    @FXML
    public ComboBox<String> destinationAirportCb;

    @FXML
    public DatePicker departureDp;

    @FXML
    public DatePicker arrivalDp;

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
    private List<FlightDTO> flightsDTOList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        airportsList = AirportDAOFactory.createAirportDAO().getAllRecords();
        flightsDTOList = FlightDAOFactory.createFlightDAO().getAllRecords();
        initAirportCombo(originAirportCb);
        initAirportCombo(destinationAirportCb);

        flightListTable.getSelectionModel().selectedItemProperty().addListener(getSelectedRow());
        setOnActionMethods(clearFiltersBtn, 8, this::clearFilters);
        setOnActionMethods(searchBtn, 8, this::fillFlightsTable);
        setOnActionMethods(bookBtn, 8, this::openBookingPage);
        setOnActionMethods(cancelBtn, 8, this::closeFlightsListPage);
    }

    private ChangeListener<FlightTableRow> getSelectedRow() {
        return (obs, oldSelection, newSelection) -> {
            if (newSelection != null)
                selectedFlight = getSelectedFlightFromTable(newSelection);
        };
    }

    private void initAirportCombo(ComboBox<String> airportCombo) {
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
        for (Flight flight : flightListFromFlightDTOList(flightsDTOList))
            flightRows.add(new FlightTableRow(flight.getId(),
                    flight.getFlightOriginAirportCode() + ": " + flight.getFlightOriginAirportName(),
                    flight.getFlightDestinationAirportCode() + ": " + flight.getFlightDestinationAirportName(),
                    flight.getDepartureTime().toLocalDate(),
                    flight.getDepartureTime().toLocalTime()));

        List<FlightTableRow> filteredFlightRows = filterFlightRows(this, flightRows);
        return observableArrayList(filteredFlightRows);
    }

    private void openBookingPage() {
        try {
            closeCurrentPage(bookBtn);
            openPage(this, "../BookingPage.fxml");
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

    private FlightDTO getSelectedFlightFromTable(FlightTableRow selectedRow) {
        String id = selectedRow.getFlightIdCol();
        Airport originAirport = new Airport(valueOf(selectedRow.getOriginAirportCol().substring(0, 3)));
        Airport destinationAirport = new Airport(valueOf(selectedRow.getDestinationAirportCol().substring(0, 3)));
        LocalDateTime departureTime = LocalDateTime.of(selectedRow.getDateCol(), selectedRow.getTimeCol());

        FlightDTO selectedFlight = new FlightDTO();
        selectedFlight.setId(id);
        selectedFlight.setOriginAirport(originAirport);
        selectedFlight.setDestinationAirport(destinationAirport);
        selectedFlight.setDepartureTime(departureTime);
        return selectedFlight;
    }

    private void closeFlightsListPage() {
        selectedFlight = null;
        bookBtn.getScene().getWindow().hide();
    }
}
