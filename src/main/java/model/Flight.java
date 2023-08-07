package model;

import commonStructures.AirportCode;
import data.dao.FlightDAO;
import data.dao.factory.FlightDAOFactory;
import dto.FlightDTO;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.lang.Math.*;
import static utilities.ConversionUtils.limitNumberOfDecimalPlaces;

public class Flight extends DBTable {
    private static final int EARTH_RADIUS = 6371;
    private String id;
    private LocalDateTime departureTime;
    private Airport originAirport;
    private Airport destinationAirport;

    public Flight() {
        this.id = "";
        this.departureTime = null;
        this.originAirport = null;
        this.destinationAirport = null;
    }

    public Flight(String id) {
        FlightDAO flightTable = FlightDAOFactory.createFlightDAO();
        FlightDTO flightById = flightTable.getRecordById(id);
        this.id = id;
        this.departureTime = flightById.getDepartureTime();
        this.originAirport = flightById.getOriginAirport();
        this.destinationAirport = flightById.getDestinationAirport();
    }

    public Flight(String id, LocalDateTime departureTime, Airport originAirport, Airport destinationAirport) {
        this.id = id;
        this.departureTime = departureTime;
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setDepartureTime(LocalDateTime flightDateAndTime) {
        this.departureTime = flightDateAndTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setOriginAirport(Airport originAirport) {

        this.originAirport = originAirport;
    }

    public Airport getOriginAirport() {
        return originAirport;
    }

    public void setDestinationAirport(Airport destinationAirport) {

        this.destinationAirport = destinationAirport;
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public double estimateFlightDistance() {
        double originLatitudeInRadian = toRadians(originAirport.getCoordinate().getLatitude());
        double originLongitudeInRadian = toRadians(originAirport.getCoordinate().getLongitude());
        double destinationLatitudeInRadian = toRadians(destinationAirport.getCoordinate().getLatitude());
        double destinationLongitudeInRadian = toRadians(destinationAirport.getCoordinate().getLongitude());

        double deltaLatitude = destinationLatitudeInRadian - originLatitudeInRadian;
        double deltaLongitude = destinationLongitudeInRadian - originLongitudeInRadian;

        double haversine = pow(Math.sin(deltaLatitude / 2), 2) +
                cos(originLatitudeInRadian) * cos(destinationLatitudeInRadian) * pow(Math.sin(deltaLongitude / 2), 2);

        double c = 2 * asin(Math.sqrt(haversine));

        return limitNumberOfDecimalPlaces(c * EARTH_RADIUS, 2);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id='" + id + '\'' +
                ", departureTime=" + departureTime +
                ", originAirport=" + originAirport +
                ", destinationAirport=" + destinationAirport +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(departureTime, flight.departureTime) && Objects.equals(originAirport, flight.originAirport) && Objects.equals(destinationAirport, flight.destinationAirport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, departureTime, originAirport, destinationAirport);
    }

    public AirportCode getFlightOriginAirportCode() {
        return originAirport.getCode();
    }

    public AirportCode getFlightDestinationAirportCode() {
        return destinationAirport.getCode();
    }

    public String getFlightOriginAirportName() {
        return originAirport.getName();
    }

    public String getFlightDestinationAirportName() {
        return destinationAirport.getName();
    }
}
