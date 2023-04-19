package flight;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LocationShould {

    @Test
    void have_a_city_and_a_country() {
        Location location = new Location("Tehran", "Iran");
        assertThat(location.getCity()).isEqualTo("Tehran");
        assertThat(location.getCountry()).isEqualTo("Iran");
    }
}
