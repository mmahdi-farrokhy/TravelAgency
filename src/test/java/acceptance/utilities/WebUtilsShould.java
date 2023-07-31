package acceptance.utilities;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import utilities.WebUtils;

public class WebUtilsShould {
    private static final String API_ENDPOINT = "https://openexchangerates.org/api/latest.json";
    private static final String API_KEY = "7b1e7a14d08d4811bba653af4999170f";

    @Test
    void connects_to_an_api_without_problem() {
        Assertions.assertThatCode(() -> WebUtils.connectToAPI(API_ENDPOINT + "?app_id=" + API_KEY)).doesNotThrowAnyException();
    }
}
