package utilities;

import commonStructures.CurrencyType;
import org.junit.jupiter.api.Test;

public class CurrencyConverterShould {

    @Test
    void convert_currencies_using_a_web_server() {
        CurrencyConverter currencyConverter = new CurrencyConverter();

        currencyConverter.setCurrencyType(CurrencyType.USD);

    }
}
