package pt.ua.airquality.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "Air_Quality")
public class AirQuality {
    private Integer id;
    private double pm2_5;
    private double pm10;
    private double no2;
    private double o3;
    private double so2;
    private String city;
    private double lat;
    private double lon;
    private Instant timestamp;

    @Column(name = "pm2_5",nullable = false)
    public double getPm2_5() {
        return pm2_5;
    }

    public void setPm2_5(double pm2_5) {
        this.pm2_5 = pm2_5;
    }

    @Column(name = "pm10",nullable = false)
    public double getPm10() {
        return pm10;
    }

    public void setPm10(double pm10) {
        this.pm10 = pm10;
    }

    @Column(name = "n02",nullable = false)
    public double getNo2() {
        return no2;
    }

    public void setNo2(double no2) {
        this.no2 = no2;
    }

    @Column(name = "o3",nullable = false)
    public double getO3() {
        return o3;
    }

    public void setO3(double o3) {
        this.o3 = o3;
    }

    @Column(name = "so2",nullable = false)
    public double getSo2() {
        return so2;
    }

    public void setSo2(double so2) {
        this.so2 = so2;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    public Integer getId() {
        return id;
    }
}
