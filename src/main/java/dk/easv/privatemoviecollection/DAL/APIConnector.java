package dk.easv.privatemoviecollection.DAL;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class APIConnector {
    private static final String PROP_FILE = "config/api.properties";
    private static String tmdbApiKey;

    public APIConnector() throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(PROP_FILE)) {
            props.load(fis);
            tmdbApiKey = props.getProperty("TMDB_API_KEY");
        }
    }

    /**
     * Request data in Json-format from the TMDB-api
     * @param requestString the string with the api request
     * @return a string with the received json-data
     */
    public static String getJsonFromApi(String requestString){
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestString))
                .header("Authorization", "Bearer " + tmdbApiKey)
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        return response.body();
    }
}
