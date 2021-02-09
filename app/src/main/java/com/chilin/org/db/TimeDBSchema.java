package com.chilin.org.db;

import com.chilin.org.db.entity.AbrufEntity;
import com.chilin.org.db.entity.DayEntity;

public final class TimeDBSchema {

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DayEntity.TABLE_NAME + " (" +
                    DayEntity._ID + " INTEGER PRIMARY KEY," +
                    DayEntity.COLUMN_NAME_DATE + " TEXT," +
                    DayEntity.COLUMN_NAME_COMMING + " TEXT," +
                    DayEntity.COLUMN_NAME_BEGINN_PAUSE + " TEXT," +
                    DayEntity.COLUMN_NAME_ENDE_PAUSE + " TEXT," +
                    DayEntity.COLUMN_NAME_LEAVING + " TEXT)";

    public static final String CREATE_ABRUF_TABLE =
            "CREATE TABLE " + AbrufEntity.TABLE_NAME + " (" +
                    AbrufEntity._ID + " INTEGER PRIMARY KEY," +
                    AbrufEntity.NAME + " TEXT," +
                    AbrufEntity.START_DATE + " TEXT," +
                    AbrufEntity.END_DATE + " TEXT," +
                    AbrufEntity.BESTELLTE_STUNDEN + " TEXT)";

    public static final String SQL_ADD_COLUMN_BEGINN_PAUSE =
            "ALTER TABLE "+DayEntity.TABLE_NAME+" ADD COLUMN "+DayEntity.COLUMN_NAME_BEGINN_PAUSE+" TEXT";

    public static final String SQL_ADD_COLUMN_ENDE_PAUSE =
            "ALTER TABLE "+DayEntity.TABLE_NAME+" ADD COLUMN "+DayEntity.COLUMN_NAME_ENDE_PAUSE+" TEXT";

}
