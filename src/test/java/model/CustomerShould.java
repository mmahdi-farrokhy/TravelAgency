package model;

import common.structures.City;
import model.submodel.Address;
import model.submodel.FullName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerShould {
    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
    }

    @Test
    void have_a_national_code() {
        customer.setNationalCode("1234567890");
        assertThat(customer.getNationalCode()).isEqualTo("1234567890");
    }

    @Test
    void have_a_name() {
        customer.setFullName(new FullName("Brad", "Pitt"));
        assertThat(customer.getFullName().getFirstName()).isEqualTo("Brad");
        assertThat(customer.getFullName().getLastName()).isEqualTo("Pitt");
    }

    @Test
    void have_a_birth_date() {
        LocalDate birthDate = LocalDate.of(1963, 12, 18);
        customer.setBirthDate(birthDate);
        assertThat(customer.getBirthDate().getMonth().toString().toLowerCase()).isEqualTo("december");
        assertThat(customer.getBirthDate().getMonth().getValue()).isEqualTo(12);
        assertThat(customer.getBirthDate().getYear()).isEqualTo(1963);
        assertThat(customer.getBirthDate().getDayOfMonth()).isEqualTo(18);
    }

    @Test
    void have_an_address() {
        customer.setAddress(new Address(City.LAS_VEGAS, "The Strip", "88901"));
        assertThat(customer.getAddress().getCityName().toString()).isEqualTo("Las Vegas");
        assertThat(customer.getAddress().getStreetName()).isEqualTo("The Strip");
        assertThat(customer.getAddress().getPostalCode()).isEqualTo("88901");
    }

    @Test
    void have_a_phone_number() {
        customer.setPhoneNumber("+989190763415");
        assertThat(customer.getPhoneNumber()).isEqualTo("+989190763415");
    }
}
