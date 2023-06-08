package buisnessLayer;

import commonStructures.CurrencyType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.stream.Collectors;

import static java.lang.Double.parseDouble;
import static utilities.WebUtils.connectToAPI;
import static utilities.ConvertUtils.limitNumberOfDecimalPlaces;

public class CurrencyConverter {

    private static final String API_ENDPOINT = "https://openexchangerates.org/api/latest.json";
    private static final String API_KEY = "7b1e7a14d08d4811bba653af4999170f";

    public static double convertCurrency(CurrencyType sourceCurrency, CurrencyType targetCurrency, double amount) {
        return limitNumberOfDecimalPlaces(amount * getCurrenciesRate(sourceCurrency, targetCurrency), 5);
    }

    public static double getCurrenciesRate(CurrencyType sourceCurrency, CurrencyType targetCurrency) {
        String response = getResponseFromAPI();
        double sourceRate = getExchangeRateFromResponse(response, sourceCurrency);
        double targetRate = getExchangeRateFromResponse(response, targetCurrency);
        return limitNumberOfDecimalPlaces(targetRate / sourceRate, 10);
    }

    private static String getResponseFromAPI() {
        try {
            HttpURLConnection connection = connectToAPI(API_ENDPOINT + "?app_id=" + API_KEY);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = reader.lines().collect(Collectors.joining());
            reader.close();
            connection.disconnect();
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static double getExchangeRateFromResponse(String jsonResponse, CurrencyType currency) {
        int currencyIndex = getCurrencyIndexInResponse(jsonResponse, currency);
        int amountStartIndex = currencyIndex + 6;
        int amountEndIndex = jsonResponse.indexOf(",", amountStartIndex) != -1
                ? jsonResponse.indexOf(",", amountStartIndex)
                : jsonResponse.indexOf("}", amountStartIndex);

        return parseDouble(jsonResponse.substring(amountStartIndex, amountEndIndex));
    }

    private static int getCurrencyIndexInResponse(String jsonResponse, CurrencyType currency) {
        int currencyIndex = jsonResponse.indexOf("\"" + currency + "\":");
        if (currencyIndex == -1) {
            throw new IllegalArgumentException("Currency not found in response: " + currency);
        }
        return currencyIndex;
    }
}