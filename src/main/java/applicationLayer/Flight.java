package applicationLayer;

import commonStructures.DBTable;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.lang.Math.*;
import static utilities.Converter.limitNumberOfDecimalPlaces;

public class Flight extends DBTable {
    private static final int EARTH_RADIUS = 6371;
    private String id;
    private LocalDateTime departureTime;
    private Airport originAirport;
    private Airport destinationAirport;

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

    public boolean isInTimeRange(LocalDateTime startDate, LocalDateTime endDate) {
        return departureTime.isAfter(startDate) && departureTime.isBefore(endDate);
    }

    public boolean validDestination() {
        return !(originAirport.equals(destinationAirport));
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
        return Objects.equals(id, flight.id) && Objects.equals(departureTime, flight.departureTime) && Objects.equals(originAirport, flight.originAirport) && Objects.equals(destinationAirport, flight.destinationAirport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, departureTime, originAirport, destinationAirport);
    }
}
