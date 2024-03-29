package controller;

import dto.FlightDTO;
import dto.OrderDTO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.submodel.OrderHistoryTableRow;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static data.dao.factory.FlightDAOFactory.createFlightDAO;
import static data.dao.factory.OrderDAOFactory.createOrderDAO;
import static javafx.collections.FXCollections.observableArrayList;
import static main.Main.loggedInCustomer;

public class OrdersHistoryPageController implements Initializable {
    @FXML
    private TableView<OrderHistoryTableRow> ordersHistoryTable;

    @FXML
    private TableColumn<OrderHistoryTableRow, String> orderIdCol;

    @FXML
    private TableColumn<OrderHistoryTableRow, String> originAirportCol;

    @FXML
    private TableColumn<OrderHistoryTableRow, String> destinationAirportCol;

    @FXML
    private TableColumn<OrderHistoryTableRow, LocalDate> flightDateCol;

    @FXML
    private TableColumn<OrderHistoryTableRow, LocalTime> flightTimeCol;

    @FXML
    private TableColumn<OrderHistoryTableRow, String> quantityCol;

    @FXML
    private TableColumn<OrderHistoryTableRow, String> priceCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillOrdersHistoryTable();
    }

    private void fillOrdersHistoryTable() {
        initOrdersHistoryTableColumns();
        ordersHistoryTable.setItems(getOrdersHistoryRows());
    }

    private void initOrdersHistoryTableColumns() {
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderIdCol"));
        originAirportCol.setCellValueFactory(new PropertyValueFactory<>("originAirportCol"));
        destinationAirportCol.setCellValueFactory(new PropertyValueFactory<>("destinationAirportCol"));
        flightDateCol.setCellValueFactory(new PropertyValueFactory<>("dateCol"));
        flightTimeCol.setCellValueFactory(new PropertyValueFactory<>("timeCol"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantityCol"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("priceCol"));
    }

    private ObservableList<OrderHistoryTableRow> getOrdersHistoryRows() {
        List<OrderHistoryTableRow> orderHistoryRows = new LinkedList<>();
        List<FlightDTO> flightsDTOList = createFlightDAO().getAllRecords();
        for (OrderDTO order : getCustomerOrders()) {
            for (FlightDTO flight : flightsDTOList)
                if (order.getFlight().getId().equals(flight.getId()))
                    orderHistoryRows.add(createOrderHistoryTableRow(order, flight));
        }

        return observableArrayList(orderHistoryRows);
    }

    private static OrderHistoryTableRow createOrderHistoryTableRow(OrderDTO order, FlightDTO flight) {
        return new OrderHistoryTableRow(
                order.getId(),
                flight.getOriginAirport().getCode() + ": " + flight.getOriginAirport().getName(),
                flight.getDestinationAirport().getCode() + ": " + flight.getDestinationAirport().getName(),
                flight.getDepartureTime().toLocalDate(),
                flight.getDepartureTime().toLocalTime(),
                String.valueOf(order.getQuantity()),
                String.valueOf(order.getPrice().getAmount())
        );
    }

    private static List<OrderDTO> getCustomerOrders() {
        return createOrderDAO().getAllRecords().stream()
                .filter(order -> Objects.equals(order.getCustomerInfo().getNationalCode(), loggedInCustomer.getNationalCode())).toList();
    }
}
