package pt.ua.airquality.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.airquality.repository.AirQualityRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class AirQualityManagerService {
    @Autowired
    private AirQualityRepository repository;

}
