package databaseConnection;

import commonStructures.AirportCode;
import flight.Airport;

import java.util.List;

public interface TravelAgencyDAO {
    List<Airport> getAllAirPorts();
    String getAirportCityByCode(AirportCode airportCode);
    String getAirportCountryByCode(AirportCode airportCode);
    double getAirportLatitudeByCode(AirportCode airportCode);
    double getAirportLongitudeByCode(AirportCode airportCode);
}
