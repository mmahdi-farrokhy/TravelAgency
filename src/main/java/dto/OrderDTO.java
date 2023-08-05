package dto;

import model.Customer;
import model.DBTable;
import model.Flight;
import model.submodel.Price;

import java.time.LocalDateTime;

public class OrderDTO extends DBTable {
    private String id;
    private int quantity;
    private Price price;
    private LocalDateTime registrationTime;
    private Customer customerInfo;
    private Flight flight;

    public OrderDTO(String id, int quantity, Price price, LocalDateTime registrationTime, Customer customerInfo, Flight flight) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.registrationTime = registrationTime;
        this.customerInfo = customerInfo;
        this.flight = flight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.registrationTime = registrationTime;
    }

    public Customer getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(Customer customerInfo) {
        this.customerInfo = customerInfo;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}
