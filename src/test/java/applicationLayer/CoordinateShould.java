package applicationLayer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CoordinateShould {

    @Test
    void have_a_latitude_and_a_longitude() {
        Coordinate coordinate = new Coordinate(21.0417, 86.8740);

        assertThat(coordinate.getLatitude()).isEqualTo(21.0417);
        assertThat(coordinate.getLongitude()).isEqualTo(86.8740);
    }
}
