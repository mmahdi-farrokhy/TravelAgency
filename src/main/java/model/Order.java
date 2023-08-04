package model;

import commonStructures.CurrencyType;
import model.submodel.Price;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.IntStream;

public class Order extends DBTable {
    public static final int[] FLIGHT_DISTANCES =
            new int[]{10000, 7500, 6000, 4500, 3500, 2500, 2000, 1600, 1200, 800, 600, 400};
    public static final double[] FLIGHT_PRICES =
            new double[]{1215.65, 1061.07, 794.98, 703.33, 581.88, 478.09, 378.72, 325.72, 253.95, 244.01, 230.76, 216.41};
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

    public void setPrice(Price price) {
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
        double flightDistance = this.flight.estimateFlightDistance();
        price = new Price(calculatePriceAmount(flightDistance), CurrencyType.USD);
    }

    private static double calculatePriceAmount(double flightDistance) {
        return IntStream.range(0, FLIGHT_DISTANCES.length)
                .filter(flightIndex -> flightDistance > FLIGHT_DISTANCES[flightIndex])
                .mapToDouble(priceIndex -> FLIGHT_PRICES[priceIndex])
                .findFirst().orElse(0);
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

    public CurrencyType getOrderPriceCurrency() {
        return price.getCurrency();
    }

    public double getOrderPriceAmount() {
        return price.getAmount();
    }

    public String getOrderCustomerNationalCode() {
        return customerInfo.getNationalCode();
    }

    public String getOrderFlightId() {
        return flight.getId();
    }
}
