package applicationLayer;
import commonStructures.AirportCode;
import dataLayer.AirportTable;
import commonStructures.DBTable;

import java.util.Objects;

public class Airport extends DBTable {
    private final AirportCode code;
    private Coordinate coordinate;
    private Location location;

    public Airport(AirportCode airportCode) {
        AirportTable airportTable = new AirportTable();
        this.code = airportCode;
        this.coordinate = airportTable.getAirportCoordinateByCode(code);
        this.location = airportTable.getAirportLocationByCode(code);
    }

    public Airport(AirportCode airportCode, Coordinate coordinate, Location location) {
        this.code = airportCode;
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
    public String toString() {
        return "Airport{" +
                "code=" + code +
                ", coordinate=" + coordinate +
                ", location=" + location +
                '}';
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
