package flight;

import java.time.LocalDateTime;

public class Flight {
    private static final double RADIAN_DIVISOR_CONST = 57.299577951;
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

        return 0;
    }
}
