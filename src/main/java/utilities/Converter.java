package utilities;

import java.text.DecimalFormat;

public class Converter {

    private Converter() {}

    public static double limitNumberOfDecimalPlaces(double value, int limit) {
        return Double.parseDouble(new DecimalFormat(getDecimalFormatterInput(limit)).format(value));
    }

    private static String getDecimalFormatterInput(int numberOfDecimalPlaces) {
        return "0." + "0".repeat(Math.max(0, numberOfDecimalPlaces));
    }
}
