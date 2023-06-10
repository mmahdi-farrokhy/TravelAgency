package model.submodel;

import java.time.LocalDate;
import java.time.LocalTime;

public class FlightTableRow {
    private String flightIdCol;
    private String originAirportCol;
    private String destinationAirportCol;
    private LocalDate dateCol;
    private LocalTime timeCol;

    public FlightTableRow(String flightIdCol, String originAirportCol, String destinationAirportCol, LocalDate dateCol, LocalTime timeCol) {
        this.flightIdCol = flightIdCol;
        this.originAirportCol = originAirportCol;
        this.destinationAirportCol = destinationAirportCol;
        this.dateCol = dateCol;
        this.timeCol = timeCol;
    }

    public void setFlightIdCol(String flightIdCol) {
        this.flightIdCol = flightIdCol;
    }

    public String getFlightIdCol() {
        return flightIdCol;
    }

    public void setOriginAirportCol(String originAirportCol) {
        this.originAirportCol = originAirportCol;
    }

    public String getOriginAirportCol() {
        return originAirportCol;
    }

    public void setDestinationAirportCol(String destinationAirportCol) {
        this.destinationAirportCol = destinationAirportCol;
    }

    public String getDestinationAirportCol() {
        return destinationAirportCol;
    }

    public void setDateCol(LocalDate dateCol) {
        this.dateCol = dateCol;
    }

    public LocalDate getDateCol() {
        return dateCol;
    }

    public void setTimeCol(LocalTime timeCol) {
        this.timeCol = timeCol;
    }

    public LocalTime getTimeCol() {
        return timeCol;
    }
}
