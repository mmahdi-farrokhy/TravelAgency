package datalayer.daos;

import commonstructures.AirportCode;
import datalayer.crud.DBAccess;
import model.Airport;
import model.submodel.Coordinate;
import model.submodel.Location;

public interface AirportDAO extends DBAccess<Airport> {
    Location getAirportLocationByCode(AirportCode airportCode);
    Coordinate getAirportCoordinateByCode(AirportCode airportCode);
    String getAirportNameByCode(AirportCode airportCode);
}
