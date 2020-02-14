package com.studiotaty.airwebreathe;


import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.studiotaty.airwebreathe.model.Station;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements TaskDelegate {


    private DownloadTask downloadTask;

    /* if there is an error with download parserMessage
    is not null and the MainActivity is showing error Toast */
    private static String parserMessage = null;
    public static void setParserMessage(String parserMessage) {
        MainActivity.parserMessage = parserMessage;
    }


    private ArrayList<Station> stationsList;
    private StationAdapter stationAdapter;

    private SharedPreferences sharedPreferences;

    private SwipeRefreshLayout refreshLayout;

    private ProgressBar progressBar;

    private String location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // default location is serbia, after program remembers last searched location
        sharedPreferences = getPreferences(MODE_PRIVATE);
        if(sharedPreferences.getString("location","").isEmpty()){
            location = "serbia";
        } else {
            location = sharedPreferences.getString("location", "");
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        stationsList = new ArrayList<>();

        stationAdapter = new StationAdapter(stationsList, this);
        recyclerView.setAdapter(stationAdapter);

        progressBar = findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.INVISIBLE);


        startDownloadTask(location);


        refreshLayout = findViewById(R.id.pullRefresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                location = sharedPreferences.getString("location", "");
                startDownloadTask(location);
                refreshLayout.setRefreshing(false);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    private void saveLocation(String location){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("location", location);
        editor.apply();
    }

    public void openDialog() {
        SearchDialog dialog = new SearchDialog();
        dialog.show(getSupportFragmentManager(), "search dialog");
    }

    public void doSearch(String term) {

        if (!term.equals("")) {
            term = term.replaceAll(" ", "%20");
            startDownloadTask(term);
            saveLocation(term);
        } else {
            makeToast(getString(R.string.enter_name));
        }
    }


    public void startDownloadTask(String location) {
        //api url to search for city or country. default location is set to serbia
        String urlString = "https://api.waqi.info/search/?keyword=" + location + "&token=69cc8a9b678ee86c3238c1154ef96ae5f7ddda67";
        downloadTask = new DownloadTask(this, true);
        downloadTask.execute(urlString);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_credits) {
            CreditsDialog creditsDialog = new CreditsDialog();
            creditsDialog.show(getSupportFragmentManager(), "credits dialog");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void taskStarted(boolean started) {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void taskComplete(boolean success) {
        //if downloadTask is finished ok, we clear old station list and then add new station data
        progressBar.setVisibility(View.INVISIBLE);
        stationsList.clear();
        try {
            if (downloadTask.get().getStation() != null)
            stationsList.addAll(downloadTask.get().getStation());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stationAdapter.notifyDataSetChanged();

        //if data is not found parserMessage is set to not null; we set it back to null again
        if (parserMessage != null) {
            if(parserMessage.equals("net")){
                makeToast(getString(R.string.connection_error));
            } else {
                makeToast(getString(R.string.no_data));
            }
            saveLocation("serbia");
            parserMessage = null;
        }

    }

    //method to make new Toast in center of screen with custom message
    public void makeToast(String message) {

        Toast toast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


}
