package com.studiotaty.airwebreathe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class DetailActivity extends AppCompatActivity implements TaskDelegate {

    DownloadTask downloadTask;

    private TextView cityName;
    private TextView infoMessage;
    private TextView viewAqi;
    private TextView co;
    private TextView no2;
    private TextView so2;
    private TextView pm10;
    private TextView pm25;
    private TextView wind;

    private LinearLayout layout;

    private Integer detailTxtColorNumber;
    private Integer detailBkgColorNumber;
    private Integer infoMessageNumber;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);




        String location = getIntent().getStringExtra("location");

        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);

        startDownloadTask(location);
        try {
            if(downloadTask.get().getAqi() == null){
                Toast toast = Toast.makeText(this, getString(R.string.connection_error), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        detailTxtColorNumber = getIntent().getIntExtra("textColor", 0);
        detailBkgColorNumber = getIntent().getIntExtra("backgroundColor", 0);
        infoMessageNumber = getIntent().getIntExtra("infoMessage", 0);

        cityName = (TextView) findViewById(R.id.city_name);

        viewAqi = (TextView) findViewById(R.id.aqi_view);

        co = (TextView) findViewById(R.id.coView);
        no2 = (TextView) findViewById(R.id.no2View);
        so2 = (TextView) findViewById(R.id.so2View);
        pm10 = (TextView) findViewById(R.id.pm10View);
        pm25 = (TextView) findViewById(R.id.pm25View);
        wind = (TextView) findViewById(R.id.windView);

        infoMessage = (TextView) findViewById(R.id.infoMessage);

        layout = (LinearLayout) findViewById(R.id.linearL);

    }


    private void startDownloadTask(String location){
        String urlString = "https://api.waqi.info/feed/" + location + "/?token=69cc8a9b678ee86c3238c1154ef96ae5f7ddda67";
        downloadTask = new DownloadTask(this, false);
        downloadTask.execute(urlString);
    }

    @Override
    public void taskStarted(boolean started) {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void taskComplete(boolean success) {
        progressBar.setVisibility(View.INVISIBLE);

        try {
            layout.setBackgroundColor(detailBkgColorNumber);
            layout.getBackground().setAlpha(128);

            String cityLink = "<a href=\""+downloadTask.get().location.getCityURL()+"/m\">"+downloadTask.get().location.getCityName()+"</a>";
            cityName.setLinksClickable(true);
            cityName.setText(Html.fromHtml(cityLink));
            cityName.setLinkTextColor(Color.BLACK);
            cityName.setMovementMethod(LinkMovementMethod.getInstance());



            viewAqi.setText(downloadTask.get().getAqi());
            co.setText(String.format("CO: %s", getStringValue(downloadTask.get().iaqi.getCo())));
            no2.setText(String.format("NO2: %s", getStringValue(downloadTask.get().iaqi.getNo2())));
            so2.setText(String.format("SO2: %s", getStringValue(downloadTask.get().iaqi.getSo2())));
            pm10.setText(String.format("pm10: %s", getStringValue(downloadTask.get().iaqi.getPm10())));
            pm25.setText(String.format("pm25: %s", getStringValue(downloadTask.get().iaqi.getPm25())));
            wind.setText(String.format("Vetar: %s", getStringValue(downloadTask.get().iaqi.getWind())));


            infoMessage.setText(getString(infoMessageNumber));


        } catch (ExecutionException e) {

            e.printStackTrace();
        } catch (InterruptedException e) {

            e.printStackTrace();
        }

    }




    public String getStringValue(Float f){

        if (f == -1) {
            return getString(R.string.no_value);
        } else {
            return String.valueOf(f);
        }
    }
}
