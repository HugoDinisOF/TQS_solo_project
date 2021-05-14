package pt.ua.airquality.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.airquality.entities.AirQuality;
import pt.ua.airquality.service.AirQualityManagerService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api")
public class AirQualityRestController {
    @Autowired
    private AirQualityManagerService service;

    @GetMapping("/airquality/today/{city}")
    private AirQuality getAirQualityToday(@PathVariable String city) {
        return service.getAirQualityTodayForCity(city);
    }
    @GetMapping("/airquality/date/{city}/{date}")
    public AirQuality getAirQualityDateForCity(@PathVariable String city, @PathVariable String date){
        System.out.println(date);
        Date d = convertStringtoDate(date);
        return service.getAirQualityDateForCity(city,d);
    }

    @GetMapping("/airquality/historic/{city}/{datestart}/{dateend}")
    public List<AirQuality> getAirQualityForCityHistoric(@PathVariable String city,@PathVariable String datestart,@PathVariable String dateend){
        Date d1 = convertStringtoDate(datestart);
        Date d2 = convertStringtoDate(dateend);
        return service.getAirQualityForCityHistoric(city, d1, d2);
    }
    private Date convertStringtoDate(String date){
        int year=Integer.parseInt(date.split("-")[2]);
        int month=Integer.parseInt(date.split("-")[1]);
        int day=Integer.parseInt(date.split("-")[0]);
        return new Date(year-1900, month-1,day);
    }
}
