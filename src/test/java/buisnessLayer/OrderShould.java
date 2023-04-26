package buisnessLayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderShould {
    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
    }

    @Test
    void have_an_id() {
        order.setId(123);
        assertThat(order.getId()).isEqualTo(123);
    }

    @Test
    void have_a_quantity() {
        order.setQuantity(5);
        assertThat(order.getQuantity()).isEqualTo(5);
    }

    @Test
    void have_a_price() {
        order.calculateOrderPriceAmountByUSD();
        assertThat(order.getQuantity()).isEqualTo(5);
    }
}
