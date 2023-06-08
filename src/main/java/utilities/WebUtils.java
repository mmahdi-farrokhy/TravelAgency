package utilities;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebUtils {
    public static HttpURLConnection connectToAPI(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }
}
