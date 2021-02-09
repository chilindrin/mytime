package com.chilin.org.db.stempel;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chilin.org.db.DBDealer;
import com.chilin.org.db.entity.DayEntity;
import com.chilin.org.exception.MyTimeException;
import com.chilin.org.model.Day;

import java.util.ArrayList;
import java.util.List;

public class DBReader {

    private DBDealer dbDealer;

    public DBReader(Context context){
        this.dbDealer = new DBDealer(context);
    }

    public Day getRegisteredDay(String currentDate){
        SQLiteDatabase readableDatabase = dbDealer.getReadableDatabase();

        String[] selectColumns = {DayEntity.COLUMN_NAME_DATE,
                DayEntity.COLUMN_NAME_COMMING,
                DayEntity.COLUMN_NAME_LEAVING,
                DayEntity.COLUMN_NAME_BEGINN_PAUSE,
                DayEntity.COLUMN_NAME_ENDE_PAUSE};

        String whereColumns = DayEntity.COLUMN_NAME_DATE + " = ?";
        String[] whereValues = { currentDate };

        Cursor cursor = readableDatabase.query(
                DayEntity.TABLE_NAME,   // The table to query
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
                    resultFromDB.getColumnIndexOrThrow(DayEntity.COLUMN_NAME_DATE)));
            result.setComingTime(resultFromDB.getString(
                    resultFromDB.getColumnIndexOrThrow(DayEntity.COLUMN_NAME_COMMING)));
            result.setLeavingTime(resultFromDB.getString(
                    resultFromDB.getColumnIndexOrThrow(DayEntity.COLUMN_NAME_LEAVING)));
            result.setBeginnPause(resultFromDB.getString(
                    resultFromDB.getColumnIndexOrThrow(DayEntity.COLUMN_NAME_BEGINN_PAUSE)));
            result.setEndePause(resultFromDB.getString(
                    resultFromDB.getColumnIndexOrThrow(DayEntity.COLUMN_NAME_ENDE_PAUSE)));
        }
        return result;
    }

    public List<Day> getAllDataInDB(){
        SQLiteDatabase readableDatabase = dbDealer.getReadableDatabase();

        Cursor cursor = readableDatabase.query(
                DayEntity.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List<Day> reportEntries = new ArrayList<>();
        while(cursor.moveToNext()) {
            Day savedDay = new Day();
            savedDay.setDayRegistered(cursor.getString(
                    cursor.getColumnIndexOrThrow(DayEntity.COLUMN_NAME_DATE)));
            savedDay.setComingTime(cursor.getString(
                    cursor.getColumnIndexOrThrow(DayEntity.COLUMN_NAME_COMMING)));
            savedDay.setBeginnPause(cursor.getString(
                    cursor.getColumnIndexOrThrow(DayEntity.COLUMN_NAME_BEGINN_PAUSE)));
            savedDay.setEndePause(cursor.getString(
                    cursor.getColumnIndexOrThrow(DayEntity.COLUMN_NAME_ENDE_PAUSE)));
            savedDay.setLeavingTime(cursor.getString(
                    cursor.getColumnIndexOrThrow(DayEntity.COLUMN_NAME_LEAVING)));
            reportEntries.add(savedDay);
        }
        cursor.close();
        return reportEntries;
    }

    public List<Day> getWorkedDaysOfMonth(String month,String year){
        String searchCondition = "%-"+month+"-"+year;
        SQLiteDatabase readableDatabase = dbDealer.getReadableDatabase();

        String[] selectColumns = { DayEntity.COLUMN_NAME_DATE,
                DayEntity.COLUMN_NAME_COMMING,
                DayEntity.COLUMN_NAME_LEAVING,
                DayEntity.COLUMN_NAME_BEGINN_PAUSE,
                DayEntity.COLUMN_NAME_ENDE_PAUSE};

        String whereColumns = DayEntity.COLUMN_NAME_DATE + " LIKE ?";
        String[] whereValues = { searchCondition };

        Cursor cursor = readableDatabase.query(
                DayEntity.TABLE_NAME,   // The table to query
                selectColumns,             // The array of columns to return (pass null to get all)
                whereColumns,              // The columns for the WHERE clause
                whereValues,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List<Day> reportEntries = new ArrayList<>();
        while(cursor.moveToNext()) {
            Day savedDay = new Day();
            savedDay.setDayRegistered(cursor.getString(
                    cursor.getColumnIndexOrThrow(DayEntity.COLUMN_NAME_DATE)));
            savedDay.setComingTime(cursor.getString(
                    cursor.getColumnIndexOrThrow(DayEntity.COLUMN_NAME_COMMING)));
            savedDay.setBeginnPause(cursor.getString(
                    cursor.getColumnIndexOrThrow(DayEntity.COLUMN_NAME_BEGINN_PAUSE)));
            savedDay.setEndePause(cursor.getString(
                    cursor.getColumnIndexOrThrow(DayEntity.COLUMN_NAME_ENDE_PAUSE)));
            savedDay.setLeavingTime(cursor.getString(
                    cursor.getColumnIndexOrThrow(DayEntity.COLUMN_NAME_LEAVING)));
            reportEntries.add(savedDay);
        }
        cursor.close();
        return reportEntries;
    }

}
