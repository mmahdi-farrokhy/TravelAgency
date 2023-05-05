package buisnessLayer.currencyConversion;

import commonStructures.CurrencyType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyConverterShould {

    @Test
    void calculate_the_ratio_of_the_price_of_the_second_currency_to_the_first() {
        assertThat(CurrencyConverter.getCurrenciesRate(CurrencyType.USD, CurrencyType.EUR)).isEqualTo(0.908044);
        assertThat(CurrencyConverter.getCurrenciesRate(CurrencyType.ARS, CurrencyType.EUR)).isEqualTo(0.0040245932);
        assertThat(CurrencyConverter.getCurrenciesRate(CurrencyType.BGN, CurrencyType.BHD)).isEqualTo(0.2123383904);
    }

    @Test
    void convert_amount_of_price_into_another_currency() {
        assertThat(CurrencyConverter.convertCurrency(CurrencyType.USD, CurrencyType.EUR, 100)).isEqualTo(90.80440);
        assertThat(CurrencyConverter.convertCurrency(CurrencyType.ARS, CurrencyType.EUR, 100)).isEqualTo(0.40246);
        assertThat(CurrencyConverter.convertCurrency(CurrencyType.BGN, CurrencyType.BHD, 100)).isEqualTo(21.23384);
    }
}
