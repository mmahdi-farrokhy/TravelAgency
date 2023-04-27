package buisnessLayer;

import applicationLayer.Airport;
import applicationLayer.Coordinate;
import applicationLayer.Flight;
import commonStructures.AirportCode;
import commonStructures.City;
import dataLayer.AirportTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static java.time.LocalDate.of;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderShould {
    private Order order;
    private AirportTable airportTable;

    @BeforeEach
    void setUp() {
        order = new Order();
        airportTable = new AirportTable();
    }

    @Test
    void have_an_id() {
        order.setId("123");
        assertThat(order.getId()).isEqualTo("123");
    }

    @Test
    void have_a_quantity() {
        order.setQuantity(5);
        assertThat(order.getQuantity()).isEqualTo(5);
    }

    @Test
    void have_a_price() {
        Flight orderFlight = new Flight();
        orderFlight.setDepartureTime(LocalDateTime.of(2018, 5, 23, 19, 0));
        orderFlight.setOriginAirport(new Airport(AirportCode.RUH));
        orderFlight.setDestinationAirport(new Airport(AirportCode.AMS));
        order.setFlight(orderFlight);
        order.calculateOrderPriceAmountByUSD();
        assertThat(order.getPrice().getAmount()).isEqualTo(703.33);

        orderFlight.setDepartureTime(LocalDateTime.of(2023, 12, 8, 9, 20));
        orderFlight.setOriginAirport(new Airport(AirportCode.ATL));
        orderFlight.setDestinationAirport(new Airport(AirportCode.MAN));
        order.setFlight(orderFlight);
        order.calculateOrderPriceAmountByUSD();
        assertThat(order.getPrice().getAmount()).isEqualTo(794.98);
    }

    @Test
    void have_a_registration_time() {
        order.setRegistrationTime(now().truncatedTo(ChronoUnit.SECONDS));
        assertThat(order.getRegistrationTime()).isEqualTo(now().truncatedTo(ChronoUnit.SECONDS));
    }

    @Test
    void have_a_customer() {
        Customer customerInfo = new Customer();
        customerInfo.setNationalCode("0123");
        customerInfo.setFullName(new FullName("Brad", "Pitt"));
        customerInfo.setBirthDate(of(1963, 12, 18));
        customerInfo.setAddress(new Address(City.LOS_ANGELES, "Alpine Drive", "90001"));
        customerInfo.setPhoneNumber("0123456789");
        order.setCustomerInfo(customerInfo);

        assertThat(order.getCustomerInfo().getNationalCode()).isEqualTo("0123");
        assertThat(order.getCustomerInfo().getFullName().getFirstname()).isEqualTo("Brad");
        assertThat(order.getCustomerInfo().getFullName().getLastname()).isEqualTo("Pitt");
        assertThat(order.getCustomerInfo().getPhoneNumber()).isEqualTo("0123456789");
    }

    @Test
    void have_flight() {
        Flight orderFlight = new Flight();
        orderFlight.setDepartureTime(LocalDateTime.of(2018, 5, 23, 19, 0));
        orderFlight.setOriginAirport(new Airport(AirportCode.RUH));
        orderFlight.setDestinationAirport(new Airport(AirportCode.AMS));
        order.setFlight(orderFlight);

        assertThat(order.getFlight().getDepartureTime()).isEqualTo(LocalDateTime.of(2018, 5, 23, 19, 0));
        assertThat(order.getFlight().getOriginAirport()).isEqualTo(new Airport(AirportCode.RUH));
        assertThat(order.getFlight().getDestinationAirport()).isEqualTo(new Airport(AirportCode.AMS));
    }
}
