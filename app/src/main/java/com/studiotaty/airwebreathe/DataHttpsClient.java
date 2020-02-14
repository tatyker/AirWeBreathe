package com.studiotaty.airwebreathe;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DataHttpsClient {




    public String getAllData(String url){



        HttpsURLConnection conn = null;

        try {
            //making coonection with given url and getting all data from it
            conn = (HttpsURLConnection) new URL(url).openConnection();
            conn.connect();


                InputStream stream = conn.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(stream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String data = null;
                while ((data = bufferedReader.readLine()) != null) {
                    stringBuilder.append(data);
                }


                stream.close();
                conn.disconnect();

                //my checkpoint to see what is downloaded - can be removed
                Log.e("class", stringBuilder.toString());

                return stringBuilder.toString();



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

}
