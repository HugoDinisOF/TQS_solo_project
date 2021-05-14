package pt.ua.airquality.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pt.ua.airquality.connection.ISimpleAPIClient;
import pt.ua.airquality.entities.AirQuality;
import pt.ua.airquality.repository.AirQualityRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AirQualityManagerServiceTest {

    @Mock(lenient = true)
    private AirQualityRepository repository;

    @InjectMocks
    private AirQualityManagerService managerService;

    private ISimpleAPIClient apiClient;

    Date d = new Date();

    AirQuality aq1;
    AirQuality aq2;
    AirQuality aq3;

    @BeforeEach
    public void setUp(){
        apiClient = mock(ISimpleAPIClient.class);
        managerService.setApiClient(apiClient);

        aq1 = new AirQuality();
        aq1.setCity("Aveiro");
        aq1.setDate(new Date(d.getYear(),d.getMonth(),d.getDate()));
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

    }


    @Test
    void getAirQualityTodayForCityExistingOnRepoTest() {
        when(repository.findAQbyCityAndDate("Aveiro", new Date(d.getYear(),d.getMonth(),d.getDate()))).thenReturn(Optional.ofNullable(aq1));
        managerService.getAirQualityTodayForCity("Aveiro");

    }

    @Test
    void getAirQualityDateForCity() {
    }

    @Test
    void getAirQualityForCityHistoric() {
    }
}