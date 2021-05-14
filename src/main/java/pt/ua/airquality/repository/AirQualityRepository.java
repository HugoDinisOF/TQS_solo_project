package pt.ua.airquality.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ua.airquality.entities.AirQuality;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AirQualityRepository extends JpaRepository<AirQuality,Integer> {
    @Query("SELECT aq FROM AirQuality aq WHERE aq.city = :city and aq.date = :date")
    public Optional<AirQuality> findAQbyCityAndDate(@Param("city") String city, @Param("date") Date date);


    @Query("SELECT aq FROM AirQuality aq WHERE aq.city = :city and aq.date >= :datestart and aq.date <= :dateend")
    public List<AirQuality> findAQSbyCityAndDate(@Param("city") String city,
                                                 @Param("datestart") Date datestart,
                                                 @Param("dateend") Date dateend);

}
