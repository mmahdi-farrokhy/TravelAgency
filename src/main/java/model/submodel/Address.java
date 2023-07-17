package model.submodel;

import commonstructures.City;

import java.util.Objects;

public class Address {
    private City cityName;
    private String streetName;
    private String postalCode;

    public Address(City city, String street, String postalCode) {
        this.cityName = city;
        this.streetName = street;
        this.postalCode = postalCode;
    }

    public void setCityName(City cityName) {
        this.cityName = cityName;
    }

    public City getCityName() {
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

    public String getRawAddress(){
        return cityName.toString() + ", " + streetName + ", " + postalCode;
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
