package databaseConnection;

import commonStructures.AirportCode;
import flight.Airport;

import java.util.List;

public class TravelAgencyDAOImplementer implements TravelAgencyDAO{
    @Override
    public List<Airport> getAllAirPorts() {
        return null;
    }

    @Override
    public String getAirportCityByCode(AirportCode airportCode) {
        return null;
    }

    @Override
    public String getAirportCountryByCode(AirportCode airportCode) {
        return null;
    }

    @Override
    public double getAirportLatitudeByCode(AirportCode airportCode) {
        return 0;
    }

    @Override
    public double getAirportLongitudeByCode(AirportCode airportCode) {
        return 0;
    }
}
