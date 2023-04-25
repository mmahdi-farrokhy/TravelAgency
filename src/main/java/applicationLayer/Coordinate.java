package applicationLayer;

import java.util.Objects;

public class Coordinate {
    private final double Latitude;
    private final double Longitude;

    public Coordinate(double latitude, double longitude) {
        this.Latitude = latitude;
        this.Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "latitude=" + Latitude +
                ", longitude=" + Longitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Double.compare(that.Latitude, Latitude) == 0 && Double.compare(that.Longitude, Longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Latitude, Longitude);
    }
}
