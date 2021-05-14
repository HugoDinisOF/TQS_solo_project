package pt.ua.airquality.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pt.ua.airquality.connection.OpenWeatherMapGeocodingClient;
import pt.ua.airquality.entities.AirQuality;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AirQualityRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AirQualityRepository repository;

    Date d = new Date();

    AirQuality aq1;
    AirQuality aq2;
    AirQuality aq3;
    AirQuality aq4;

    @BeforeEach
    void setUp() {
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
    void findAQbyCityAndDate() {
        Date dend1  =new Date(2021-1900,4,11);
        Date dend2 = new Date(dend1.getYear(),dend1.getMonth(),dend1.getDate()+1);
        entityManager.persist(aq1);
        entityManager.persist(aq2);
        entityManager.persist(aq3);
        entityManager.flush();

        Optional<AirQuality> aqs = repository.findAQbyCityAndDate("Aveiro",dend1);

        assertEquals(aqs.get(),aq3);
    }

    @Test
    void findAQSbyCityAndDate() {
        Date dend1  =new Date(2021-1900,4,11);
        Date dend2 = new Date(dend1.getYear(),dend1.getMonth(),dend1.getDate()+1);
        entityManager.persist(aq1);
        entityManager.persist(aq2);
        entityManager.persist(aq3);
        entityManager.flush();

        List<AirQuality> aqs = repository.findAQSbyCityAndDate("Aveiro",dend1,dend2);

        assertThat(aqs).hasSize(2).extracting(AirQuality::getNo2).contains(aq3.getNo2(), aq2.getNo2());
    }
}