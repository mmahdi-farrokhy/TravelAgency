package utilities;

import flight.Coordinate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DistanceCalculatorShould {

    private DistanceCalculator distanceCalculator;

    @BeforeEach
    void setUp() {
        distanceCalculator = new DistanceCalculator();
    }

    @Test
    void calculate_distance_of_two_coordinates() {
        Coordinate aPoint = new Coordinate(21.0417, 86.8740);
        Coordinate bPoint = new Coordinate(28.1967, 113.2208);

        double distance = distanceCalculator.calculateDistance(aPoint, bPoint);

        assertThat(distance).isEqualTo(14121);
    }
}
