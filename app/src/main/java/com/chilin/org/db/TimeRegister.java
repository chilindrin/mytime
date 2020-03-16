package com.chilin.org.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeRegister {

    public void saveLeavingTime(Context context, String currentDate) {
        TimeDBDealer dbRegister = new TimeDBDealer(context);
        if (isCurrentDateAlreadyInDB(dbRegister, currentDate)) {
            updateLeavingTime(dbRegister, currentDate);
        } else {
            createLeavingTime(dbRegister, currentDate);
        }
    }

    private void updateLeavingTime(TimeDBDealer dbDealer, String currentDate) {
        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();

        String whereColumns = TimeDBSchema.DayEntry.COLUMN_NAME_DATE + " = ?";
        String[] whereValues = { currentDate };

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        ContentValues values = new ContentValues();
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_LEAVING, formatter.format(currentTime));

        writableDatabase.update(TimeDBSchema.DayEntry.TABLE_NAME,
                values,whereColumns,whereValues);
    }

    private void createLeavingTime(TimeDBDealer dbDealer, String currentDate){
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        ContentValues values = new ContentValues();
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_DATE, currentDate);
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_LEAVING, formatter.format(currentTime));

        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();
        writableDatabase.insert(TimeDBSchema.DayEntry.TABLE_NAME, null, values);
    }

    private boolean isCurrentDateAlreadyInDB(TimeDBDealer dbDealer, String currentDate){
        SQLiteDatabase readableDatabase = dbDealer.getReadableDatabase();

        String whereColumns = TimeDBSchema.DayEntry.COLUMN_NAME_DATE + " = ?";
        String[] whereValues = { currentDate };

        Cursor cursor = readableDatabase.query(
                TimeDBSchema.DayEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                whereColumns,              // The columns for the WHERE clause
                whereValues,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return false;
        }

        cursor.close();
        return true;
    }

    public List<String[]> getAllDataInDB(Context context){
        TimeDBDealer dbDealer = new TimeDBDealer(context);
        SQLiteDatabase readableDatabase = dbDealer.getReadableDatabase();

        Cursor cursor = readableDatabase.query(
                TimeDBSchema.DayEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List<String[]> itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            String date = cursor.getString(
                    cursor.getColumnIndexOrThrow(TimeDBSchema.DayEntry.COLUMN_NAME_DATE));
            String coming = cursor.getString(
                    cursor.getColumnIndexOrThrow(TimeDBSchema.DayEntry.COLUMN_NAME_COMMING));
            String leaving = cursor.getString(
                    cursor.getColumnIndexOrThrow(TimeDBSchema.DayEntry.COLUMN_NAME_LEAVING));
            String[] result = {date,coming,leaving};
            itemIds.add(result);
        }
        cursor.close();
        return itemIds;
    }

    public void saveComingTime(Context context, String currentDate) {
        TimeDBDealer dbRegister = new TimeDBDealer(context);
        if (isCurrentDateAlreadyInDB(dbRegister, currentDate)) {
            updateComingTime(dbRegister, currentDate);
        } else {
            createComingTime(dbRegister, currentDate);
        }
    }

    private void updateComingTime(TimeDBDealer dbRegister, String currentDate) {
        SQLiteDatabase writableDatabase = dbRegister.getWritableDatabase();

        String whereColumns = TimeDBSchema.DayEntry.COLUMN_NAME_DATE + " = ?";
        String[] whereValues = { currentDate };

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        ContentValues values = new ContentValues();
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_COMMING, formatter.format(currentTime));

        writableDatabase.update(TimeDBSchema.DayEntry.TABLE_NAME,
                values,whereColumns,whereValues);
    }

    private void createComingTime(TimeDBDealer dbRegister, String currentDate) {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        ContentValues values = new ContentValues();
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_DATE, currentDate);
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_COMMING, formatter.format(currentTime));

        SQLiteDatabase writableDatabase = dbRegister.getWritableDatabase();
        writableDatabase.insert(TimeDBSchema.DayEntry.TABLE_NAME, null, values);
    }

    public void deleteDB(Context context) {
        TimeDBDealer dbRegister = new TimeDBDealer(context);
        SQLiteDatabase writableDatabase = dbRegister.getWritableDatabase();
        writableDatabase.delete(TimeDBSchema.DayEntry.TABLE_NAME,null,null);
    }
}
