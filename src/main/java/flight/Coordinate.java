package flight;

import java.util.Objects;

public class Coordinate {
    private double Latitude;
    private double Longitude;

    public Coordinate(double latitude, double longitude) {
        this.Latitude = latitude;
        this.Longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.Latitude = latitude;
    }
    public double getLatitude() {
        return Latitude;
    }

    public void setLongitude(double longitude) {
        this.Longitude = longitude;
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
