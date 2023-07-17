package commonstructures;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyTypeShould {

    @Test
    void get_a_currency_symbol_by_its_type() {
        assertThat(CurrencyType.getCurrencySymbol(CurrencyType.USD)).isEqualTo("$");
        assertThat(CurrencyType.getCurrencySymbol(CurrencyType.EUR)).isEqualTo("€");
        assertThat(CurrencyType.getCurrencySymbol(CurrencyType.RON)).isEqualTo("lei");
    }
}
