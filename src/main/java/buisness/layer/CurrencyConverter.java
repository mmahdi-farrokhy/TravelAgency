package buisness.layer;

import common.structures.CurrencyType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.stream.Collectors;

import static java.lang.Double.parseDouble;
import static utilities.ConversionUtils.limitNumberOfDecimalPlaces;
import static utilities.WebUtils.connectToAPI;

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

    private static double getExchangeRateFromResponse(String jsonResponse, CurrencyType currency) {
        int currencyIndex = getCurrencyIndexInResponse(jsonResponse, currency);
        int currencyAmountStartIndex = currencyIndex + 6;
        int currencyAmountEndIndex = jsonResponse.indexOf(",", currencyAmountStartIndex) != -1
                ? jsonResponse.indexOf(",", currencyAmountStartIndex)
                : jsonResponse.indexOf("}", currencyAmountStartIndex);

        return parseDouble(jsonResponse.substring(currencyAmountStartIndex, currencyAmountEndIndex));
    }

    private static int getCurrencyIndexInResponse(String jsonResponse, CurrencyType currency) {
        int currencyIndex = jsonResponse.indexOf("\"" + currency + "\":");
        if (currencyIndex == -1) {
            throw new IllegalArgumentException("Currency not found in response: " + currency);
        }
        return currencyIndex;
    }
}