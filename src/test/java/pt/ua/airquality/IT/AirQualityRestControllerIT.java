package pt.ua.airquality.IT;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pt.ua.airquality.entities.AirQuality;
import pt.ua.airquality.entities.AirQualityCacheData;
import pt.ua.airquality.service.AirQualityManagerService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AirQualityRestControllerIT {


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Order(1)
    public void getAQforCityToday() throws Exception{
        String baseURI = "http://localhost:8080/api/airquality/today/Aveiro";
        given().relaxedHTTPSValidation().when().get(baseURI).then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("city",is("Aveiro"));
    }
    @Test
    @Order(2)
    public void getAQforCityDate() throws Exception{
        String baseURI = "http://localhost:8080/api/airquality/date/Aveiro/13-05-2021";
        given().relaxedHTTPSValidation().when().get(baseURI).then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("city",is("Aveiro"));
    }
    @Test
    @Order(3)
    public void getAQforCityTodayHit() throws Exception{
        String baseURI = "http://localhost:8080/api/airquality/today/Aveiro";
        given().relaxedHTTPSValidation().when().get(baseURI).then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("city",is("Aveiro"))
                .body("hit",is(1));
    }

}
