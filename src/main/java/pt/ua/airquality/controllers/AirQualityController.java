package pt.ua.airquality.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ua.airquality.entities.AirQuality;
import pt.ua.airquality.service.AirQualityManagerService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AirQualityController {

    @Autowired
    private AirQualityManagerService service;

    @GetMapping("")
    private String index(){return "index";}

    @GetMapping("/today")
    private String todayAirQuality(@RequestParam(value = "city",required = false) String city, Model model){
        List<AirQuality> lista = new ArrayList<>();
        AirQuality aq = service.getAirQualityTodayForCity(city);
        lista.add(aq);
        model.addAttribute("aqlist",lista);
        return "result";
    }
}
