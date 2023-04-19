package flight;

import utilities.Converter;

import java.time.LocalDateTime;

import static java.lang.Math.*;

public class Flight {
    private static final double RADIAN_DIVISOR_CONST = 57.299577951;
    public static final int EARTH_RADIUS = 6371;
    private LocalDateTime dateTime;
    private Airport originAirport;
    private Airport destinationAirport;

    public void setDateTime(LocalDateTime flightDateAndTime) {
        this.dateTime = flightDateAndTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
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
        return dateTime.isAfter(startDate) && dateTime.isBefore(endDate);
    }

    public boolean validDestination() {
        return !(originAirport.equals(destinationAirport));
    }

    public double estimateDistanceOfAirports() {
        double originLatitudeInRadian = toRadians(originAirport.getCoordinate().getLatitude());
        double originLongitudeInRadian = toRadians(originAirport.getCoordinate().getLongitude());
        double destinationLatitudeInRadian = toRadians(destinationAirport.getCoordinate().getLatitude());
        double destinationLongitudeInRadian = toRadians(destinationAirport.getCoordinate().getLongitude());

        double deltaLatitude = destinationLatitudeInRadian - originLatitudeInRadian;
        double deltaLongitude = destinationLongitudeInRadian - originLongitudeInRadian;

        double haversine = pow(Math.sin(deltaLatitude / 2), 2) +
                cos(originLatitudeInRadian) * cos(destinationLatitudeInRadian) * pow(Math.sin(deltaLongitude / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(haversine));

        return Converter.limitNumberOfDecimalPlaces(c * EARTH_RADIUS, 2);
    }

    //private double haversineFormula()
}
