package flight;

import commonStructures.AirportCode;

import java.util.Objects;

public class Airport {
    private final AirportCode code;
    private Coordinate coordinate;
    private Location location;

    public Airport(AirportCode airportName) {
        this.code = airportName;
    }

    public Airport(AirportCode code, Coordinate coordinate) {
        this.code = code;
        this.coordinate = coordinate;
    }

    public Airport(AirportCode code, Coordinate coordinate, Location location) {
        this.code = code;
        this.coordinate = coordinate;
        this.location = location;
    }

    public AirportCode getCode() {
        return code;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return code == airport.code && Objects.equals(coordinate, airport.coordinate) && Objects.equals(location, airport.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, coordinate, location);
    }
}
