package pt.ua.airquality.connection;

import pt.ua.airquality.entities.AirQuality;

import java.util.Date;
import java.util.List;

public interface ISimpleAPIClient {
    public AirQuality getToday(String city);
    public AirQuality getForecast(String city);
    public List<AirQuality> getHistoric(String city, Date dateStart, Date dateEnd);
}
