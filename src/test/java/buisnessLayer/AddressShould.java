package buisnessLayer;

import commonStructures.City;
import org.assertj.core.api.Assertions;
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
        assertThat(address.getCity()).isEqualTo(City.AMSTERDAM);
    }

    @Test
    void have_a_street_name() {
        assertThat(address.getStreet()).isEqualTo("Brouwersgracht");
    }

    @Test
    void have_a_postal_code() {
        assertThat(address.getPostalCode()).isEqualTo("1011 AA");
    }

    @Test
    void change_the_city_name() {
        address.setCity(City.LAS_VEGAS);
        assertThat(address.getCity()).isEqualTo(City.LAS_VEGAS);
    }

    @Test
    void change_the_street_name() {
        address.setStreet("The Strip");
        assertThat(address.getStreet()).isEqualTo("The Strip");
    }

    @Test
    void change_the_postal_code() {
        address.setPostalCode("88901");
        assertThat(address.getPostalCode()).isEqualTo("88901");
    }
}
