package pt.ua.airquality.connection;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class OpenWeatherMapGeocodingClient {

    private final static String BASEURL = "http://api.openweathermap.org/geo/1.0/direct?q=%s&appid=%s";
    private final static String APIKEY="1c428b4c88b618053ff3e686f3b49ed6";

    public double[] getLatLonfromCity(String city) throws IOException {
        String SUrl = String.format(BASEURL,city,APIKEY);
        // Connect to the URL using java's native library
        URL url = new URL(SUrl);
        URLConnection request = url.openConnection();
        request.connect();

        // Convert to a JSON object to print data
        //from gson
        JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
        JsonArray rootobj = root.getAsJsonArray(); //May be an array, may be an object.
        double lat = rootobj.get(0).getAsJsonObject().get("lat").getAsDouble();
        double lon = rootobj.get(0).getAsJsonObject().get("lon").getAsDouble();
        return new double[]{lat,lon};
    }
}
