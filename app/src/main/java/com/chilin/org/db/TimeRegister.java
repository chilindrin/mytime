package com.chilin.org.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeRegister {

    public void saveLeavingTime(Context context, String currentDate) {
        TimeDBRegister dbRegister = new TimeDBRegister(context);
        if (isCurrentDateAlreadyInDB(dbRegister, currentDate)) {
            updateLeavingTime(dbRegister, currentDate);
        } else {
            createLeavingTime(dbRegister, currentDate);
        }
    }

    private void updateLeavingTime(TimeDBRegister dbRegister, String currentDate) {
        SQLiteDatabase writableDatabase = dbRegister.getWritableDatabase();

        String whereColumns = TimeDBSchema.DayEntry.COLUMN_NAME_DATE + " = ?";
        String[] whereValues = { currentDate };

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        ContentValues values = new ContentValues();
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_LEAVING, formatter.format(currentTime));

        writableDatabase.update(TimeDBSchema.DayEntry.TABLE_NAME,
                values,whereColumns,whereValues);
    }

    private void createLeavingTime(TimeDBRegister dbRegister, String currentDate){
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        ContentValues values = new ContentValues();
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_DATE, currentDate);
        values.put(TimeDBSchema.DayEntry.COLUMN_NAME_LEAVING, formatter.format(currentTime));

        SQLiteDatabase writableDatabase = dbRegister.getWritableDatabase();
        long newRowId = writableDatabase.insert(TimeDBSchema.DayEntry.TABLE_NAME, null, values);
    }

    private boolean isCurrentDateAlreadyInDB(TimeDBRegister dbRegister, String currentDate){
        SQLiteDatabase readableDatabase = dbRegister.getReadableDatabase();

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

}
