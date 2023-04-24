package databaseConnection;

import commonStructures.AirportCode;
import flight.Airport;
import flight.Coordinate;
import flight.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DBAccessShould {
    private AirportTable dbAccess;

    @BeforeEach
    void setUp() {
        dbAccess = new AirportTable();
    }

    @Test
    void get_all_airports_from_the_airport_table() {
        List<Airport> allAirPorts = dbAccess.getAllRecords();

        assertThat(allAirPorts.size()).isEqualTo(100);
        assertThat(allAirPorts.get(0)).isEqualTo(new Airport(AirportCode.AMS, new Coordinate(52.3105, 4.7683), new Location("Amsterdam", "Netherlands")));
    }

    @Test
    void get_a_specific_airport_from_airport_table() {
        Airport airPortByRUHCode = dbAccess.getRecordById(AirportCode.RUH);
        assertThat(airPortByRUHCode).isEqualTo(new Airport(AirportCode.RUH, new Coordinate(24.9596, 46.7024), new Location("Riyadh", "Saudi Arabia")));

        Airport airPortByMANCode = dbAccess.getRecordById(AirportCode.MAN);
        assertThat(airPortByMANCode).isEqualTo(new Airport(AirportCode.MAN, new Coordinate(53.3554, -2.2773), new Location("Manchester", "United Kingdom")));
    }

    @Test
    void get_a_specific_airports_location_from_the_airport_table() {
        Location airportLocationByRUHCode = dbAccess.getRecordLocationById(AirportCode.RUH);
        assertThat(airportLocationByRUHCode).isEqualTo(new Location("Riyadh", "Saudi Arabia"));
    }

    @Test
    void get_a_specific_airports_coordinate_from_the_airport_table() {
        Coordinate airportCoordinateByRUHCode = dbAccess.getRecordCoordinateById(AirportCode.RUH);
        assertThat(airportCoordinateByRUHCode).isEqualTo(new Coordinate(24.9596, 46.7024));
    }

}
