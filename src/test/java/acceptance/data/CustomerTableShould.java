package acceptance.data;

import commonStructures.City;
import data.dao.factory.CustomerDAOFactory;
import data.dao.CustomerDAO;
import model.Customer;
import model.submodel.Address;
import model.submodel.FullName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class CustomerTableShould {

    private CustomerDAO dbAccess;
    private Customer jamesBond;

    @BeforeEach
    void setUp() {
        dbAccess = CustomerDAOFactory.createCustomerDAO();
        jamesBond = new Customer();
        jamesBond.setNationalCode("007");
        jamesBond.setFullName(new FullName("James", "Bond"));
        jamesBond.setBirthDate(LocalDate.of(1921, 11, 11));
        jamesBond.setAddress(new Address(City.LONDON, "Queen st.", "007700"));
        jamesBond.setPhoneNumber("+447975777666");

    }

    @Test
    void get_all_customers_from_the_customer_table() {
        List<Customer> allCustomers = dbAccess.getAllRecords();
        assertThat(allCustomers.isEmpty()).isFalse();
        assertThat(allCustomers.get(0)).isEqualTo(jamesBond);
    }

    @Test
    void get_a_specific_customer_from_customer_table() {
        Customer customerByNationalCode = dbAccess.getRecordById("007");
        assertThat(customerByNationalCode).isEqualTo(jamesBond);
    }

    @Test
    void insert_a_new_customer_to_customer_table() {
        Customer newRecord = new Customer();
        newRecord.setNationalCode("456");
        newRecord.setFullName(new FullName("Jason", "Statham"));
        newRecord.setBirthDate(LocalDate.of(1967, 7, 26));
        newRecord.setAddress(new Address(City.LOS_ANGELES, "Century Park East", "CA 900067"));
        newRecord.setPhoneNumber("(310) 477-8442");
        newRecord.setEmail("JasonStatham@mail.com");
        newRecord.setPassword("JStath1967");

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
