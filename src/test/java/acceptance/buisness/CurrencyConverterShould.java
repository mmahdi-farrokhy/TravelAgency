package acceptance.buisness;

import commonStructures.CurrencyType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyConverterShould {

    @Test
    void calculate_the_ratio_of_the_price_of_the_second_currency_to_the_first() {
        Assertions.assertThat(CurrencyType.getCurrenciesRate(CurrencyType.USD, CurrencyType.EUR)).isEqualTo(0.908044);
        Assertions.assertThat(CurrencyType.getCurrenciesRate(CurrencyType.ARS, CurrencyType.EUR)).isEqualTo(0.0040245932);
        Assertions.assertThat(CurrencyType.getCurrenciesRate(CurrencyType.BGN, CurrencyType.BHD)).isEqualTo(0.2123383904);
    }

    @Test
    void convert_amount_of_price_into_another_currency() {
        Assertions.assertThat(CurrencyType.convertCurrency(CurrencyType.USD, CurrencyType.EUR, 100)).isEqualTo(90.80440);
        Assertions.assertThat(CurrencyType.convertCurrency(CurrencyType.ARS, CurrencyType.EUR, 100)).isEqualTo(0.40246);
        Assertions.assertThat(CurrencyType.convertCurrency(CurrencyType.BGN, CurrencyType.BHD, 100)).isEqualTo(21.23384);
    }
}
