package pt.ua.airquality.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.airquality.connection.ISimpleAPIClient;
import pt.ua.airquality.connection.OpenWeatherMapAirPollutionClient;
import pt.ua.airquality.entities.AirQuality;
import pt.ua.airquality.entities.AirQualityCacheData;
import pt.ua.airquality.repository.AirQualityRepository;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
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
        if(Instant.now().getEpochSecond()-aq.getTimestamp().getEpochSecond()>15*60){
            AirQuality result = ApiClient.getToday(city);
            aq.update(result);
            repository.save(aq);
            return aq;
        }
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
        if(Instant.now().getEpochSecond()-aq.getTimestamp().getEpochSecond()>15*60){
            if (today.getTime() > aq.getDate().getTime()){
                Date dend = new Date(aq.getDate().getYear(),aq.getDate().getMonth(),aq.getDate().getDate()+1);
                dend.setHours(23);
                AirQuality result = ApiClient.getHistoric(city,d,dend).get(0);
                aq.update(result);
                repository.save(aq);
                return aq;
            }
            else{
                AirQuality result = ApiClient.getForecast(city);
                aq.update(result);
                repository.save(aq);
                return aq;
            }
        }
        aq.addHit();
        repository.save(aq);
        return resultRepo.get();
    }

    public List<AirQuality> getAirQualityForCityHistoric(String city, Date start, Date end){
        Date d = new Date();
        long days = (end.getTime()- start.getTime())/(1000*60*60*24)+1;
        List<AirQuality> resultRepo = repository.findAQSbyCityAndDate(city,start,end);
        if (resultRepo.size()<days){
            List<AirQuality> result = ApiClient.getHistoric(city,start,end);
            for (AirQuality aq: result){
                if (repository.findAQbyCityAndDate(city,aq.getDate()).isEmpty()){
                    repository.save(aq);
                }
                else{
                    AirQuality aqold =repository.findAQbyCityAndDate(city,aq.getDate()).get();
                    aqold.update(aq);
                    repository.save(aqold);
                }
            }
            return result;
        }
        for (AirQuality aq: resultRepo){
            aq.addHit();
            repository.save(aq);
        }
        return resultRepo;
    }
    public List<AirQualityCacheData> getCache() {
        List<AirQualityCacheData> result = new ArrayList<>();
        for (AirQuality aq: repository.findAll()){
            AirQualityCacheData aqdata = new AirQualityCacheData(aq.getCity(),aq.getDate(),aq.getMiss(),aq.getHit());
            result.add(aqdata);
        }
        return result;
    }


    public void setApiClient(ISimpleAPIClient apiClient){
        this.ApiClient = apiClient;
    }
}
