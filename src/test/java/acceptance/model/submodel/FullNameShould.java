package acceptance.model.submodel;

import model.submodel.FullName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FullNameShould {
    private FullName fullName;

    @BeforeEach
    void setUp() {
        fullName = new FullName("Brad", "Pitt");
    }

    @Test
    void have_a_first_name() {
        assertThat(fullName.getFirstName()).isEqualTo("Brad");
    }

    @Test
    void have_a_last_name() {
        assertThat(fullName.getLastName()).isEqualTo("Pitt");
    }

    @Test
    void change_the_first_name() {
        fullName.setFirstName("Angelina");
        assertThat(fullName.getFirstName()).isEqualTo("Angelina");
    }

    @Test
    void change_the_last_name() {
        fullName.setLastName("Jolie");
        assertThat(fullName.getLastName()).isEqualTo("Jolie");
    }
}
