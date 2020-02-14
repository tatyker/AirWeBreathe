package com.studiotaty.airwebreathe;

import com.studiotaty.airwebreathe.model.AirData;

public interface TaskDelegate {

    //helps to notify that download task has started or is completed
    public void taskComplete(boolean success);

    public void taskStarted(boolean started);


}
