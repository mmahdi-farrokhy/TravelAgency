package databaseConnection;

import commonStructures.AirportCode;
import flight.Airport;
import flight.Coordinate;
import flight.Location;

import java.util.List;
import java.util.Locale;

public interface TravelAgencyDAO {
    List<Airport> getAllAirPorts();
    Airport getAirPortByCode(AirportCode airportCode);
    Location getAirportLocationByCode(AirportCode airportCode);
    Coordinate getAirportCoordinateByCode(AirportCode airportCode);
}
