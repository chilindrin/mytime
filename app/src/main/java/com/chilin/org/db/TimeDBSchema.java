package com.chilin.org.db;

import android.provider.BaseColumns;

public final class TimeDBSchema {

    /* Inner class that defines the table contents */
    public static class DayEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_COMMING = "comming";
        public static final String COLUMN_NAME_BEGINN_PAUSE = "beginnpause";
        public static final String COLUMN_NAME_ENDE_PAUSE = "endepause";
        public static final String COLUMN_NAME_LEAVING = "leaving";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DayEntry.TABLE_NAME + " (" +
                    DayEntry._ID + " INTEGER PRIMARY KEY," +
                    DayEntry.COLUMN_NAME_DATE + " TEXT," +
                    DayEntry.COLUMN_NAME_COMMING + " TEXT," +
                    DayEntry.COLUMN_NAME_BEGINN_PAUSE + " TEXT," +
                    DayEntry.COLUMN_NAME_ENDE_PAUSE + " TEXT," +
                    DayEntry.COLUMN_NAME_LEAVING + " TEXT)";

    public static final String SQL_ADD_COLUMN_BEGINN_PAUSE =
            "ALTER TABLE "+DayEntry.TABLE_NAME+" ADD COLUMN "+DayEntry.COLUMN_NAME_BEGINN_PAUSE+" TEXT";

    public static final String SQL_ADD_COLUMN_ENDE_PAUSE =
            "ALTER TABLE "+DayEntry.TABLE_NAME+" ADD COLUMN "+DayEntry.COLUMN_NAME_ENDE_PAUSE+" TEXT";

}
