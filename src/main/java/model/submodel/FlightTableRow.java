package model.submodel;

import java.time.LocalDate;
import java.time.LocalTime;

public class FlightTableRow {
    private String flightIdCol;
    private String originAirportCol;
    private String destinationAirportCol;
    private LocalDate dateCol;
    private LocalTime timeCol;

    public FlightTableRow(String id, String originAirport, String destinationAirport, LocalDate date, LocalTime time) {
        this.flightIdCol = id;
        this.originAirportCol = originAirport;
        this.destinationAirportCol = destinationAirport;
        this.dateCol = date;
        this.timeCol = time;
    }

    public String getFlightIdCol() {
        return flightIdCol;
    }

    public String getOriginAirportCol() {
        return originAirportCol;
    }

    public String getDestinationAirportCol() {
        return destinationAirportCol;
    }

    public LocalDate getDateCol() {
        return dateCol;
    }

    public LocalTime getTimeCol() {
        return timeCol;
    }
}
