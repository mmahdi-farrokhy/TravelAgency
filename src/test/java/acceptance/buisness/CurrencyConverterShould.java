package acceptance.buisness;

import commonStructures.CurrencyType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyConverterShould {
    @Test
    void convert_amount_of_price_into_another_currency() {
        try {
            assertThat(CurrencyType.convertCurrency(CurrencyType.USD, CurrencyType.EUR, 100)).isEqualTo(90.80440);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            assertThat(CurrencyType.convertCurrency(CurrencyType.ARS, CurrencyType.EUR, 100)).isEqualTo(0.40246);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            assertThat(CurrencyType.convertCurrency(CurrencyType.BGN, CurrencyType.BHD, 100)).isEqualTo(21.23384);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
