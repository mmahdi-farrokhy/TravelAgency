package applicationLayer;
import java.util.Objects;

public class Location {
    private String City;
    private String Country;

    public Location(String city, String country) {
        this.City = city;
        this.Country = country;
    }

    public String getCity() {
        return City;
    }

    public String getCountry() {
        return Country;
    }

    @Override
    public String toString() {
        return "Location{" +
                "City='" + City + '\'' +
                ", Country='" + Country + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(City, location.City) && Objects.equals(Country, location.Country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(City, Country);
    }
}
