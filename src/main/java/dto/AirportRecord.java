package dto;

import commonStructures.AirportCode;
import model.DBTable;
import model.submodel.Coordinate;
import model.submodel.Location;

public class AirportRecord extends DBTable {
    private AirportCode code;
    private Coordinate coordinate;
    private Location location;
    private String name;

    public AirportRecord(AirportCode code, Coordinate coordinate, Location location, String name) {
        this.code = code;
        this.coordinate = coordinate;
        this.location = location;
        this.name = name;
    }

    public AirportCode getCode() {
        return code;
    }

    public void setCode(AirportCode code) {
        this.code = code;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
