package acceptance.model.submodel;

import commonStructures.CurrencyType;
import model.submodel.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PriceShould {
    private Price price;

    @BeforeEach
    void setUp() {
        price = new Price(120, CurrencyType.ARS);
    }

    @Test
    void have_an_amount() {
        assertThat(price.getAmount()).isEqualTo(120);
    }

    @Test
    void have_a_currency() {
        assertThat(price.getCurrency()).isEqualTo(CurrencyType.ARS);
    }
}
