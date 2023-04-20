package databaseConnection;

import commonStructures.AirportCode;
import flight.Airport;
import flight.Coordinate;
import flight.Location;

import java.util.List;

public interface DAO {
    List<Airport> getAllAirPorts();
    Airport getAirPortByCode(AirportCode airportCode);
    Location getAirportLocationByCode(AirportCode airportCode);
    Coordinate getAirportCoordinateByCode(AirportCode airportCode);
}
