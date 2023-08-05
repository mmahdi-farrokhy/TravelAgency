package dto;

import model.DBTable;
import model.submodel.Address;
import model.submodel.FullName;

import java.time.LocalDate;

public class CustomerDTO extends DBTable {
    private String nationalCode;
    private FullName fullName;
    private LocalDate birthDate;
    private Address address;
    private String phoneNumber;
    private String email;
    private String password;

    public CustomerDTO(String nationalCode, FullName fullName, LocalDate birthDate, Address address, String phoneNumber, String email, String password) {
        this.nationalCode = nationalCode;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public FullName getFullName() {
        return fullName;
    }

    public void setFullName(FullName fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
