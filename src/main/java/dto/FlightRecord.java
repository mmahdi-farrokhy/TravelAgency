package dto;

import model.Airport;
import model.DBTable;

import java.time.LocalDateTime;

public class FlightRecord extends DBTable {
    private String id;
    private LocalDateTime departureTime;
    private Airport originAirport;
    private Airport destinationAirport;

    public FlightRecord(String id, LocalDateTime departureTime, Airport originAirport, Airport destinationAirport) {
        this.id = id;
        this.departureTime = departureTime;
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public Airport getOriginAirport() {
        return originAirport;
    }

    public void setOriginAirport(Airport originAirport) {
        this.originAirport = originAirport;
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(Airport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }
}
