package buisness;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.stream.Collectors;

import static utilities.WebUtils.connectToAPI;

public class CurrencyConverter {

    private static final String API_ENDPOINT = "https://openexchangerates.org/api/latest.json";
    private static final String API_KEY = "7b1e7a14d08d4811bba653af4999170f";

    public static String getCurrenciesRatesAsJSONFromAPI() {
        try {
            HttpURLConnection connectionToAPI = connectToAPI(API_ENDPOINT + "?app_id=" + API_KEY);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connectionToAPI.getInputStream()));
            String jsonResponse = reader.lines().collect(Collectors.joining());
            reader.close();
            connectionToAPI.disconnect();
            return jsonResponse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}