package utilities;

import com.google.gson.Gson;
import flight.Coordinate;
import flight.Location;

import java.text.DecimalFormat;

public class Converter {

    private Converter() {}

    public static double limitNumberOfDecimalPlaces(double value, int limit) {
        return Double.parseDouble(new DecimalFormat(getDecimalFormatterInput(limit)).format(value));
    }

    private static String getDecimalFormatterInput(int numberOfDecimalPlaces) {
        return "0." + "0".repeat(Math.max(0, numberOfDecimalPlaces));
    }

    public static Coordinate jsonToCoordinate(String jsonCoordinate){
        Gson gsonMapper = new Gson();
        final Coordinate coordinateFromDb = gsonMapper.fromJson(jsonCoordinate, Coordinate.class);
        return coordinateFromDb;
    }

    public static Location jsonToLocation(String jsonLocation){
        Gson gsonMapper = new Gson();
        final Location locationFromDb = gsonMapper.fromJson(jsonLocation, Location.class);
        return locationFromDb;
    }
}
