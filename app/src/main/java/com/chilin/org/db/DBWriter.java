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

    public void updateLeavingTime(String currentDate) {
        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();

        String whereColumns = TimeDBSchema.DayEntry.COLUMN_NAME_DATE + " = ?";
        String[] whereValues = { currentDate };

        Date currentTime = Calendar.getInstance().getTime();
        ContentValues values = new ContentValues();
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_LEAVING, FORMATTER.format(currentTime));

        writableDatabase.update(TABLE_NAME,
                values,whereColumns,whereValues);
    }

    public void createLeavingTime(String currentDate){
        Date currentTime = Calendar.getInstance().getTime();
        ContentValues values = new ContentValues();
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_DATE, currentDate);
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_LEAVING, FORMATTER.format(currentTime));

        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();
        writableDatabase.insert(TABLE_NAME, null, values);
    }

    public void updateComingTime(String currentDate) {
        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();

        String whereColumns = TimeDBSchema.DayEntry.COLUMN_NAME_DATE + " = ?";
        String[] whereValues = { currentDate };

        Date currentTime = Calendar.getInstance().getTime();

        ContentValues values = new ContentValues();
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_COMMING, FORMATTER.format(currentTime));

        writableDatabase.update(TABLE_NAME,
                values,whereColumns,whereValues);
    }

    public void createComingTime(String currentDate) {
        Date currentTime = Calendar.getInstance().getTime();
        ContentValues values = new ContentValues();
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_DATE, currentDate);
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_COMMING, FORMATTER.format(currentTime));

        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();
        writableDatabase.insert(TABLE_NAME, null, values);
    }

    public void updateBeginnPause(String currentDate) {
        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();

        String whereColumns = TimeDBSchema.DayEntry.COLUMN_NAME_DATE + " = ?";
        String[] whereValues = { currentDate };

        Date currentTime = Calendar.getInstance().getTime();
        ContentValues values = new ContentValues();
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_BEGINN_PAUSE, FORMATTER.format(currentTime));

        writableDatabase.update(TABLE_NAME,
                values,whereColumns,whereValues);
    }

    public void createBeginnPause(String currentDate) {
        Date currentTime = Calendar.getInstance().getTime();
        ContentValues values = new ContentValues();
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_DATE, currentDate);
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_BEGINN_PAUSE, FORMATTER.format(currentTime));

        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();
        writableDatabase.insert(TABLE_NAME, null, values);
    }

    public void createEndePause(String currentDate) {
        Date currentTime = Calendar.getInstance().getTime();
        ContentValues values = new ContentValues();
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_DATE, currentDate);
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_ENDE_PAUSE, FORMATTER.format(currentTime));

        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();
        writableDatabase.insert(TABLE_NAME, null, values);
    }

    public void updateEndePause(String currentDate) {
        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();

        String whereColumns = TimeDBSchema.DayEntry.COLUMN_NAME_DATE + " = ?";
        String[] whereValues = { currentDate };

        Date currentTime = Calendar.getInstance().getTime();
        ContentValues values = new ContentValues();
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_ENDE_PAUSE, FORMATTER.format(currentTime));

        writableDatabase.update(TABLE_NAME,
                values,whereColumns,whereValues);
    }

}
