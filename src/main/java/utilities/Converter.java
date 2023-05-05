package utilities;

import com.google.gson.Gson;
import commonStructures.Properties;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import static java.lang.Double.parseDouble;

public class Converter {

    private Converter() {
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

    public static HttpURLConnection connectToAPI(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }

}