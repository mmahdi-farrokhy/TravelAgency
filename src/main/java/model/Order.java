package model;

import commonStructures.CurrencyType;
import model.submodel.Price;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order extends DBTable {
    private String id;
    private int quantity;
    private Price price;
    private LocalDateTime registrationTime;
    private Customer customerInfo;
    private Flight flight;

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

    public void setPrice(Price price){
        this.price = price;
    }
    public Price getPrice() {
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

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Flight getFlight() {
        return flight;
    }

    public void calculateOrderPriceAmountByUSD() {
        double flightDistance = flight.estimateFlightDistance();
        double priceAmount = 0;
        CurrencyType priceCurrency = CurrencyType.USD;

        if (flightDistance >= 400 && flightDistance <= 600)
            priceAmount = 216.41;
        if (flightDistance >= 601 && flightDistance <= 800)
            priceAmount = 230.76;
        if (flightDistance >= 801 && flightDistance <= 1200)
            priceAmount = 244.01;
        if (flightDistance >= 1201 && flightDistance <= 1600)
            priceAmount = 253.95;
        if (flightDistance >= 1601 && flightDistance <= 2000)
            priceAmount = 325.72;
        if (flightDistance >= 2001 && flightDistance <= 2500)
            priceAmount = 378.72;
        if (flightDistance >= 2501 && flightDistance <= 3500)
            priceAmount = 478.09;
        if (flightDistance >= 3501 && flightDistance <= 4500)
            priceAmount = 581.88;
        if (flightDistance >= 4501 && flightDistance <= 6000)
            priceAmount = 703.33;
        if (flightDistance >= 6001 && flightDistance <= 7500)
            priceAmount = 794.98;
        if (flightDistance >= 7501 && flightDistance <= 10000)
            priceAmount = 1061.07;
        if (flightDistance >= 10001)
            priceAmount = 1215.65;

        price = new Price(priceAmount, priceCurrency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return quantity == order.quantity && Objects.equals(price, order.price) && Objects.equals(customerInfo, order.customerInfo) && Objects.equals(flight, order.flight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, price, registrationTime, customerInfo, flight);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", registrationTime=" + registrationTime +
                ", customerInfo=" + customerInfo +
                ", flight=" + flight +
                '}';
    }

    public CurrencyType getCurrency() {
        return price.getCurrency();
    }

    public double getAmount() {
        return price.getAmount();
    }
}
