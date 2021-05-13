package pt.ua.airquality.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.airquality.entities.AirQuality;
import pt.ua.airquality.repository.AirQualityRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AirQualityManagerService {
    @Autowired
    private AirQualityRepository repository;

    public AirQuality getAirQualityTodayForCity(String city){
        return null;
    }

    public AirQuality getAirQualityDateForCity(String city, Date d){
        return null;
    }

    public List<AirQuality> getAirQualityForCityHistoric(String city, Date start, Date end){
        return null;
    }

}
