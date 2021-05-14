package pt.ua.airquality.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pt.ua.airquality.entities.AirQuality;
import pt.ua.airquality.entities.AirQualityCacheData;
import pt.ua.airquality.service.AirQualityManagerService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class AirQualityRestControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    AirQualityManagerService service;

    AirQuality aq1;
    AirQuality aq2;
    AirQuality aq3;

    @BeforeEach
    void setUp() {
        aq1 = new AirQuality();
        aq1.setCity("Aveiro");
        aq1.setDate(new Date(2021-1900,4,13));
        aq1.setLat(120);
        aq1.setLon(120);
        aq1.setNo2(12);
        aq1.setO3(12);
        aq1.setPm2_5(12);
        aq1.setPm10(12);
        aq1.setSo2(12);
        aq2 = new AirQuality();
        aq2.setCity("Aveiro");
        aq2.setDate(new Date(2021-1900,4,12));
        aq2.setLat(120);
        aq2.setLon(120);
        aq2.setNo2(11);
        aq2.setO3(11);
        aq2.setPm2_5(11);
        aq2.setPm10(11);
        aq2.setSo2(11);
        aq3 = new AirQuality();
        aq3.setCity("Aveiro");
        aq3.setDate(new Date(2021-1900,4,11));
        aq3.setLat(120);
        aq3.setLon(120);
        aq3.setNo2(10);
        aq3.setO3(10);
        aq3.setPm2_5(10);
        aq3.setPm10(10);
        aq3.setSo2(10);
        //service = mock(AirQualityManagerService.class);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void getAQforCityToday() throws Exception{
        when( service.getAirQualityTodayForCity("Aveiro")).thenReturn( aq1 );

        mvc.perform(MockMvcRequestBuilders.get("/api/airquality/today/Aveiro"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("lat").value(120))
                .andExpect(jsonPath("lon").value(120))
                .andExpect(jsonPath("no2").value(12))
                .andExpect(jsonPath("o3").value(12))
                .andExpect(jsonPath("pm2_5").value(12))
                .andExpect(jsonPath("pm10").value(12))
                .andExpect(jsonPath("so2").value(12))
                .andExpect(jsonPath("city").value("Aveiro"));

        verify(service, times(1)).getAirQualityTodayForCity("Aveiro");
    }
    @Test
    public void getAQforCityDate() throws Exception{
        when( service.getAirQualityDateForCity("Aveiro",new Date(2021-1900, 5-1,13))).thenReturn( aq1 );

        mvc.perform(MockMvcRequestBuilders.get("/api/airquality/date/Aveiro/13-05-2021"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("lat").value(120))
                .andExpect(jsonPath("lon").value(120))
                .andExpect(jsonPath("no2").value(12))
                .andExpect(jsonPath("o3").value(12))
                .andExpect(jsonPath("pm2_5").value(12))
                .andExpect(jsonPath("pm10").value(12))
                .andExpect(jsonPath("so2").value(12))
                .andExpect(jsonPath("city").value("Aveiro"));

        verify(service, times(1)).getAirQualityDateForCity("Aveiro",new Date(2021-1900, 5-1,13));
    }
    @Test
    public void getAirQualityForCityHistoric() throws Exception{
        List<AirQuality> aqlist = Arrays.asList(aq3,aq2,aq1);

        when( service.getAirQualityForCityHistoric("Aveiro",new Date(2021-1900, 5-1,11),new Date(2021-1900, 5-1,13))).thenReturn( aqlist );

        mvc.perform(MockMvcRequestBuilders.get("/api/airquality/historic/Aveiro/11-05-2021/13-05-2021"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lat", is(aq3.getLat())))
                .andExpect(jsonPath("$[0].lon",is(aq3.getLon())))
                .andExpect(jsonPath("$[0].no2",is(aq3.getNo2())))
                .andExpect(jsonPath("$[1].no2",is(aq2.getNo2())))
                .andExpect(jsonPath("$[2].no2",is(aq1.getNo2())))
                .andExpect(jsonPath("$[0].city",is(aq3.getCity())));

        verify(service, times(1)).getAirQualityForCityHistoric("Aveiro",new Date(2021-1900, 5-1,11),new Date(2021-1900, 5-1,13));
    }

    @Test
    public void getCache() throws Exception{
        AirQualityCacheData aqdata = new AirQualityCacheData(aq1);
        when( service.getCache()).thenReturn( Arrays.asList(aqdata));

        mvc.perform(MockMvcRequestBuilders.get("/api/cache"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].city", is(aqdata.getCity())));

        verify(service, times(1)).getCache();
    }
}