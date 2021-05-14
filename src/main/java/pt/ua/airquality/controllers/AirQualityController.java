package pt.ua.airquality.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ua.airquality.entities.AirQuality;
import pt.ua.airquality.service.AirQualityManagerService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class AirQualityController {

    private static final String AQLIST = "aqlist";
    @Autowired
    private AirQualityManagerService service;

    @GetMapping("")
    private String index(){
        return "index";
    }

    @GetMapping("/today")
    private String todayAirQuality(@RequestParam(value = "city",required = false) String city, Model model){
        if (city!=null){
            List<AirQuality> lista = new ArrayList<>();
            AirQuality aq = service.getAirQualityTodayForCity(city);
            lista.add(aq);
            model.addAttribute(AQLIST,lista);
        }
        return "resultCity";
    }
    @GetMapping("/date")
    private String dateAirQuality(@RequestParam(value = "city",required = false) String city,@RequestParam(value = "date",required = false) String sDate, Model model){
        if (city!=null && sDate!=null){
            Date date = convertStringtoDate(sDate);
            List<AirQuality> lista = new ArrayList<>();
            AirQuality aq = service.getAirQualityDateForCity(city,date);
            lista.add(aq);
            model.addAttribute(AQLIST,lista);
        }
        return "resultCityDate";
    }

    @GetMapping("/historic")
    private String historicAirQuality(@RequestParam(value = "city",required = false) String city,
                                      @RequestParam(value = "dateStart",required = false) String startDate,
                                      @RequestParam(value = "dateEnd",required = false) String endDate,
                                      Model model){
        if (city!=null && startDate!=null && endDate!=null){
            Date sdate = convertStringtoDate(startDate);
            Date edate = convertStringtoDate(endDate);
            List<AirQuality> result = service.getAirQualityForCityHistoric(city,sdate,edate);
            model.addAttribute(AQLIST,result);
        }
        return "resultHistoricCityDate";
    }

    private Date convertStringtoDate(String date){
        int year=Integer.parseInt(date.split("-")[0]);
        int month=Integer.parseInt(date.split("-")[1]);
        int day=Integer.parseInt(date.split("-")[2]);
        return new Date(year-1900, month-1,day);
    }
}
