package acceptance.data;

import data.dao.FlightDAO;
import data.dao.factory.FlightDAOFactory;
import dto.FlightDTO;
import model.Airport;
import commonStructures.AirportCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class FlightTableShould {
    private FlightDAO dbAccess;

    @BeforeEach
    void setUp() {
        dbAccess = FlightDAOFactory.createFlightDAO();
    }

    @Test
    void get_all_flights_from_the_flight_table() {
        List<FlightDTO> allFlights = dbAccess.getAllRecords();
        assertThat(allFlights.isEmpty()).isFalse();

        FlightDTO flight = new FlightDTO();
        flight.setId("1");
        flight.setDepartureTime(LocalDateTime.of(2023, 4, 28, 12, 54, 30));
        flight.setOriginAirport(new Airport(AirportCode.ATL));
        flight.setDestinationAirport(new Airport(AirportCode.RUH));
        assertThat(allFlights.get(0)).isEqualTo(flight);
    }

    @Test
    void get_a_specific_airport_from_flight_table() {
        FlightDTO flightById1 = dbAccess.getRecordById("1");
        assertThat(flightById1.getOriginAirport()).isEqualTo(new Airport(AirportCode.ATL));
        assertThat(flightById1.getDestinationAirport()).isEqualTo(new Airport(AirportCode.RUH));

        FlightDTO flightById2 = dbAccess.getRecordById("2");
        assertThat(flightById2.getOriginAirport()).isEqualTo(new Airport(AirportCode.MAN));
        assertThat(flightById2.getDestinationAirport()).isEqualTo(new Airport(AirportCode.NKG));
    }

    @Test
    void insert_a_new_flight_to_flight_table() {
        FlightDTO newRecord = new FlightDTO();
        newRecord.setDepartureTime(LocalDateTime.of(2029, 8, 1, 8, 0, 0));
        newRecord.setOriginAirport(new Airport(AirportCode.CUN));
        newRecord.setDestinationAirport(new Airport(AirportCode.ZRH));
        assertThat(dbAccess.insertNewRecord(newRecord)).isFalse();

        newRecord.setDepartureTime(LocalDateTime.of(2029, 8, 1, 8, 0, 0));
        newRecord.setOriginAirport(new Airport(AirportCode.SAW));
        newRecord.setDestinationAirport(new Airport(AirportCode.PEK));
        assertThat(dbAccess.insertNewRecord(newRecord)).isFalse();
    }

    @Test
    void delete_a_flight_from_flight_table() {
        dbAccess.deleteRecordById("4");
        assertThatExceptionOfType(java.lang.RuntimeException.class)
                .isThrownBy(() -> dbAccess.getRecordById("4"));
    }
}
