package flight;

import commonStructures.AirportCode;
import java.util.Objects;

public class Airport {
    private final AirportCode code;
    private double latitude;
    private double longitude;
    private String city;
    private String country;

    public Airport(AirportCode airportName) {
        this.code = airportName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return Double.compare(airport.latitude, latitude) == 0 && Double.compare(airport.longitude, longitude) == 0 && code == airport.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, latitude, longitude);
    }
}
