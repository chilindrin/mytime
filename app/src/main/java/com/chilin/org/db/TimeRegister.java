package com.chilin.org.db;

import android.content.Context;

import com.chilin.org.exception.MyTimeException;
import com.chilin.org.model.Day;
import com.chilin.org.util.DateTimeOperationsProvider;

import org.apache.commons.lang3.StringUtils;

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

    public List<String[]> getAllDataInDB(){
        return getDbReader().getAllDataInDB();
    }

    public void saveComingTime(String currentDate,String comingTime) {
        Day registeredDay = getDbReader().getRegisteredDay(currentDate);
        if (noRegistryInDB(registeredDay)){
            getDbWriter().createComingTime(currentDate,comingTime);
        } else {
            DateTimeOperationsProvider.validateComingTime(registeredDay,comingTime);
            getDbWriter().updateComingTime(currentDate,comingTime);
        }
    }

    public void saveBeginnPause(String currentDate, String beginnPause) {
        Day registeredDay = getDbReader().getRegisteredDay(currentDate);
        if (noRegistryInDB(registeredDay)){
            getDbWriter().createBeginnPause(currentDate,beginnPause);
        } else {
            DateTimeOperationsProvider.validateBeginnPause(registeredDay,beginnPause);
            getDbWriter().updateBeginnPause(currentDate,beginnPause);
        }
    }

    public void saveEndePause(String currentDate, String endePause) {
        Day registeredDay = getDbReader().getRegisteredDay(currentDate);
        if (noRegistryInDB(registeredDay)){
            getDbWriter().createEndePause(currentDate,endePause);
        } else {
            DateTimeOperationsProvider.validateEndePause(registeredDay,endePause);
            getDbWriter().updateEndePause(currentDate,endePause);
        }
    }

    public void saveLeavingTime(String currentDate, String leavingTime) {
        Day registeredDay = getDbReader().getRegisteredDay(currentDate);
        if (noRegistryInDB(registeredDay)){
            getDbWriter().createLeavingTime(currentDate,leavingTime);
        } else {
            DateTimeOperationsProvider.validateLeavingTime(registeredDay,leavingTime);
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
