package utilities;

import com.google.gson.Gson;
import commonStructures.Properties;

import java.lang.reflect.Type;
import java.text.DecimalFormat;

import static java.lang.Double.parseDouble;

public class ConversionUtils {

    private ConversionUtils() {
    }

    public static double limitNumberOfDecimalPlaces(double value, int limit) {
        return parseDouble(new DecimalFormat(getDecimalFormatterInput(limit)).format(value));
    }

    private static String getDecimalFormatterInput(int numberOfDecimalPlaces) {
        return "0." + "0".repeat(Math.max(0, numberOfDecimalPlaces));
    }

    public static <T extends Properties> T jsonToProperty(String jsonProperty, T type) {
        return new Gson().fromJson(jsonProperty, (Type) type.getClass());
    }
}