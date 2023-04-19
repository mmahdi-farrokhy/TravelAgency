package utilities;

import java.text.DecimalFormat;

public class Converter {

    private Converter() {
    }

    public static double limitNumberOfDecimalPlaces(double value, int limit) {
        return Double.valueOf(new DecimalFormat(getDecimalFormatterInput(limit)).format(value));
    }

    private static String getDecimalFormatterInput(int numberOfDecimalPlaces) {
        String decimalFormatterInput = "0.";

        for (int decimalPlaceNumber = 0; decimalPlaceNumber < numberOfDecimalPlaces; decimalPlaceNumber++)
            decimalFormatterInput += "0";

        return decimalFormatterInput;
    }
}
