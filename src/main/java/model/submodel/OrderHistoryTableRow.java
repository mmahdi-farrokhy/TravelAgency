package model.submodel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class OrderHistoryTableRow {
    private String orderIdCol;
    private String originAirportCol;
    private String destinationAirportCol;
    private LocalDate dateCol;
    private LocalTime timeCol;
    private String quantityCol;
    private String priceCol;

    public OrderHistoryTableRow(String id, String originAirport, String destinationAirport, LocalDate date, LocalTime time, String quantity, String price) {
        this.orderIdCol = id;
        this.originAirportCol = originAirport;
        this.destinationAirportCol = destinationAirport;
        this.dateCol = date;
        this.timeCol = time;
        this.quantityCol = quantity;
        this.priceCol = price;
    }

    public String getOrderIdCol() {
        return orderIdCol;
    }

    public String getOriginAirportCol() {
        return originAirportCol;
    }

    public String getDestinationAirportCol() {
        return destinationAirportCol;
    }

    public LocalDate getDateCol() {
        return dateCol;
    }

    public LocalTime getTimeCol() {
        return timeCol;
    }

    public String getQuantityCol() {
        return quantityCol;
    }

    public String getPriceCol() {
        return priceCol;
    }
}
