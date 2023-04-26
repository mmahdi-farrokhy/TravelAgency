package buisnessLayer;

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
        assertThat(fullName.getFirstname()).isEqualTo("Brad");
    }

    @Test
    void have_a_last_name() {
        assertThat(fullName.getLastname()).isEqualTo("Pitt");
    }

    @Test
    void change_the_first_name() {
        fullName.setFirstname("Angelina");
        assertThat(fullName.getFirstname()).isEqualTo("Angelina");
    }

    @Test
    void change_the_last_name() {
        fullName.setLastname("Jolie");
        assertThat(fullName.getLastname()).isEqualTo("Jolie");
    }
}
