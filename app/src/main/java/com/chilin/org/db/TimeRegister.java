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
        Day registeredDay = getDbReader().getRegisteredDay(currentDate);
        if (noRegistryInDB(registeredDay)){
            getDbWriter().createBeginnPause(currentDate,beginnPause);
        } else {
            validateBeginnPause(registeredDay,beginnPause);
            getDbWriter().updateBeginnPause(currentDate,beginnPause);
        }
    }

    private void validateBeginnPause(Day registeredDay, String beginnPause) {
        String currentDate = registeredDay.getDayRegistered();
        String leavingTime = registeredDay.getLeavingTime();
        String comingTime = registeredDay.getComingTime();
        if (StringUtils.isNotBlank(comingTime) && StringUtils.isNotBlank(leavingTime)){
            if (DateTimeOperationsProvider.isBeginnPauseBetweenComingAndLeavingTime(currentDate,comingTime,leavingTime,beginnPause)){
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (StringUtils.isNotBlank(comingTime) && StringUtils.isBlank(leavingTime)){
            if (DateTimeOperationsProvider.isBeginnPauseBeforeComingTime(currentDate,comingTime,beginnPause)){
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (StringUtils.isBlank(comingTime) && StringUtils.isNotBlank(leavingTime)){
            if (DateTimeOperationsProvider.isBeginnPauseAfterLeavingTime(currentDate,comingTime,beginnPause)){
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        }
    }

    private boolean noRegistryInDB(Day registeredDay) {
        return registeredDay == null;
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
