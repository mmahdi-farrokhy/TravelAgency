package buisnessLayer;

import commonStructures.DBTable;
import dataLayer.CustomerTable;
import dataLayer.OrderTable;

import java.time.LocalDate;
import java.util.Objects;

public class Customer extends DBTable {
    public String nationalCode;
    private FullName fullName;
    private LocalDate birthDate;
    private Address address;
    private String phoneNumber;

    public Customer() {
        this.nationalCode = "";
        this.fullName = null;
        this.birthDate = null;
        this.address = null;
        this.phoneNumber = "";
    }

    public Customer(String nationalCode) {
        CustomerTable orderTable = new CustomerTable();
        this.nationalCode = nationalCode;
        Customer recordById = orderTable.getRecordById(nationalCode);
        this.fullName = recordById.fullName;
        this.birthDate = recordById.birthDate;
        this.address = recordById.address;
        this.phoneNumber = recordById.phoneNumber;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setFullName(FullName fullName) {
        this.fullName = fullName;
    }

    public FullName getFullName() {
        return fullName;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(nationalCode, customer.nationalCode) && Objects.equals(fullName, customer.fullName) && Objects.equals(birthDate, customer.birthDate) && Objects.equals(address, customer.address) && Objects.equals(phoneNumber, customer.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nationalCode, fullName, birthDate, address, phoneNumber);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "nationalCode='" + nationalCode + '\'' +
                ", fullName=" + fullName +
                ", birthDate=" + birthDate +
                ", address=" + address +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
