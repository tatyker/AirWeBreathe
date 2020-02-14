package com.studiotaty.airwebreathe.model;

import java.io.Serializable;
import java.util.List;

public class AirData implements Serializable {

    private String aqi;

    private List<Station> station;

    private List<Attribution> credits;

    public Location location = new Location();
    public Iaqi iaqi = new Iaqi();
    public Time time = new Time();

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public List<Station> getStation() {
        return station;
    }

    public void setStation(List<Station> station) {
        this.station = station;
    }

    public List<Attribution> getCredits() {
        return credits;
    }

    public void setCredits(List<Attribution> credits) {
        this.credits = credits;
    }

    public class Location {

        private String cityName = "";
        private String cityURL = "";

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getCityURL() {
            return cityURL;
        }

        public void setCityURL(String cityURL) {
            this.cityURL = cityURL;
        }
    }

    public class Iaqi{

        private float co = -1;
        private float no2 = -1;
        private float so2 = -1;
        private float pm10 = -1;
        private float pm25 = -1;
        private float wind = -1;

        public float getCo() {
            return co;
        }

        public void setCo(float co) {
            this.co = co;
        }

        public float getNo2() {
            return no2;
        }

        public void setNo2(float no2) {
            this.no2 = no2;
        }

        public float getSo2() {
            return so2;
        }

        public void setSo2(float so2) {
            this.so2 = so2;
        }

        public float getPm10() {
            return pm10;
        }

        public void setPm10(float pm10) {
            this.pm10 = pm10;
        }

        public float getPm25() {
            return pm25;
        }

        public void setPm25(float pm25) {
            this.pm25 = pm25;
        }

        public float getWind() {
            return wind;
        }

        public void setWind(float wind) {
            this.wind = wind;
        }
    }


    public class Time{

        private String dateTime;
        private String timeZone;

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }
    }


}
