package model.submodel;

import common.structures.AirportCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderHistoryTableRowShould {
    private OrderHistoryTableRow orderHistoryTableRow;

    @BeforeEach
    void setUp() {
        String orderIdCol = "123";
        String originAirportCol = AirportCode.RUH + ": King Khalid International Airport";
        String destinationAirportCol = AirportCode.MCO + ": Orlando International Airport";
        LocalDate localDateCol = LocalDate.of(2023, 6, 23);
        LocalTime localTimeCol = LocalTime.of(5, 53);
        String quantityCol = "5";
        String priceCol = "230.54";
        orderHistoryTableRow = new OrderHistoryTableRow(orderIdCol, originAirportCol, destinationAirportCol, localDateCol, localTimeCol, quantityCol, priceCol);
    }

    @Test
    void have_a_order_id_column() {
        assertThat(orderHistoryTableRow.getOrderIdCol()).isEqualTo("123");
    }

    @Test
    void have_a_origin_airport_column() {
        assertThat(orderHistoryTableRow.getOriginAirportCol()).isEqualTo("RUH: King Khalid International Airport");
    }

    @Test
    void have_a_destination_airport_column() {
        assertThat(orderHistoryTableRow.getDestinationAirportCol()).isEqualTo("MCO: Orlando International Airport");
    }

    @Test
    void have_a_local_date_column() {
        assertThat(orderHistoryTableRow.getDateCol()).isEqualTo(LocalDate.of(2023,6,23));
    }

    @Test
    void have_a_local_time_column() {
        assertThat(orderHistoryTableRow.getTimeCol()).isEqualTo(LocalTime.of(5,53));
    }

    @Test
    void have_a_quantity_column() {
        assertThat(orderHistoryTableRow.getQuantityCol()).isEqualTo("5");
    }

    @Test
    void have_a_price_column() {
        assertThat(orderHistoryTableRow.getPriceCol()).isEqualTo("230.54");
    }
}
