package model.submodel;

import commonstructures.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressShould {
    private Address address;

    @BeforeEach
    void setUp() {
        address = new Address(City.AMSTERDAM, "Brouwersgracht", "1011 AA");
    }

    @Test
    void have_a_city_name() {
        assertThat(address.getCityName().toString()).isEqualTo("Amsterdam");
    }

    @Test
    void have_a_street_name() {
        assertThat(address.getStreetName()).isEqualTo("Brouwersgracht");
    }

    @Test
    void have_a_postal_code() {
        assertThat(address.getPostalCode()).isEqualTo("1011 AA");
    }

    @Test
    void change_the_city_name() {
        address.setCityName(City.LAS_VEGAS);
        assertThat(address.getCityName().toString()).isEqualTo("Las Vegas");
    }

    @Test
    void change_the_street_name() {
        address.setStreetName("The Strip");
        assertThat(address.getStreetName()).isEqualTo("The Strip");
    }

    @Test
    void change_the_postal_code() {
        address.setPostalCode("88901");
        assertThat(address.getPostalCode()).isEqualTo("88901");
    }
}
