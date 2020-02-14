package com.studiotaty.airwebreathe.model;



public class Station {

    private String aqi;

    private String stationZone;
    private String stationTime;

    private String stationName;
    private String stationUrl;
    private String countryCode;

    private String lon;
    private String lat;

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getAqi() {
        return aqi;
    }

    public String getStationZone() {
        return stationZone;
    }

    public String getStationTime() {
        return stationTime;
    }

    public String getStationName() {
        return stationName;
    }


    public String getStationUrl() {
        return stationUrl;
    }

    public String getCountryCode() {
        return countryCode;
    }


    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public void setStationZone(String stationZone) {
        this.stationZone = stationZone;
    }

    public void setStationTime(String stationTime) {
        this.stationTime = stationTime;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }


    public void setStationUrl(String stationUrl) {
        this.stationUrl = stationUrl;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
