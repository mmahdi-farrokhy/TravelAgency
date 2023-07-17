package model.submodel;

import commonstructures.AirportCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class FlightTableRowShould {
    private FlightTableRow flightTableRow;

    @BeforeEach
    void setUp() {

        String id = "123";
        String originAirportCol = AirportCode.RUH + ": King Khalid International Airport";
        String destinationAirportCol = AirportCode.MCO + ": Orlando International Airport";
        LocalDate localDateCol = LocalDate.of(2023, 6, 23);
        LocalTime localTimeCol = LocalTime.of(5, 53);

        flightTableRow = new FlightTableRow(id, originAirportCol, destinationAirportCol, localDateCol, localTimeCol);
    }

    @Test
    void have_a_flight_id_column() {
        assertThat(flightTableRow.getFlightIdCol()).isEqualTo("123");
    }

    @Test
    void have_an_origin_airport_column() {
        assertThat(flightTableRow.getOriginAirportCol()).isEqualTo("RUH: King Khalid International Airport");
    }

    @Test
    void have_a_destination_airport_column() {
        assertThat(flightTableRow.getDestinationAirportCol()).isEqualTo("MCO: Orlando International Airport");
    }

    @Test
    void have_a_date_column() {
        assertThat(flightTableRow.getDateCol()).isEqualTo(LocalDate.of(2023,6,23));
    }

    @Test
    void have_a_time_column() {
        assertThat(flightTableRow.getTimeCol()).isEqualTo(LocalTime.of(5,53));
    }

}
