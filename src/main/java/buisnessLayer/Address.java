package buisnessLayer;

import commonStructures.City;

public class Address {
    private City city;
    private String street;
    String postalCode;

    public Address(City city, String street, String postalCode) {
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public City getCity() {
        return city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPostalCode() {
        return postalCode;
    }
}
