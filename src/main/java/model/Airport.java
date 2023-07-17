package model;

import common.structures.AirportCode;
import data.layer.daos.AirportDAO;
import data.layer.factories.AirportDAOFactory;
import model.submodel.Coordinate;
import model.submodel.Location;

import java.util.Objects;

public class Airport extends DBTable {
    private final AirportCode code;
    private Coordinate coordinate;
    private Location location;
    private String name;

    public Airport(AirportCode airportCode) {
        AirportDAO airportTable = AirportDAOFactory.createAirportDAO();
        this.code = airportCode;
        this.coordinate = airportTable.getAirportCoordinateByCode(code);
        this.location = airportTable.getAirportLocationByCode(code);
        this.name = airportTable.getAirportNameByCode(code);
    }

    public Airport(AirportCode airportCode, Coordinate coordinate, Location location, String name) {
        this.code = airportCode;
        this.coordinate = coordinate;
        this.location = location;
        this.name = name;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
