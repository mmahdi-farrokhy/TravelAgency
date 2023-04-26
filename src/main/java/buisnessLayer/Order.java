package buisnessLayer;

import applicationLayer.Flight;
import commonStructures.CurrencyType;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private int quantity;
    private Price price;
    private LocalDateTime registrationTime;
    private Customer customerInfo;
    private Flight flight;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
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

    private void registerOrder() {

    }
}
