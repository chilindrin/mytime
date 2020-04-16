package com.chilin.org.db;

import android.content.Context;

import com.chilin.org.model.Day;

import java.util.List;

import lombok.Setter;

@Setter
public class TimeRegister {

    private DBReader dbReader;
    private DBWriter dbWriter;
    private Context context;

    public TimeRegister(Context context){
        this.context = context;
    }

    public void saveLeavingTime(String currentDate) {
        if (getDbReader().isCurrentDateAlreadyInDB(currentDate)) {
            getDbWriter().updateLeavingTime(currentDate);
        } else {
            getDbWriter().createLeavingTime(currentDate);
        }
    }

    public List<String[]> getAllDataInDB(){
        return getDbReader().getAllDataInDB();
    }

    public void saveComingTime(String currentDate) {
        if (getDbReader().isCurrentDateAlreadyInDB(currentDate)) {
            getDbWriter().updateComingTime(currentDate);
        } else {
            getDbWriter().createComingTime(currentDate);
        }
    }

    public void saveBeginnPause(String currentDate, String beginnPause) {



        if (getDbReader().isCurrentDateAlreadyInDB(currentDate)) {
            Day registeredDay = getDbReader().getRegisteredDay(currentDate);

            //getDbWriter().updateBeginnPause(currentDate,leavingTime);
        } else {
            getDbWriter().createBeginnPause(currentDate);
        }
    }

    public void saveEndePause(String currentDate) {
        if (getDbReader().isCurrentDateAlreadyInDB(currentDate)) {
            getDbWriter().updateEndePause(currentDate);
        } else {
            getDbWriter().createEndePause(currentDate);
        }
    }

    private DBReader getDbReader(){
        if (this.dbReader == null){
            this.dbReader = new DBReader(this.context);
        }
        return this.dbReader;
    }

    private DBWriter getDbWriter(){
        if (this.dbWriter == null){
            this.dbWriter = new DBWriter(this.context);
        }
        return this.dbWriter;
    }
}
