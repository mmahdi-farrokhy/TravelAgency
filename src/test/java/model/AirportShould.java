package model;

import common.structures.AirportCode;
import model.submodel.Coordinate;
import model.submodel.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AirportShould {

    private Airport airport;

    @BeforeEach
    void setUp() {
        airport = new Airport(AirportCode.CUN);
    }

    @Test
    void have_a_code() {
        assertThat(airport.getCode()).isEqualTo(AirportCode.CUN);
    }

    @Test
    void have_a_coordinate() {
        airport.setCoordinate(new Coordinate(21.0417, 86.8740));

        assertThat(airport.getCoordinate().getLatitude()).isEqualTo(21.0417);
        assertThat(airport.getCoordinate().getLongitude()).isEqualTo(86.8740);
    }

    @Test
    void have_a_city_and_a_country() {
        airport.setLocation(new Location("Cancun", "Mexico"));

        assertThat(airport.getLocation().getCity()).isEqualTo("Cancun");
        assertThat(airport.getLocation().getCountry()).isEqualTo("Mexico");
    }

    @Test
    void compare_two_airports() {
        Airport airport2 = new Airport(AirportCode.CUN);
        Airport airport3 = new Airport(AirportCode.RUH);

        boolean sameAirports1_2 = airport.equals(airport2);
        boolean sameAirports1_3 = airport.equals(airport3);

        assertThat(sameAirports1_2).isTrue();
        assertThat(sameAirports1_3).isFalse();
    }
}
