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
        AirQuality aq = resultRepo.get();
        aq.addHit();
        repository.save(aq);
        return aq;
    }

    public AirQuality getAirQualityDateForCity(String city, Date d){
        Date today = new Date();
        Optional<AirQuality> resultRepo = repository.findAQbyCityAndDate(city,d);
        if (resultRepo.isEmpty()){
            if (today.getTime() > d.getTime()){
                Date dend = new Date(d.getYear(),d.getMonth(),d.getDate()+1);
                dend.setHours(23);
                AirQuality result = ApiClient.getHistoric(city,d,dend).get(0);
                repository.save(result);
                return result;
            }
            else{
                AirQuality result = ApiClient.getForecast(city);
                repository.save(result);
                return result;
            }
        }
        AirQuality aq = resultRepo.get();
        aq.addHit();
        repository.save(aq);
        return resultRepo.get();
    }

    public List<AirQuality> getAirQualityForCityHistoric(String city, Date start, Date end){
        Date d = new Date();
        List<AirQuality> resultRepo = repository.findAQSbyCityAndDate(city,start,end);
        if (resultRepo.size()==0){
            List<AirQuality> result = ApiClient.getHistoric(city,start,end);
            for (AirQuality aq: result){
                repository.save(aq);
            }
            return result;
        }
        for (AirQuality aq: resultRepo){
            aq.addHit();
            repository.save(aq);
        }
        return resultRepo;
    }
    public List<AirQuality> getCache(String city, Date start, Date end) {
        return repository.findAll();
    }


    public void setApiClient(ISimpleAPIClient apiClient){
        this.ApiClient = apiClient;
    }
}
