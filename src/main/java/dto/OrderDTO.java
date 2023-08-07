package dto;

import commonStructures.CurrencyType;
import model.Customer;
import model.DBTable;
import model.Flight;
import model.Order;
import model.submodel.Price;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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

    public OrderDTO() {
        this.id = "";
        this.quantity = 0;
        this.price = new Price(0, CurrencyType.NONE);
        this.registrationTime = LocalDateTime.of(1, 1, 1, 1, 1, 1);
        this.customerInfo = new Customer();
        this.flight = new Flight();
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

    public static List<Order> flightListFromFlightDTOList(List<OrderDTO> orderDTOList) {
        List<Order> orderList = new LinkedList<>();

        for (OrderDTO orderDTO : orderDTOList)
            orderList.add(orderDTO.convertOrderDTOToOrder());

        return orderList;
    }

    public Order convertOrderDTOToOrder() {
        return new Order(
                getId(),
                getQuantity(),
                getPrice(),
                getRegistrationTime(),
                getCustomerInfo(),
                getFlight());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return quantity == orderDTO.quantity && Objects.equals(price, orderDTO.price)  && Objects.equals(customerInfo, orderDTO.customerInfo) && Objects.equals(flight, orderDTO.flight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, price, registrationTime, customerInfo, flight);
    }
}
