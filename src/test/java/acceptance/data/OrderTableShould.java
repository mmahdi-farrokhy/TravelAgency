package acceptance.data;

import commonStructures.AirportCode;
import commonStructures.City;
import commonStructures.CurrencyType;
import data.dao.OrderDAO;
import data.factory.OrderDAOFactory;
import model.Airport;
import model.Customer;
import model.Flight;
import model.Order;
import model.submodel.Address;
import model.submodel.FullName;
import model.submodel.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class OrderTableShould {
    private OrderDAO dbAccess;
    private Order order;
    private Customer bradPitt;
    private Flight flight;

    @BeforeEach
    void setUp() {
        dbAccess = OrderDAOFactory.createCustomerDAO();
        order = new Order();
        bradPitt = new Customer();
        flight = new Flight();

        bradPitt.setNationalCode("123");
        bradPitt.setFullName(new FullName("Brad", "Pitt"));
        bradPitt.setBirthDate(LocalDate.of(1963, 12, 18));
        bradPitt.setAddress(new Address(City.LOS_ANGELES, "Wilshire Blvd.", "CA 90212"));
        bradPitt.setPhoneNumber("(310) 275-6135");

        flight.setDepartureTime(LocalDateTime.of(2029,8,1,8,0,0));
        flight.setOriginAirport(new Airport(AirportCode.SAW));
        flight.setDestinationAirport(new Airport(AirportCode.PEK));

        order.setId("1");
        order.setQuantity(3);
        order.setPrice(new Price(794.98, CurrencyType.USD));
        order.setRegistrationTime(LocalDateTime.of(2023,4,29,22,45,6));
        order.setCustomerInfo(bradPitt);
        order.setFlight(flight);
    }

    @Test
    void get_all_orders_from_the_order_table() {
        List<Order> allCustomers = dbAccess.getAllRecords();
        assertThat(allCustomers.isEmpty()).isFalse();
        assertThat(allCustomers.get(0)).isEqualTo(order);
    }

    @Test
    void get_a_specific_order_from_order_table() {
        Order orderById = dbAccess.getRecordById("1");
        assertThat(orderById).isEqualTo(order);
    }

    @Test
    void insert_a_new_order_to_order_table() {
        Order newRecord = new Order();
        newRecord.setQuantity(1);
        newRecord.setRegistrationTime(now());
        newRecord.setCustomerInfo(new Customer("456"));
        newRecord.setFlight(new Flight("11"));
        newRecord.calculateOrderPriceAmountByUSD();
        assertThat(dbAccess.insertNewRecord(newRecord)).isFalse();
        assertThat(dbAccess.insertNewRecord(newRecord)).isFalse();
    }

    @Test
    void delete_a_order_from_order_table() {
        dbAccess.deleteRecordById("11");
        assertThatExceptionOfType(java.lang.RuntimeException.class)
                .isThrownBy(() -> dbAccess.getRecordById("11"));
    }
}
