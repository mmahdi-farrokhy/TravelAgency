package acceptance.model;

import commonStructures.AirportCode;
import model.Airport;
import model.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class FlightShould {
    private LocalDateTime flightDateAndTime;
    private Flight flight;

    @BeforeEach
    void setUp() {
        flight = new Flight();
        flightDateAndTime = LocalDateTime.of(2023, 4, 18, 20, 34);
        flight.setDepartureTime(flightDateAndTime);
    }

    @Test
    void have_an_id() {
        flight.setId("123");
        assertThat(flight.getId()).isEqualTo("123");
    }

    @Test
    void have_a_time_and_date() {
        String flightDateString = flight.getDepartureTime().toString();
        assertThat(flightDateString).isEqualTo("2023-04-18T20:34");
    }

    @Test
    void have_an_origin() {
        flight.setOriginAirport(new Airport(AirportCode.AMS));
        assertThat(flight.getOriginAirport()).isEqualTo(new Airport(AirportCode.AMS));
    }

    @Test
    void have_a_destination() {
        flight.setDestinationAirport(new Airport(AirportCode.ATL));
        assertThat(flight.getDestinationAirport()).isEqualTo(new Airport(AirportCode.ATL));
    }

    @Test
    void calculate_distance_between_two_airports() {
        flight.setOriginAirport(new Airport(AirportCode.ATL));
        flight.setDestinationAirport(new Airport(AirportCode.CUN));

        double distance = flight.estimateFlightDistance();

        assertThat(distance).isEqualTo(1421.48);
    }
}
