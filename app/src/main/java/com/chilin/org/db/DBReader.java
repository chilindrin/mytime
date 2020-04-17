package com.chilin.org.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chilin.org.exception.MyTimeException;
import com.chilin.org.model.Day;

import java.util.ArrayList;
import java.util.List;

import static com.chilin.org.db.TimeDBSchema.DayEntry.TABLE_NAME;

public class DBReader {

    private TimeDBDealer dbDealer;

    public DBReader(Context context){
        this.dbDealer = new TimeDBDealer(context);
    }

    public boolean isCurrentDateAlreadyInDB(String currentDate){
        SQLiteDatabase readableDatabase = dbDealer.getReadableDatabase();

        String whereColumns = TimeDBSchema.DayEntry.COLUMN_NAME_DATE + " = ?";
        String[] whereValues = { currentDate };

        Cursor cursor = readableDatabase.query(
                TABLE_NAME,   // The table to query
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

    public Day getRegisteredDay(String currentDate){
        SQLiteDatabase readableDatabase = dbDealer.getReadableDatabase();

        String[] selectColumns = { TimeDBSchema.DayEntry.COLUMN_NAME_DATE,
                TimeDBSchema.DayEntry.COLUMN_NAME_COMMING,
                TimeDBSchema.DayEntry.COLUMN_NAME_LEAVING,
        TimeDBSchema.DayEntry.COLUMN_NAME_BEGINN_PAUSE};

        String whereColumns = TimeDBSchema.DayEntry.COLUMN_NAME_DATE + " = ?";
        String[] whereValues = { currentDate };

        Cursor cursor = readableDatabase.query(
                TABLE_NAME,   // The table to query
                selectColumns,             // The array of columns to return (pass null to get all)
                whereColumns,              // The columns for the WHERE clause
                whereValues,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        Day result = validateAndExtractResult(currentDate, cursor);

        cursor.close();
        return result;
    }

    private Day validateAndExtractResult(String currentDate, Cursor resultFromDB) {
        if (resultFromDB.getCount() >1){
            throw new MyTimeException("There is more than one entry for the date: "+currentDate);
        }
        Day result = null;
        if (resultFromDB.moveToNext()) {
            result = new Day();
            result.setDayRegistered(resultFromDB.getString(
                    resultFromDB.getColumnIndexOrThrow(TimeDBSchema.DayEntry.COLUMN_NAME_DATE)));
            result.setComingTime(resultFromDB.getString(
                    resultFromDB.getColumnIndexOrThrow(TimeDBSchema.DayEntry.COLUMN_NAME_COMMING)));
            result.setLeavingTime(resultFromDB.getString(
                    resultFromDB.getColumnIndexOrThrow(TimeDBSchema.DayEntry.COLUMN_NAME_LEAVING)));
            result.setBeginnPause(resultFromDB.getString(
                    resultFromDB.getColumnIndexOrThrow(TimeDBSchema.DayEntry.COLUMN_NAME_BEGINN_PAUSE)));
        }
        return result;
    }

    public List<String[]> getAllDataInDB(){
        SQLiteDatabase readableDatabase = dbDealer.getReadableDatabase();

        Cursor cursor = readableDatabase.query(
                TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List<String[]> reportEntries = new ArrayList<>();
        while(cursor.moveToNext()) {
            String date = cursor.getString(
                    cursor.getColumnIndexOrThrow(TimeDBSchema.DayEntry.COLUMN_NAME_DATE));
            String coming = cursor.getString(
                    cursor.getColumnIndexOrThrow(TimeDBSchema.DayEntry.COLUMN_NAME_COMMING));
            String leaving = cursor.getString(
                    cursor.getColumnIndexOrThrow(TimeDBSchema.DayEntry.COLUMN_NAME_LEAVING));
            String beginnPause = cursor.getString(
                    cursor.getColumnIndexOrThrow(TimeDBSchema.DayEntry.COLUMN_NAME_BEGINN_PAUSE));
            String endePause = cursor.getString(
                    cursor.getColumnIndexOrThrow(TimeDBSchema.DayEntry.COLUMN_NAME_ENDE_PAUSE));
            String[] dayEntry = {date,coming,leaving,beginnPause,endePause};
            reportEntries.add(dayEntry);
        }
        cursor.close();
        return reportEntries;
    }

}
