package pt.ua.airquality.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ua.airquality.entities.AirQuality;

@Repository
public interface AirQualityRepository extends JpaRepository<AirQuality,Integer> {
}
