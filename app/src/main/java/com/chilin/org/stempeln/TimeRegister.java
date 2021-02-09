package com.chilin.org.stempeln;

import android.content.Context;

import com.chilin.org.db.stempel.DBReader;
import com.chilin.org.db.stempel.DBWriter;
import com.chilin.org.model.Day;

import lombok.Setter;

@Setter
public class TimeRegister {

    private DBReader dbReader;
    private DBWriter dbWriter;
    private Context context;

    public TimeRegister(Context context){
        this.context = context;
    }

    public void saveComingTime(String currentDate,String comingTime) {
        Day registeredDay = getDbReader().getRegisteredDay(currentDate);
        if (noRegistryInDB(registeredDay)){
            getDbWriter().createComingTime(currentDate,comingTime);
        } else {
            InsertTimeValidator.validateComingTime(registeredDay,comingTime);
            getDbWriter().updateComingTime(currentDate,comingTime);

        }
    }

    public void saveBeginnPause(String currentDate, String beginnPause) {
        Day registeredDay = getDbReader().getRegisteredDay(currentDate);
        if (noRegistryInDB(registeredDay)){
            getDbWriter().createBeginnPause(currentDate,beginnPause);
        } else {
            InsertTimeValidator.validateBeginnPause(registeredDay,beginnPause);
            getDbWriter().updateBeginnPause(currentDate,beginnPause);
        }
    }

    public void saveEndePause(String currentDate, String endePause) {
        Day registeredDay = getDbReader().getRegisteredDay(currentDate);
        if (noRegistryInDB(registeredDay)){
            getDbWriter().createEndePause(currentDate,endePause);
        } else {
            InsertTimeValidator.validateEndePause(registeredDay,endePause);
            getDbWriter().updateEndePause(currentDate,endePause);
        }
    }

    public void saveLeavingTime(String currentDate, String leavingTime) {
        Day registeredDay = getDbReader().getRegisteredDay(currentDate);
        if (noRegistryInDB(registeredDay)){
            getDbWriter().createLeavingTime(currentDate,leavingTime);
        } else {
            InsertTimeValidator.validateLeavingTime(registeredDay,leavingTime);
            getDbWriter().updateLeavingTime(currentDate,leavingTime);
        }
    }

    private boolean noRegistryInDB(Day registeredDay) {
        return registeredDay == null;
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

    /*
    Seam fuer UnitTest
     */
    void setDbReader(DBReader dbReader){
        this.dbReader = dbReader;
    }

    /*
    Seam fuer UnitTest
     */
    void setDbWriter(DBWriter dbWriter){
        this.dbWriter = dbWriter;
    }
}
