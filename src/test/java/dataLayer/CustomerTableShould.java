package dataLayer;

import model.submodel.Address;
import model.Customer;
import model.submodel.FullName;
import commonStructures.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class CustomerTableShould {

    private CustomerTable dbAccess;
    private Customer bradPitt;

    @BeforeEach
    void setUp() {
        dbAccess = new CustomerTable();
        bradPitt = new Customer();
        bradPitt.setNationalCode("123");
        bradPitt.setFullName(new FullName("Brad", "Pitt"));
        bradPitt.setBirthDate(LocalDate.of(1963, 12, 18));
        bradPitt.setAddress(new Address(City.LOS_ANGELES, "Wilshire Blvd.", "CA 90212"));
        bradPitt.setPhoneNumber("(310) 275-6135");

    }

    @Test
    void get_all_customers_from_the_customer_table() {
        List<Customer> allCustomers = dbAccess.getAllRecords();
        assertThat(allCustomers.isEmpty()).isFalse();
        assertThat(allCustomers.get(0)).isEqualTo(bradPitt);
    }

    @Test
    void get_a_specific_customer_from_customer_table() {
        Customer customerByNationalCode = dbAccess.getRecordById("123");
        assertThat(customerByNationalCode).isEqualTo(bradPitt);
    }

    @Test
    void insert_a_new_customer_to_customer_table() {
        Customer newRecord = new Customer();
        newRecord.setNationalCode("456");
        newRecord.setFullName(new FullName("Jason", "Statham"));
        newRecord.setBirthDate(LocalDate.of(1967, 7, 26));
        newRecord.setAddress(new Address(City.LOS_ANGELES, "Century Park East", "CA 900067"));
        newRecord.setPhoneNumber("(310) 477-8442");

        assertThat(dbAccess.insertNewRecord(newRecord)).isTrue();
        assertThat(dbAccess.insertNewRecord(newRecord)).isFalse();
    }

    @Test
    void delete_a_customer_from_customer_table() {
        dbAccess.deleteRecordById("456");
        assertThatExceptionOfType(java.lang.RuntimeException.class)
                .isThrownBy(() -> dbAccess.getRecordById("456"));
    }
}
