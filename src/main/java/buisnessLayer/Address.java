package buisnessLayer;

import commonStructures.Properties;

import java.util.Objects;

public class Address extends Properties {
    private String cityName;
    private String streetName;
    private String postalCode;

    public Address(String city, String street, String postalCode) {
        this.cityName = city;
        this.streetName = street;
        this.postalCode = postalCode;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String toJson() {
        return "{\"cityName\" : \"" + cityName + "\", " +
                "\"streetName\" : \"" + streetName + "\", " +
                "\"postalCode\" : \"" + postalCode + "\" }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(cityName, address.cityName) && Objects.equals(streetName, address.streetName) && Objects.equals(postalCode, address.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityName, streetName, postalCode);
    }

    @Override
    public String toString() {
        return "Address{" +
                "cityName='" + cityName + '\'' +
                ", streetName='" + streetName + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
