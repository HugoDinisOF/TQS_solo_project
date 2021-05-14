package pt.ua.airquality.connection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class OpenWeatherMapGeocodingClientTest {

    OpenWeatherMapGeocodingClient client;

    @BeforeEach
    void setUp() {
        client = new OpenWeatherMapGeocodingClient();
    }
    //TODO add error testing
    @Test
    void getCityfromLatLong() throws IOException {
        double[] latlon= client.getLatLonfromCity("Aveiro");
        assertEquals(40.6443,latlon[0]);
        assertEquals(-8.6455,latlon[1]);
    }
}