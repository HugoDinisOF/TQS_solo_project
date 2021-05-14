package pt.ua.airquality.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.airquality.connection.ISimpleAPIClient;
import pt.ua.airquality.connection.OpenWeatherMapAirPollutionClient;
import pt.ua.airquality.entities.AirQuality;
import pt.ua.airquality.repository.AirQualityRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AirQualityManagerService {
    @Autowired
    private AirQualityRepository repository;

    private ISimpleAPIClient ApiClient = new OpenWeatherMapAirPollutionClient();

    public AirQualityManagerService(AirQualityRepository repository) {
        this.repository = repository;
    }


    //TODO add timestamp check
    public AirQuality getAirQualityTodayForCity(String city){
        Date d = new Date();
        Optional<AirQuality> resultRepo = repository.findAQbyCityAndDate(city,new Date(d.getYear(),d.getMonth(),d.getDate()));
        if (resultRepo.isEmpty()){
            AirQuality result = ApiClient.getToday(city);
            repository.save(result);
            return result;
        }
        return resultRepo.get();
    }

    public AirQuality getAirQualityDateForCity(String city, Date d){
        return null;
    }

    public List<AirQuality> getAirQualityForCityHistoric(String city, Date start, Date end){
        return null;
    }

    public void setApiClient(ISimpleAPIClient apiClient){
        this.ApiClient = apiClient;
    }
}
