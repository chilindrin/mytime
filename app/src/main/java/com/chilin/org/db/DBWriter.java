package com.chilin.org.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.chilin.org.db.TimeDBSchema.DayEntry.TABLE_NAME;

public class DBWriter {

    private TimeDBDealer dbDealer;
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("HH:mm");

    public DBWriter(Context context){
        this.dbDealer = new TimeDBDealer(context);
    }

    public void updateLeavingTime(String currentDate, String leavingTime) {
        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();

        String whereColumns = TimeDBSchema.DayEntry.COLUMN_NAME_DATE + " = ?";
        String[] whereValues = { currentDate };

        ContentValues values = new ContentValues();
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_LEAVING, leavingTime);

        writableDatabase.update(TABLE_NAME,
                values,whereColumns,whereValues);
    }

    public void createLeavingTime(String currentDate, String leavingTime){
        ContentValues values = new ContentValues();
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_DATE, currentDate);
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_LEAVING, leavingTime);

        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();
        writableDatabase.insert(TABLE_NAME, null, values);
    }

    public void updateComingTime(String currentDate,String comingTime) {
        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();

        String whereColumns = TimeDBSchema.DayEntry.COLUMN_NAME_DATE + " = ?";
        String[] whereValues = { currentDate };

        ContentValues values = new ContentValues();
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_COMMING, comingTime);

        writableDatabase.update(TABLE_NAME,
                values,whereColumns,whereValues);
    }

    public void createComingTime(String currentDate,String comingTime) {
        ContentValues values = new ContentValues();
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_DATE, currentDate);
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_COMMING, comingTime);

        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();
        writableDatabase.insert(TABLE_NAME, null, values);
    }

    public void updateBeginnPause(String currentDate,String beginnPause) {
        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();

        String whereColumns = TimeDBSchema.DayEntry.COLUMN_NAME_DATE + " = ?";
        String[] whereValues = { currentDate };

        ContentValues values = new ContentValues();
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_BEGINN_PAUSE, beginnPause);

        writableDatabase.update(TABLE_NAME,
                values,whereColumns,whereValues);
    }

    public void deleteInfo(String currentDate){
        String whereColumns = TimeDBSchema.DayEntry.COLUMN_NAME_DATE + " = ?";
        String[] whereValues = { currentDate };
        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();
        writableDatabase.delete(TABLE_NAME,whereColumns,whereValues);
    }

    public void createBeginnPause(String currentDate, String beginnPause) {
        ContentValues values = new ContentValues();
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_DATE, currentDate);
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_BEGINN_PAUSE, beginnPause);

        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();
        writableDatabase.insert(TABLE_NAME, null, values);
    }

    public void createEndePause(String currentDate, String endePause) {
        ContentValues values = new ContentValues();
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_DATE, currentDate);
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_ENDE_PAUSE, endePause);

        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();
        writableDatabase.insert(TABLE_NAME, null, values);
    }

    public void updateEndePause(String currentDate, String endePause) {
        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();

        String whereColumns = TimeDBSchema.DayEntry.COLUMN_NAME_DATE + " = ?";
        String[] whereValues = { currentDate };

        ContentValues values = new ContentValues();
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_ENDE_PAUSE, endePause);

        writableDatabase.update(TABLE_NAME,
                values,whereColumns,whereValues);
    }

}
