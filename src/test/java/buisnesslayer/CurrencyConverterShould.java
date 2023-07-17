package buisnesslayer;

import commonstructures.CurrencyType;
import org.junit.jupiter.api.Test;

import static buisnesslayer.CurrencyConverter.convertCurrency;
import static buisnesslayer.CurrencyConverter.getCurrenciesRate;
import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyConverterShould {

    @Test
    void calculate_the_ratio_of_the_price_of_the_second_currency_to_the_first() {
        assertThat(getCurrenciesRate(CurrencyType.USD, CurrencyType.EUR)).isEqualTo(0.908044);
        assertThat(getCurrenciesRate(CurrencyType.ARS, CurrencyType.EUR)).isEqualTo(0.0040245932);
        assertThat(getCurrenciesRate(CurrencyType.BGN, CurrencyType.BHD)).isEqualTo(0.2123383904);
    }

    @Test
    void convert_amount_of_price_into_another_currency() {
        assertThat(convertCurrency(CurrencyType.USD, CurrencyType.EUR, 100)).isEqualTo(90.80440);
        assertThat(convertCurrency(CurrencyType.ARS, CurrencyType.EUR, 100)).isEqualTo(0.40246);
        assertThat(convertCurrency(CurrencyType.BGN, CurrencyType.BHD, 100)).isEqualTo(21.23384);
    }
}
