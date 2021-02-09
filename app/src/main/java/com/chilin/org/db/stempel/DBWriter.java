package com.chilin.org.db.stempel;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.chilin.org.db.DBDealer;
import com.chilin.org.db.entity.DayEntity;
import com.chilin.org.model.Day;

public class DBWriter {

    private DBDealer dbDealer;

    public DBWriter(Context context){
        this.dbDealer = new DBDealer(context);
    }

    public void updateComingTime(String currentDate,String comingTime) {
        ContentValues values = new ContentValues();
        values.put(DayEntity.COLUMN_NAME_COMMING, comingTime);
        updateDay(values,currentDate);
    }

    public void updateBeginnPause(String currentDate,String beginnPause) {
        ContentValues values = new ContentValues();
        values.put(DayEntity.COLUMN_NAME_BEGINN_PAUSE, beginnPause);
        updateDay(values,currentDate);
    }

    public void updateEndePause(String currentDate, String endePause) {
        ContentValues values = new ContentValues();
        values.put(DayEntity.COLUMN_NAME_ENDE_PAUSE, endePause);
        updateDay(values,currentDate);
    }

    public void updateLeavingTime(String currentDate, String leavingTime) {
        ContentValues values = new ContentValues();
        values.put(DayEntity.COLUMN_NAME_LEAVING, leavingTime);
        updateDay(values,currentDate);
    }

    public void createComingTime(String currentDate,String comingTime) {
        ContentValues values = new ContentValues();
        values.put(DayEntity.COLUMN_NAME_DATE, currentDate);
        values.put(DayEntity.COLUMN_NAME_COMMING, comingTime);
        createDay(values);
    }

    public void createBeginnPause(String currentDate, String beginnPause) {
        ContentValues values = new ContentValues();
        values.put(DayEntity.COLUMN_NAME_DATE, currentDate);
        values.put(DayEntity.COLUMN_NAME_BEGINN_PAUSE, beginnPause);
        createDay(values);
    }

    public void createEndePause(String currentDate, String endePause) {
        ContentValues values = new ContentValues();
        values.put(DayEntity.COLUMN_NAME_DATE, currentDate);
        values.put(DayEntity.COLUMN_NAME_ENDE_PAUSE, endePause);
        createDay(values);
    }

    public void createLeavingTime(String currentDate, String leavingTime){
        ContentValues values = new ContentValues();
        values.put(DayEntity.COLUMN_NAME_DATE, currentDate);
        values.put(DayEntity.COLUMN_NAME_LEAVING, leavingTime);
        createDay(values);
    }

    public void deleteDay(String currentDate){
        String whereColumns = DayEntity.COLUMN_NAME_DATE + " = ?";
        String[] whereValues = { currentDate };
        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();
        writableDatabase.delete(DayEntity.TABLE_NAME,whereColumns,whereValues);
    }

    public int updateDay(Day dayToChange) {
        String whereColumns = DayEntity.COLUMN_NAME_DATE + " = ?";
        String[] whereValues = { dayToChange.getDayRegistered() };

        ContentValues values = new ContentValues();
        values.put(DayEntity.COLUMN_NAME_COMMING, dayToChange.getComingTime());
        values.put(DayEntity.COLUMN_NAME_BEGINN_PAUSE, dayToChange.getBeginnPause());
        values.put(DayEntity.COLUMN_NAME_ENDE_PAUSE, dayToChange.getEndePause());
        values.put(DayEntity.COLUMN_NAME_LEAVING, dayToChange.getLeavingTime());

        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();
        return writableDatabase.update(DayEntity.TABLE_NAME,values,whereColumns,whereValues);
    }

    public void createDay(Day newDay) {
        ContentValues values = new ContentValues();
        values.put(DayEntity.COLUMN_NAME_DATE, newDay.getDayRegistered());
        values.put(DayEntity.COLUMN_NAME_COMMING, newDay.getComingTime());
        values.put(DayEntity.COLUMN_NAME_BEGINN_PAUSE, newDay.getBeginnPause());
        values.put(DayEntity.COLUMN_NAME_ENDE_PAUSE, newDay.getEndePause());
        values.put(DayEntity.COLUMN_NAME_LEAVING, newDay.getLeavingTime());

        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();
        writableDatabase.insert(DayEntity.TABLE_NAME, null, values);
    }

    private void updateDay(ContentValues values,String currentDate){
        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();

        String whereColumns = DayEntity.COLUMN_NAME_DATE + " = ?";
        String[] whereValues = { currentDate };

        writableDatabase.update(DayEntity.TABLE_NAME,
                values,whereColumns,whereValues);
    }

    private void createDay(ContentValues values){
        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();
        writableDatabase.insert(DayEntity.TABLE_NAME, null, values);
    }

}
