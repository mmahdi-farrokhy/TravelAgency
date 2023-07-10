package dataLayer.dao;

import commonStructures.AirportCode;
import dataLayer.DBAccess;
import model.Airport;
import model.submodel.Coordinate;
import model.submodel.Location;

public interface AirportDAO extends DBAccess<Airport> {
    Location getAirportLocationByCode(AirportCode airportCode);
    Coordinate getAirportCoordinateByCode(AirportCode airportCode);
    String getAirportNameByCode(AirportCode airportCode);
}
