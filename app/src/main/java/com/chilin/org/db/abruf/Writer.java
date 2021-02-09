package com.chilin.org.db.abruf;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.chilin.org.db.DBDealer;
import com.chilin.org.db.entity.AbrufEntity;
import com.chilin.org.db.entity.DayEntity;
import com.chilin.org.model.Abruf;

public class Writer {

    private DBDealer dbDealer;

    public Writer(Context context){
        this.dbDealer = new DBDealer(context);
    }

    public void createAbruf(Abruf abruf){
        ContentValues values = new ContentValues();
        values.put(AbrufEntity.NAME,abruf.getName());
        values.put(AbrufEntity.START_DATE,abruf.getAnfang());
        values.put(AbrufEntity.END_DATE,abruf.getEnde());
        values.put(AbrufEntity.BESTELLTE_STUNDEN,abruf.getStunden());
        writeAbrufInDB(values);
    }

    public int deleteAbruf(String abrufName){
        String whereColumns = AbrufEntity.NAME + " = ?";
        String[] whereValues = { abrufName };
        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();
        return writableDatabase.delete(AbrufEntity.TABLE_NAME,whereColumns,whereValues);
    }

    private void writeAbrufInDB(ContentValues values) {
        SQLiteDatabase writableDatabase = dbDealer.getWritableDatabase();
        writableDatabase.insert(AbrufEntity.TABLE_NAME,null,values);
    }

}
