package pt.ua.airquality.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pt.ua.airquality.service.AirQualityManagerService;

@Controller
public class AirQualityController {

    @Autowired
    private AirQualityManagerService service;

    @GetMapping("")
    private String index(){return "";}
}
