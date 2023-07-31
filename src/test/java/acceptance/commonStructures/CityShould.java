package acceptance.commonStructures;

import commonStructures.City;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CityShould {

    @Test
    void convert_a_city_to_string() {
        Assertions.assertThat(City.LAS_VEGAS.toString()).isEqualTo("Las Vegas");
        assertThat(City.AMSTERDAM.toString()).isEqualTo("Amsterdam");
        assertThat(City.LOS_ANGELES.toString()).isEqualTo("Los Angeles");
    }
}
