package pt.ua.airquality.connection;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import pt.ua.airquality.entities.AirQuality;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class OpenWeatherMapAirPollutionClient implements ISimpleAPIClient{
    private final static String baseurlToday="http://api.openweathermap.org/data/2.5/air_pollution?lat=%f&lon=%f&appid=%s";
    private final static String baseurlForecast="http://api.openweathermap.org/data/2.5/air_pollution/forecast?lat=%f&lon=%f&appid=%s";
    private final static String baseurlHistoric="http://api.openweathermap.org/data/2.5/air_pollution/history?lat=%f&lon=%f&start=%d&end=%d&appid=%s";
    private final static String APIKEY="1c428b4c88b618053ff3e686f3b49ed6";
    private final static String COMPONENTS="components";

    OpenWeatherMapGeocodingClient geocodingClient = new OpenWeatherMapGeocodingClient();

    @Override
    public AirQuality getToday(String city) {
        double[] latlon=null;
        try {
            latlon = geocodingClient.getLatLonfromCity(city);
        }catch (IOException e){
            return null;
        }
        double lat = latlon[0];
        double lon = latlon[1];
        String SUrl = String.format(baseurlToday,lat,lon,APIKEY);
        try {
            URL url = new URL(SUrl);
            URLConnection request = url.openConnection();
            request.connect();

            // Convert to a JSON object to print data
            //from gson
            JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
            JsonArray aqlist = rootobj.get("list").getAsJsonArray();
            JsonObject components = aqlist.get(0).getAsJsonObject().get(COMPONENTS).getAsJsonObject();
            double no2 = components.get("no2").getAsDouble();
            double o3 = components.get("o3").getAsDouble();
            double so2 = components.get("so2").getAsDouble();
            double pm2_5 = components.get("pm2_5").getAsDouble();
            double pm10 = components.get("pm10").getAsDouble();
            AirQuality aq = new AirQuality();
            aq.setLat(lat);
            aq.setLon(lon);
            aq.setSo2(so2);
            aq.setPm10(pm10);
            aq.setO3(o3);
            Date d = new Date();
            aq.setDate(new Date(d.getYear(),d.getMonth(),d.getDate()));
            aq.setPm2_5(pm2_5);
            aq.setNo2(no2);
            aq.setCity(city);
            return aq;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public AirQuality getForecast(String city) {
        double[] latlon=null;
        try {
            latlon = geocodingClient.getLatLonfromCity(city);
        }catch (IOException e){
            return null;
        }
        double lat = latlon[0];
        double lon = latlon[1];
        String SUrl = String.format(baseurlForecast,lat,lon,APIKEY);
        try {
            URL url = new URL(SUrl);
            URLConnection request = url.openConnection();
            request.connect();

            // Convert to a JSON object to print data
            //from gson
            JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
            JsonArray aqlist = rootobj.get("list").getAsJsonArray();
            JsonObject components = null;
            Date today = new Date();
            long dateunix = (new Date(today.getYear(),today.getMonth(),today.getDate())).getTime()/1000;
            System.out.println(dateunix);
            for (JsonElement c : aqlist) {
                int dt = c.getAsJsonObject().get("dt").getAsInt();
                if (dt-dateunix>60*60*24){
                    components = c.getAsJsonObject().get(COMPONENTS).getAsJsonObject();
                    break;
                }
            }
            if (components == null){
                return null;
            }
            double no2 = components.get("no2").getAsDouble();
            double o3 = components.get("o3").getAsDouble();
            double so2 = components.get("so2").getAsDouble();
            double pm2_5 = components.get("pm2_5").getAsDouble();
            double pm10 = components.get("pm10").getAsDouble();
            AirQuality aq = new AirQuality();
            aq.setLat(lat);
            aq.setLon(lon);
            aq.setSo2(so2);
            aq.setPm10(pm10);
            aq.setO3(o3);
            aq.setDate(new Date(today.getYear(),today.getMonth(),today.getDate()+1));
            aq.setPm2_5(pm2_5);
            aq.setNo2(no2);
            aq.setCity(city);
            return aq;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<AirQuality> getHistoric(String city, Date dateStart, Date dateEnd) {
        double[] latlon=null;
        try {
            latlon = geocodingClient.getLatLonfromCity(city);
        }catch (IOException e){
            return null;
        }
        double lat = latlon[0];
        double lon = latlon[1];
        String SUrl = String.format(baseurlHistoric,lat,lon,dateStart.getTime()/1000,dateEnd.getTime()/1000,APIKEY);
        System.out.println(SUrl);
        try {
            URL url = new URL(SUrl);
            URLConnection request = url.openConnection();
            request.connect();

            // Convert to a JSON object to print data
            //from gson
            JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
            JsonArray listjson = rootobj.get("list").getAsJsonArray();
            List<AirQuality> aqlist = new ArrayList<>();

            Set<String> datesAlreadyUsed= new HashSet<>();
            for (JsonElement c : listjson) {
                long dt = c.getAsJsonObject().get("dt").getAsLong();
                JsonObject components = null;
                Date date = new Date(dt*1000);
                String converted = String.format("%d-%d-%d",date.getYear(),date.getMonth(),date.getDate());
                if (datesAlreadyUsed.add(converted)){
                    System.out.println("aqui");
                    components = c.getAsJsonObject().get(COMPONENTS).getAsJsonObject();
                    double no2 = components.get("no2").getAsDouble();
                    double o3 = components.get("o3").getAsDouble();
                    double so2 = components.get("so2").getAsDouble();
                    double pm2_5 = components.get("pm2_5").getAsDouble();
                    double pm10 = components.get("pm10").getAsDouble();
                    AirQuality aq = new AirQuality();
                    aq.setLat(lat);
                    aq.setLon(lon);
                    aq.setSo2(so2);
                    aq.setPm10(pm10);
                    aq.setO3(o3);
                    aq.setDate(new Date(date.getYear(),date.getMonth(),date.getDate()));
                    aq.setPm2_5(pm2_5);
                    aq.setNo2(no2);
                    aq.setCity(city);
                    aqlist.add(aq);
                }
            }
            return aqlist;
        }catch (Exception e){
            return null;
        }
    }

    public void setGeocodingClient(OpenWeatherMapGeocodingClient geocodingClient){
        this.geocodingClient = geocodingClient;
    }
}
