package dataLayer;

import applicationLayer.Airport;
import applicationLayer.Flight;
import commonStructures.AirportCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FlightTableShould {
    private FlightTable flightTable;

    @BeforeEach
    void setUp() {
        flightTable = new FlightTable();
    }

    @Test
    void get_all_flights_from_the_flight_table() {
        List<Flight> allFlights = flightTable.getAllRecords();
        assertThat(allFlights.isEmpty()).isFalse();

        Flight flight = new Flight();
        flight.setId("1");
        flight.setDepartureTime(LocalDateTime.of(2023, 04, 28, 12, 54, 30));
        flight.setOriginAirport(new Airport(AirportCode.ATL));
        flight.setDestinationAirport(new Airport(AirportCode.RUH));
        assertThat(allFlights.get(0)).isEqualTo(flight);

        assertThat(allFlights.size()).isEqualTo(3);
    }


}
