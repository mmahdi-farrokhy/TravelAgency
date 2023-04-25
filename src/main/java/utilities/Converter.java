package utilities;

import com.google.gson.Gson;
import applicationLayer.Coordinate;
import applicationLayer.Location;

import java.text.DecimalFormat;

import static java.lang.Double.parseDouble;

public class Converter {

    private Converter() {}

    public static double limitNumberOfDecimalPlaces(double value, int limit) {
        return parseDouble(new DecimalFormat(getDecimalFormatterInput(limit)).format(value));
    }

    private static String getDecimalFormatterInput(int numberOfDecimalPlaces) {
        return "0." + "0".repeat(Math.max(0, numberOfDecimalPlaces));
    }

    public static Coordinate jsonToCoordinate(String jsonCoordinate){
        return new Gson().fromJson(jsonCoordinate, Coordinate.class);
    }

    public static Location jsonToLocation(String jsonLocation){
        return new Gson().fromJson(jsonLocation, Location.class);
    }
}
