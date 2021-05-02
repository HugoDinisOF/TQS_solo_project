package pt.ua.airquality.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.airquality.service.AirQualityManagerService;

@RestController
@RequestMapping("api")
public class AirQualityRestController {
    @Autowired
    private AirQualityManagerService service;
}
