package buisnessLayer;

import java.time.LocalDateTime;

public class Order {
    private String id;
    private int quantity;
    private double price;
    private LocalDateTime registrationTime;
    private Customer customerInfo;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.registrationTime = registrationTime;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setCustomerInfo(Customer customerInfo) {
        this.customerInfo = customerInfo;
    }

    public Customer getCustomerInfo() {
        return customerInfo;
    }

    private void calculateOrderPrice() {

    }

    private void registerOrder() {

    }
}
