package pt.ua.airquality.connection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ua.airquality.entities.AirQuality;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OpenWeatherMapAirPollutionClientTest {

    OpenWeatherMapGeocodingClient geocodingClient;
    OpenWeatherMapAirPollutionClient client;

    @BeforeEach
    void setUp() {
        geocodingClient = mock(OpenWeatherMapGeocodingClient.class);
        client = new OpenWeatherMapAirPollutionClient();
        client.setGeocodingClient(geocodingClient);
    }

    @Test
    void getToday() throws IOException {
        when(geocodingClient.getLatLonfromCity("Aveiro")).thenReturn(new double[]{40.6443,-8.6455});

        AirQuality aq = client.getToday("Aveiro");
        Date d=new Date();
        assertEquals(new Date(d.getYear(),d.getMonth(),d.getDate()),aq.getDate());
        assertEquals("Aveiro",aq.getCity());

    }

    @Test
    void getForecast() throws IOException {
        when(geocodingClient.getLatLonfromCity("Aveiro")).thenReturn(new double[]{40.6443,-8.6455});

        AirQuality aq = client.getForecast("Aveiro");
        Date d=new Date();
        assertEquals(new Date(d.getYear(),d.getMonth(),d.getDate()+1),aq.getDate());
        assertEquals("Aveiro",aq.getCity());

    }
    @Test
    void getHistoric() throws IOException {
        when(geocodingClient.getLatLonfromCity("Aveiro")).thenReturn(new double[]{40.6443,-8.6455});

        List<AirQuality> aq = client.getHistoric("Aveiro",new Date(2021-1900,5-1,11),new Date(2021-1900,5-1,13));

        assertEquals(3,aq.size());
    }
}