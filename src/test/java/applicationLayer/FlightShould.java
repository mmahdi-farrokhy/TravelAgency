package applicationLayer;

import commonStructures.AirportCode;
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
    void have_a_time_and_date() {
        String flightDateString = flight.getDepartureTime().toString();
        assertThat(flightDateString).isEqualTo("2023-04-18T20:34");
    }

    @Test
    void check_if_its_time_and_date_is_in_a_specific_range() {
        LocalDateTime startDate1 = LocalDateTime.of(2023, 3, 29, 12, 50);
        LocalDateTime endDate1 = LocalDateTime.of(2023, 4, 29, 12, 50);
        LocalDateTime startDate2 = LocalDateTime.of(2023, 4, 29, 12, 50);
        LocalDateTime endDate2 = LocalDateTime.of(2023, 5, 29, 12, 50);

        boolean isInRange1 = flight.isInTimeRange(startDate1, endDate1);
        boolean isInRange2 = flight.isInTimeRange(startDate2, endDate2);

        assertThat(isInRange1).isTrue();
        assertThat(isInRange2).isFalse();
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
    void check_if_the_origin_and_the_destination_are_the_same() {
        flight.setOriginAirport(new Airport(AirportCode.ARN));
        flight.setDestinationAirport(new Airport(AirportCode.AUH));
        assertThat(flight.validDestination()).isTrue();

        flight.setOriginAirport(new Airport(AirportCode.BOG));
        flight.setDestinationAirport(new Airport(AirportCode.BOG));
        assertThat(flight.validDestination()).isFalse();
    }

    @Test
    void calculate_distance_between_two_airports() {
        flight.setOriginAirport(new Airport(AirportCode.ATL, new Coordinate(33.6407, -84.4277)));
        flight.setDestinationAirport(new Airport(AirportCode.CUN, new Coordinate(21.0417, -86.8740)));

        double distance = flight.estimateFlightDistance();

        assertThat(distance).isEqualTo(1421.48);
    }
}
