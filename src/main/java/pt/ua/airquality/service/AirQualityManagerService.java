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
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
public class AirQualityManagerService {
    @Autowired
    private AirQualityRepository repository;

    private ISimpleAPIClient ApiClient = new OpenWeatherMapAirPollutionClient();
    private final Logger logger = Logger.getLogger(AirQualityRepository.class.getName());


    public AirQualityManagerService(AirQualityRepository repository) {
        this.repository = repository;
    }


    public AirQuality getAirQualityTodayForCity(String city){
        Date d = new Date();
        Optional<AirQuality> resultRepo = repository.findAQbyCityAndDate(city,new Date(d.getYear(),d.getMonth(),d.getDate()));
        if (resultRepo.isEmpty()){
            logger.log(Level.INFO, "Getting Air Quality Today from API");
            AirQuality result = ApiClient.getToday(city);
            repository.save(result);
            return result;
        }

        AirQuality aq = resultRepo.get();
        if(Instant.now().getEpochSecond()-aq.getTimestamp().getEpochSecond()>15*60){
            logger.log(Level.INFO, "Getting Air Quality Today from API");
            AirQuality result = ApiClient.getToday(city);
            aq.update(result);
            repository.save(aq);
            return aq;
        }
        logger.log(Level.INFO, "Getting Air Quality Today from repo");
        aq.addHit();
        repository.save(aq);
        return aq;
    }

    public AirQuality getAirQualityDateForCity(String city, Date d){
        Date today = new Date();
        Optional<AirQuality> resultRepo = repository.findAQbyCityAndDate(city,d);

        if (resultRepo.isEmpty()){
            if (today.getTime() > d.getTime()){
                logger.log(Level.INFO, "Getting Air Quality Date from API");
                Date dend = new Date(d.getYear(),d.getMonth(),d.getDate()+1);
                dend.setHours(23);
                AirQuality result = ApiClient.getHistoric(city,d,dend).get(0);
                repository.save(result);
                return result;
            }
            else{
                logger.log(Level.INFO, "Getting Air Quality Forecast from API");
                AirQuality result = ApiClient.getForecast(city);
                repository.save(result);
                return result;
            }
        }
        AirQuality aq = resultRepo.get();
        if(Instant.now().getEpochSecond()-aq.getTimestamp().getEpochSecond()>15*60){
            if (today.getTime() > aq.getDate().getTime()){
                logger.log(Level.INFO, "Getting Air Quality Date from API");
                Date dend = new Date(aq.getDate().getYear(),aq.getDate().getMonth(),aq.getDate().getDate()+1);
                dend.setHours(23);
                AirQuality result = ApiClient.getHistoric(city,d,dend).get(0);
                aq.update(result);
                repository.save(aq);
                return aq;
            }
            else{
                logger.log(Level.INFO, "Getting Air Quality Forecast from API");
                AirQuality result = ApiClient.getForecast(city);
                aq.update(result);
                repository.save(aq);
                return aq;
            }
        }
        logger.log(Level.INFO, "Getting Air Quality Date from repo");
        aq.addHit();
        repository.save(aq);
        return resultRepo.get();
    }

    public List<AirQuality> getAirQualityForCityHistoric(String city, Date start, Date end){
        long days = (end.getTime()- start.getTime())/(1000*60*60*24)+1;
        List<AirQuality> resultRepo = repository.findAQSbyCityAndDate(city,start,end);
        if (resultRepo.size()<days){
            logger.log(Level.INFO, "Getting Air Quality Historic from API");
            List<AirQuality> result = ApiClient.getHistoric(city,start,end);
            for (AirQuality aq: result){
                Optional<AirQuality> resultdb1= repository.findAQbyCityAndDate(city,aq.getDate());
                if (resultdb1.isEmpty()){
                    repository.save(aq);
                }
                else{
                    AirQuality aqold =resultdb1.get();
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
        logger.log(Level.INFO, "Getting Air Quality Date from repo");
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
