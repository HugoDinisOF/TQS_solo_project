package pt.ua.airquality.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

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
    private final Instant timestamp;
    private Date date;
    private int miss=0;
    private int hit=0;

    public AirQuality(){
        timestamp = Instant.now();
        setMiss(1);
    }

    public void addHit(){
        hit += 1;
    }

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

    @Column(name = "city",nullable = false)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "lat",nullable = false)
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Column(name = "lon",nullable = false)
    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Column(name = "timestamp",nullable = false)
    public Instant getTimestamp() {
        return timestamp;
    }

    @Column(name = "date",nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "miss",nullable = false)
    public int getMiss() {
        return miss;
    }

    public void setMiss(int miss) {
        this.miss = miss;
    }

    @Column(name = "hit",nullable = false)
    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AirQuality)) return false;
        AirQuality that = (AirQuality) o;
        return Double.compare(that.getPm2_5(), getPm2_5()) == 0 && Double.compare(that.getPm10(), getPm10()) == 0 && Double.compare(that.getNo2(), getNo2()) == 0 && Double.compare(that.getO3(), getO3()) == 0 && Double.compare(that.getSo2(), getSo2()) == 0 && Double.compare(that.getLat(), getLat()) == 0 && Double.compare(that.getLon(), getLon()) == 0 && getCity().equals(that.getCity()) && getDate().equals(that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPm2_5(), getPm10(), getNo2(), getO3(), getSo2(), getCity(), getLat(), getLon(), getDate());
    }
}
