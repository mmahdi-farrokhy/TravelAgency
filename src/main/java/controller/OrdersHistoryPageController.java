package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Flight;
import model.Order;
import model.submodel.OrderHistoryTableRow;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static data.factory.FlightDAOFactory.createFlightDAO;
import static data.factory.OrderDAOFactory.createOrderDAO;
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

        for (Order order : getCustomerOrders())
            for (Flight flight : createFlightDAO().getAllRecords())
                if (order.getOrderFlightId().equals(flight.getId()))
                    orderHistoryRows.add(createOrderHistoryTableRow(order, flight));

        return observableArrayList(orderHistoryRows);
    }

    private static OrderHistoryTableRow createOrderHistoryTableRow(Order order, Flight flight) {
        return new OrderHistoryTableRow(
                order.getId(),
                flight.getFlightOriginAirportCode() + ": " + flight.getFlightOriginAirportName(),
                flight.getFlightDestinationAirportCode() + ": " + flight.getFlightDestinationAirportName(),
                flight.getDepartureTime().toLocalDate(),
                flight.getDepartureTime().toLocalTime(),
                String.valueOf(order.getQuantity()),
                String.valueOf(order.getOrderPriceAmount())
        );
    }

    private static List<Order> getCustomerOrders() {
        return createOrderDAO().getAllRecords().stream()
                .filter(order -> Objects.equals(order.getOrderCustomerNationalCode(), loggedInCustomer.getNationalCode())).toList();
    }
}
