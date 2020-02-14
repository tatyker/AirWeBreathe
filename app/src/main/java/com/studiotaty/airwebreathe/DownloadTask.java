package com.studiotaty.airwebreathe;

import android.os.AsyncTask;

import com.studiotaty.airwebreathe.model.AirData;


public class DownloadTask extends AsyncTask<String, Void, AirData> {

    private TaskDelegate taskDelegate;
    private Boolean search;


    public  DownloadTask (TaskDelegate taskDelegate, Boolean search){
            this.taskDelegate = taskDelegate;
            this.search = search;
    }

    @Override
    protected void onPreExecute() {
        //notifies through taskDelegat interface that the task has started and loading circle should appear
        taskDelegate.taskStarted(true);
    }

    @Override
    protected AirData doInBackground(String... strings) {

        //downloads data from api with help of DataHttpsClient
        DataHttpsClient dataHttpsClient = new DataHttpsClient();
        String data = dataHttpsClient.getAllData(strings[0]);

        //returns airData with help of JSONParser
        return JSONParser.getAirData(data, search);
    }

    @Override
    protected void onPostExecute(AirData airData) {

        //notifies that task is done and that data exists
       if (airData != null) {
           taskDelegate.taskComplete(true);
       }

    }
}
