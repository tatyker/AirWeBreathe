package com.studiotaty.airwebreathe;

import android.util.Log;

import com.studiotaty.airwebreathe.model.AirData;
import com.studiotaty.airwebreathe.model.Attribution;
import com.studiotaty.airwebreathe.model.Station;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONParser {


    public static AirData getAirData(String data, Boolean search) {


        AirData airData = new AirData();
        ArrayList<Station> stations = new ArrayList<>();
        ArrayList<Attribution> attributions = new ArrayList<>();

        //check if api data exists
        if(data != null) {

            //if we use search for some term this will be used
            if (search) {

                try {
                    //reading JSON data from api and putting them in POJO classes
                    JSONObject object = new JSONObject(data);

                    JSONArray stationArray = object.getJSONArray("data");

                    if (stationArray.length() == 0) {
                        MainActivity.setParserMessage("message");
                    }

                    for (int i = 0; i < stationArray.length(); i++) {

                        JSONObject arrayJSONObject = stationArray.getJSONObject(i);
                        Station station = new Station();

                        String aqi = arrayJSONObject.getString("aqi");

                        JSONObject timeObject = arrayJSONObject.getJSONObject("time");

                        String stationTime = timeObject.getString("stime");
                        String timeZone = timeObject.getString("tz");

                        JSONObject stationObject = arrayJSONObject.getJSONObject("station");

                        String stationName = stationObject.getString("name");
                        String stationUrl = stationObject.getString("url");


                        JSONArray locationArray = stationObject.getJSONArray("geo");

                        String stringLon;
                        String stringLat;

                        if (locationArray.length() == 2) {
                            stringLon = locationArray.getString(0);
                            stringLat = locationArray.getString(1);
                        } else {
                            stringLon = "0";
                            stringLat = "0";
                        }

                        station.setAqi(aqi);
                        station.setStationTime(stationTime);
                        station.setStationZone(timeZone);
                        station.setStationName(stationName);
                        station.setStationUrl(stationUrl);
                        station.setLon(stringLon);
                        station.setLat(stringLat);


                        stations.add(station);

                        //my control point - can be removed with no lose from program
                        Log.e("aqi", aqi);
                        Log.e("time", stationTime);
                        Log.e("timeZone", timeZone);
                        Log.e("name", stationName);
                        Log.e("url", stationUrl);
                        Log.e("longitude", stringLon);
                        Log.e("latitude", stringLat);
                    }
                    airData.setStation(stations);


                } catch (Exception e) {
                    e.printStackTrace();
                    //notifying MainActivity that there were some problem
                    MainActivity.setParserMessage("message");
                }
            } else {
                //if we are using location instead of search

                try {
                    //reading JSON data and putting them in POJO classes
                    JSONObject object = new JSONObject(data);
                    JSONObject dataObject = object.getJSONObject("data");

                    airData.setAqi(dataObject.getString("aqi"));

                    JSONObject locObject = dataObject.getJSONObject("city");

                    airData.location.setCityName(locObject.getString("name"));
                    airData.location.setCityURL(locObject.getString("url"));

                    JSONArray attributionArray = dataObject.getJSONArray("attributions");

                    for (int i = 0; i < attributionArray.length(); i++) {
                        JSONObject localAtt = attributionArray.getJSONObject(i);

                        Attribution attribution = new Attribution();

                        String name = localAtt.getString("name");
                        String url = localAtt.getString("url");

                        if (localAtt.has("logo")) {
                            String logo = localAtt.getString("logo");
                            attribution.setAttLogo(logo);
                        }

                        attribution.setAttName(name);
                        attribution.setAttURL(url);

                        attributions.add(attribution);

                    }

                    airData.setCredits(attributions);

                    JSONObject iaqiObject = dataObject.getJSONObject("iaqi");


                    airData.iaqi.setCo(getIaqiFloat(iaqiObject, "co"));
                    airData.iaqi.setNo2(getIaqiFloat(iaqiObject, "no2"));
                    airData.iaqi.setSo2(getIaqiFloat(iaqiObject, "so2"));
                    airData.iaqi.setPm10(getIaqiFloat(iaqiObject, "pm10"));
                    airData.iaqi.setPm25(getIaqiFloat(iaqiObject, "pm25"));
                    airData.iaqi.setWind(getIaqiFloat(iaqiObject, "w"));


                    JSONObject timeObject = dataObject.getJSONObject("time");

                    airData.time.setDateTime(timeObject.getString("s"));
                    airData.time.setTimeZone(timeObject.getString("tz"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    MainActivity.setParserMessage("message");
                }
            }
        } else MainActivity.setParserMessage("net");
            return airData;
    }

    public static float getIaqiFloat(JSONObject object, String name) {

        float result;

        try {
            //getting floats. if there are no values for some iaqi -1 is returned
            String ime = object.optString(name);
            System.out.println(ime);
            if(ime.equals("")){
                result = -1f;

            }else{
                JSONObject newObject = object.getJSONObject(name);
                result = (float) newObject.getDouble("v");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            result = -1f;
        }

        return result;

    }


}
