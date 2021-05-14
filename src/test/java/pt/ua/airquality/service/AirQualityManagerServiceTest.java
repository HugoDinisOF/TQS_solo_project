package pt.ua.airquality.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pt.ua.airquality.connection.ISimpleAPIClient;
import pt.ua.airquality.entities.AirQuality;
import pt.ua.airquality.entities.AirQualityCacheData;
import pt.ua.airquality.repository.AirQualityRepository;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AirQualityManagerServiceTest {

    private AirQualityRepository repository;

    private AirQualityManagerService managerService;

    private ISimpleAPIClient apiClient;

    Date d = new Date();

    AirQuality aq1;
    AirQuality aq2;
    AirQuality aq3;
    AirQuality aq4;

    @BeforeEach
    public void setUp(){
        repository = mock(AirQualityRepository.class);
        apiClient = mock(ISimpleAPIClient.class);
        managerService = new AirQualityManagerService(repository);
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
        aq4 = new AirQuality();
        aq4.setCity("Aveiro");
        aq4.setDate(new Date(d.getYear(),d.getMonth(),d.getDate()+1));
        aq4.setLat(120);
        aq4.setLon(120);
        aq4.setNo2(10);
        aq4.setO3(10);
        aq4.setPm2_5(10);
        aq4.setPm10(10);
        aq4.setSo2(10);

    }


    @Test
    void getAirQualityTodayForCityExistingOnRepoTest() {
        when(repository.findAQbyCityAndDate("Aveiro", new Date(d.getYear(),d.getMonth(),d.getDate()))).thenReturn(Optional.ofNullable(aq1));
        AirQuality aq = managerService.getAirQualityTodayForCity("Aveiro");
        assertEquals(aq,aq1);
        verify(repository, VerificationModeFactory.times(1)).findAQbyCityAndDate("Aveiro", new Date(d.getYear(),d.getMonth(),d.getDate()));
        verify(apiClient, VerificationModeFactory.times(0)).getToday("Aveiro");
    }
    @Test
    void getAirQualityTodayForCityExistingOnAPITest() {
        when(repository.findAQbyCityAndDate("Aveiro", new Date(d.getYear(),d.getMonth(),d.getDate()))).thenReturn(Optional.empty());
        when(apiClient.getToday("Aveiro")).thenReturn(aq1);
        AirQuality aq = managerService.getAirQualityTodayForCity("Aveiro");
        assertEquals(aq,aq1);
        verify(repository, VerificationModeFactory.times(1)).findAQbyCityAndDate("Aveiro", new Date(d.getYear(),d.getMonth(),d.getDate()));
        verify(apiClient, VerificationModeFactory.times(1)).getToday("Aveiro");
    }

    @Test
    void getAirQualityDateForCityOnRepoTest() {
        Date dend1 = new Date(d.getYear(),d.getMonth(),d.getDate()+1);
        Date dend2 = new Date(d.getYear(),d.getMonth(),d.getDate()+2);
        when(repository.findAQbyCityAndDate("Aveiro", dend1)).thenReturn(Optional.ofNullable(aq4));
        AirQuality aq = managerService.getAirQualityDateForCity("Aveiro",dend1);
        assertEquals(aq,aq4);
        verify(repository, VerificationModeFactory.times(1)).findAQbyCityAndDate("Aveiro", dend1);
        verify(apiClient, VerificationModeFactory.times(0)).getHistoric("Aveiro",dend1,dend2);
        verify(apiClient, VerificationModeFactory.times(0)).getForecast("Aveiro");
    }
    @Test
    void getAirQualityDateForCityOnAPIForecastTest() {
        Date dend1 = new Date(d.getYear(),d.getMonth(),d.getDate()+1);
        Date dend2 = new Date(d.getYear(),d.getMonth(),d.getDate()+2);
        when(repository.findAQbyCityAndDate("Aveiro", dend1)).thenReturn(Optional.empty());
        when(apiClient.getForecast("Aveiro")).thenReturn(aq4);
        AirQuality aq = managerService.getAirQualityDateForCity("Aveiro",dend1);
        assertEquals(aq,aq4);
        verify(repository, VerificationModeFactory.times(1)).findAQbyCityAndDate("Aveiro", dend1);
        verify(apiClient, VerificationModeFactory.times(0)).getHistoric("Aveiro",dend1,dend2);
        verify(apiClient, VerificationModeFactory.times(1)).getForecast("Aveiro");
    }
    @Test
    void getAirQualityDateForCityOnAPIHistoricTest() {
        Date dend1  =new Date(2021-1900,4,11);
        Date dend = new Date(dend1.getYear(),dend1.getMonth(),dend1.getDate()+1);
        dend.setHours(23);
        when(repository.findAQbyCityAndDate("Aveiro", dend1)).thenReturn(Optional.empty());
        when(apiClient.getHistoric("Aveiro",dend1,dend)).thenReturn(Arrays.asList(aq3));
        AirQuality aq = managerService.getAirQualityDateForCity("Aveiro",dend1);
        assertEquals(aq,aq3);
        verify(repository, VerificationModeFactory.times(1)).findAQbyCityAndDate("Aveiro", dend1);
        verify(apiClient, VerificationModeFactory.times(1)).getHistoric("Aveiro",dend1,dend);
        verify(apiClient, VerificationModeFactory.times(0)).getForecast("Aveiro");
    }

    @Test
    void getAirQualityForCityHistoricRepoTest() {
        Date dend1  =new Date(2021-1900,4,11);
        Date dend2 = new Date(dend1.getYear(),dend1.getMonth(),dend1.getDate()+1);
        when(repository.findAQSbyCityAndDate("Aveiro",dend1,dend2)).thenReturn(Arrays.asList(aq2,aq3));
        List<AirQuality> aq = managerService.getAirQualityForCityHistoric("Aveiro",dend1,dend2);
        assertEquals(aq,Arrays.asList(aq2,aq3));
        verify(repository, VerificationModeFactory.times(1)).findAQSbyCityAndDate("Aveiro", dend1,dend2);
        verify(apiClient, VerificationModeFactory.times(0)).getHistoric("Aveiro",dend1,dend2);
    }
    @Test
    void getAirQualityForCityHistoricAPITest() {
        Date dend1  =new Date(2021-1900,4,11);
        Date dend2 = new Date(dend1.getYear(),dend1.getMonth(),dend1.getDate()+1);
        when(repository.findAQSbyCityAndDate("Aveiro",dend1,dend2)).thenReturn(new ArrayList<>());
        when(apiClient.getHistoric("Aveiro",dend1,dend2)).thenReturn(Arrays.asList(aq2,aq3));
        List<AirQuality> aq = managerService.getAirQualityForCityHistoric("Aveiro",dend1,dend2);
        assertEquals(aq,Arrays.asList(aq2,aq3));
        verify(repository, VerificationModeFactory.times(1)).findAQSbyCityAndDate("Aveiro", dend1,dend2);
        verify(apiClient, VerificationModeFactory.times(1)).getHistoric("Aveiro",dend1,dend2);
    }
    @Test
    void getCache(){
        AirQualityCacheData aqdata = new AirQualityCacheData(aq1);
        when( repository.findAll()).thenReturn( Arrays.asList(aq1));

        List<AirQualityCacheData> result=managerService.getCache();
        assertEquals(result,Arrays.asList(aqdata));

        verify(repository, times(1)).findAll();
    }
}