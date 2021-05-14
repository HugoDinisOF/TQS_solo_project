package pt.ua.airquality.entities;

import java.util.Date;
import java.util.Objects;

public class AirQualityCacheData {
    private String city;
    private Date date;
    private int miss;
    private int hit;

    public AirQualityCacheData(String city, Date date, int miss, int hit) {
        this.city = city;
        this.date = date;
        this.miss = miss;
        this.hit = hit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AirQualityCacheData)) return false;
        AirQualityCacheData that = (AirQualityCacheData) o;
        return getMiss() == that.getMiss() && getHit() == that.getHit() && getCity().equals(that.getCity()) && getDate().equals(that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getDate(), getMiss(), getHit());
    }

    public AirQualityCacheData(AirQuality aq){
        this.city = aq.getCity();
        this.date = aq.getDate();
        this.miss = aq.getMiss();
        this.hit = aq.getHit();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMiss() {
        return miss;
    }

    public void setMiss(int miss) {
        this.miss = miss;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }





}
