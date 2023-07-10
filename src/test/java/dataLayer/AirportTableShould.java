package dataLayer;

import commonStructures.AirportCode;
import dataLayer.dao.AirportDAO;
import dataLayer.factory.AirportDAOFactory;
import model.Airport;
import model.submodel.Coordinate;
import model.submodel.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AirportTableShould {
    private AirportDAO dbAccess;

    @BeforeEach
    void setUp() {
        dbAccess = AirportDAOFactory.createAirportDAO();
    }

    @Test
    void get_all_airports_from_the_airport_table() {
        List<Airport> allAirPorts = dbAccess.getAllRecords();

        assertThat(allAirPorts.size()).isEqualTo(100);
        assertThat(allAirPorts.get(0)).isEqualTo(new Airport(AirportCode.AMS));
    }

    @Test
    void get_a_specific_airport_from_airport_table() {
        Airport airPortByRUHCode = dbAccess.getRecordById(AirportCode.RUH.toString());
        assertThat(airPortByRUHCode).isEqualTo(new Airport(AirportCode.RUH));

        Airport airPortByMANCode = dbAccess.getRecordById(AirportCode.MAN.toString());
        assertThat(airPortByMANCode).isEqualTo(new Airport(AirportCode.MAN));
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

    @Test
    void get_a_specific_airports_name_from_the_airport_table() {
        String airportNameByRUHCode = dbAccess.getAirportNameByCode(AirportCode.RUH);
        assertThat(airportNameByRUHCode).isEqualTo("King Khalid International Airport");
    }
}
