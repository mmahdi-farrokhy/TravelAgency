package acceptance.utilities;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import utilities.ConversionUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class ConversionUtilsShould {

    @Test
    void limit_number_of_decimal_places_of_a_double_number() {
        Assertions.assertThat(ConversionUtils.limitNumberOfDecimalPlaces(123.123456, 1)).isEqualTo(123.1);
        assertThat(ConversionUtils.limitNumberOfDecimalPlaces(123.123456, 2)).isEqualTo(123.12);
        assertThat(ConversionUtils.limitNumberOfDecimalPlaces(123.123456, 3)).isEqualTo(123.123);
        assertThat(ConversionUtils.limitNumberOfDecimalPlaces(123.123456, 4)).isEqualTo(123.1235);
        assertThat(ConversionUtils.limitNumberOfDecimalPlaces(123.123456, 5)).isEqualTo(123.12346);
        assertThat(ConversionUtils.limitNumberOfDecimalPlaces(123.123456, 6)).isEqualTo(123.123456);
    }
}
