package databaseConnection;

import commonStructures.AirportCode;
import flight.Airport;
import flight.Coordinate;
import flight.Location;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilities.Converter;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class DBAccessShould {
    private DBAccess dbAccess;

    @BeforeEach
    void setUp() {
        dbAccess = new DBAccess();
    }

    @Test
    void get_all_airports_from_the_airport_table() {
        List<Airport> allAirPorts = dbAccess.getAllAirPorts();

        assertThat(allAirPorts.size()).isEqualTo(100);
        assertThat(allAirPorts.get(0)).isEqualTo(new Airport(AirportCode.AMS, new Coordinate(52.3105, 4.7683), new Location("Amsterdam", "Netherlands")));
    }

    @Test
    void get_a_specific_airport_from_airport_table() {
        Airport airPortByRUHCode = dbAccess.getAirPortByCode(AirportCode.RUH);
        assertThat(airPortByRUHCode).isEqualTo(new Airport(AirportCode.RUH, new Coordinate(24.9596, 46.7024), new Location("Riyadh", "Saudi Arabia")));

        Airport airPortByMANCode = dbAccess.getAirPortByCode(AirportCode.MAN);
        assertThat(airPortByMANCode).isEqualTo(new Airport(AirportCode.MAN, new Coordinate(53.3554, -2.2773), new Location("Manchester", "United Kingdom")));
    }

    @Test
    void get_a_specific_airports_location_from_the_airport_table() {
        Location airportLocationByRUHCode = dbAccess.getAirportLocationByCode(AirportCode.RUH);
        assertThat(airportLocationByRUHCode).isEqualTo(new Location("Riyadh", "Saudi Arabia"));
    }

    @Test
    void get_a_specific_airports_coordinate_from_the_airport_table() {
        Coordinate airportCoordinateByRUHCode = dbAccess.getAirportCoordinateByCode(AirportCode.RUH);
        assertThat(airportCoordinateByRUHCode).isEqualTo(new Coordinate(24.9596, 46.7024));
    }

}
