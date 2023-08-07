package model;

import commonStructures.City;
import data.dao.CustomerDAO;
import data.dao.factory.CustomerDAOFactory;
import model.submodel.Address;
import model.submodel.FullName;

import java.time.LocalDate;
import java.util.Objects;

public class Customer extends DBTable {
    private String nationalCode;
    private FullName fullName;
    private LocalDate birthDate;
    private Address address;
    private String phoneNumber;
    private String email;
    private String password;

    public Customer() {
        this.nationalCode = "";
        this.fullName = null;
        this.birthDate = null;
        this.address = null;
        this.phoneNumber = "";
    }

    public Customer(String nationalCode) {
        CustomerDAO orderTable = CustomerDAOFactory.createCustomerDAO();
        this.nationalCode = nationalCode;
        Customer recordById = orderTable.getRecordById(nationalCode);
        this.fullName = recordById.fullName;
        this.birthDate = recordById.birthDate;
        this.address = recordById.address;
        this.phoneNumber = recordById.phoneNumber;
        this.email = recordById.email;
        this.password = recordById.password;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
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

    public String getRawFullName(){
        return fullName.getFirstName() + " " + fullName.getLastName();
    }

    public String getRawAddress(){
        return address.getCityName().toString() + ", " + address.getStreetName() + ", " + address.getPostalCode();
    }

    public String getCustomerFirstName() {
        return fullName.getFirstName();
    }

    public String getCustomerLastName() {
        return fullName.getLastName();
    }

    public City getCustomerCityName() {
        return address.getCityName();
    }

    public String getCustomerPostalCode() {
        return address.getPostalCode();
    }

    public String getCustomerStreetName() {
        return address.getStreetName();
    }
}
