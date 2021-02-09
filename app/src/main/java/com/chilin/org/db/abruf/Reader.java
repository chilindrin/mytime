package com.chilin.org.db.abruf;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chilin.org.db.DBDealer;
import com.chilin.org.db.entity.AbrufEntity;
import com.chilin.org.model.Abruf;

import java.util.ArrayList;
import java.util.List;

public class Reader {

    private DBDealer dbDealer;

    public Reader(Context context){
        this.dbDealer = new DBDealer(context);
    }

    public List<Abruf> getAllAbrufeFromDB(){
        SQLiteDatabase readableDatabase = dbDealer.getReadableDatabase();

        Cursor cursor = readableDatabase.query(
                AbrufEntity.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List<Abruf> allAbrufeFromDB = new ArrayList<>();
        while(cursor.moveToNext()) {
            Abruf savedAbruf = new Abruf();
            savedAbruf.setName(cursor.getString(cursor.getColumnIndexOrThrow(AbrufEntity.NAME)));
            savedAbruf.setAnfang(cursor.getString(cursor.getColumnIndexOrThrow(AbrufEntity.START_DATE)));
            savedAbruf.setEnde(cursor.getString(cursor.getColumnIndexOrThrow(AbrufEntity.END_DATE)));
            savedAbruf.setStunden(cursor.getString(cursor.getColumnIndexOrThrow(AbrufEntity.BESTELLTE_STUNDEN)));
            allAbrufeFromDB.add(savedAbruf);
        }
        cursor.close();
        return allAbrufeFromDB;
    }

}
