package utilities;

import commonStructures.AirportCode;

public interface TravelAgencyDAO {
    String getAirportCityByCode(AirportCode airportCode);
    String getAirportCountryByCode(AirportCode airportCode);
    double getAirportLatitudeByCode(AirportCode airportCode);
    double getAirportLongitudeByCode(AirportCode airportCode);
}
