package commonStructures;

import java.io.IOException;

import static buisness.OnlineCurrencyConverter.getCurrenciesRatesAsJSONFromAPI;
import static java.lang.Double.parseDouble;
import static utilities.ConversionUtils.limitNumberOfDecimalPlaces;

public enum CurrencyType {
    NONE,
    USD,
    EUR,
    JPY,
    GBP,
    CNY,
    AUD,
    CAD,
    CHF,
    HKD,
    SGD,
    SEK,
    KRW,
    NOK,
    NZD,
    INR,
    MXN,
    TWD,
    ZAR,
    BRL,
    DKK,
    PLN,
    THB,
    ILS,
    IDR,
    CZK,
    AED,
    TRY,
    HUF,
    CLP,
    SAR,
    PHP,
    MYR,
    COP,
    RUB,
    RON,
    PEN,
    BHD,
    BGN,
    ARS;

    public static final String dollarSign = "$";

    public static String getCurrencySymbol(CurrencyType currencyType) {
        switch (currencyType) {
            case USD, ARS, COP, CLP, MXN, NZD, SGD, HKD, CAD, AUD -> {
                return dollarSign;
            }
            case EUR -> {
                return "\u20AC";
            }
            case JPY, CNY -> {
                return "\u00A5";
            }
            case GBP -> {
                return "\u00A3";
            }
            case CHF -> {
                return "\u20A3";
            }
            case SEK, NOK, DKK -> {
                return "\u006B\u0072";
            }
            case KRW -> {
                return "\u20A9";
            }
            case INR -> {
                return "\u20B9";
            }
            case TWD -> {
                return "\u004E\u0054\u0024";
            }
            case ZAR -> {
                return "R";
            }
            case BRL -> {
                return "\u0052\u0024";
            }
            case PLN -> {
                return "\u007A\u0142";
            }
            case THB -> {
                return "\u0E3F";
            }
            case ILS -> {
                return "\u20AA";
            }
            case IDR -> {
                return "\u0052\u0070";
            }
            case CZK -> {
                return "\u004B\u010D";
            }
            case AED -> {
                return "\u062F\u002E\u0625\u002E";
            }
            case TRY -> {
                return "\u20BA";
            }
            case HUF -> {
                return "\u0046\u0074";
            }
            case SAR -> {
                return "\u0631\u002E\u0633\u002E";
            }
            case PHP -> {
                return "\u20B1";
            }
            case MYR -> {
                return "\u0052\u004D";
            }
            case RUB -> {
                return "\u20BD";
            }
            case RON -> {
                return "\u006C\u0065\u0069";
            }
            case PEN -> {
                return "\u0053\u002F";
            }
            case BHD -> {
                return "\u0628\u002E\u062F";
            }
            case BGN -> {
                return "\u043B\u0432";
            }
        }
        return "";
    }

    public static double convertCurrency(CurrencyType sourceCurrency, CurrencyType targetCurrency, double amount) throws IOException {
        return limitNumberOfDecimalPlaces(amount * getCurrenciesRate(sourceCurrency, targetCurrency), 5);
    }

    private static int getCurrencyIndexInResponse(CurrencyType currency, String jsonResponse) {
        int currencyIndex = jsonResponse.indexOf("\"" + currency + "\":");
        if (currencyIndex == -1) {
            throw new IllegalArgumentException("Currency not found in response: " + currency);
        }
        return currencyIndex;
    }

    private static double getExchangeRateFromResponse(CurrencyType currency, String jsonResponse) {
        int currencyIndex = getCurrencyIndexInResponse(currency, jsonResponse);
        int currencyAmountStartIndex = currencyIndex + 6;
        int currencyAmountEndIndex = jsonResponse.indexOf(",", currencyAmountStartIndex) != -1
                ? jsonResponse.indexOf(",", currencyAmountStartIndex)
                : jsonResponse.indexOf("}", currencyAmountStartIndex);

        return parseDouble(jsonResponse.substring(currencyAmountStartIndex, currencyAmountEndIndex));
    }

    private static double getCurrenciesRate(CurrencyType sourceCurrency, CurrencyType targetCurrency) throws IOException {
        String currenciesRatesAsJSON = getCurrenciesRatesAsJSONFromAPI();
        double sourceRate = getExchangeRateFromResponse(sourceCurrency, currenciesRatesAsJSON);
        double targetRate = getExchangeRateFromResponse(targetCurrency, currenciesRatesAsJSON);
        return limitNumberOfDecimalPlaces(targetRate / sourceRate, 10);
    }
}
